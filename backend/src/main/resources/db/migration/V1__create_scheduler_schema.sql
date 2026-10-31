-- Keyloop Unified Service Scheduler - PostgreSQL schema
-- MVP assumptions:
-- 1) One business-hours interval per dealership per weekday/date.
-- 2) One technician override interval per technician per date.
-- 3) Appointment timestamps are absolute instants (timestamptz).
-- 4) Cancelled appointments do not reserve resources; confirmed/completed/no-show
--    appointments remain protected from overlapping historical bookings.
-- 5) Appointment cancellation is the business workflow; soft-delete is not a substitute.

CREATE EXTENSION IF NOT EXISTS btree_gist;

CREATE TABLE customer (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    full_name       VARCHAR(160) NOT NULL,
    email           VARCHAR(254) NOT NULL,
    phone           VARCHAR(40),
    is_deleted      BOOLEAN NOT NULL DEFAULT FALSE,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at      TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE UNIQUE INDEX uq_customer_email_not_deleted
    ON customer (lower(email)) WHERE is_deleted = FALSE;

CREATE TABLE vehicle (
    id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    customer_id         UUID NOT NULL REFERENCES customer(id),
    registration_number VARCHAR(32) NOT NULL,
    make                VARCHAR(100) NOT NULL,
    model               VARCHAR(100) NOT NULL,
    model_year          SMALLINT,
    is_deleted          BOOLEAN NOT NULL DEFAULT FALSE,
    created_at          TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at          TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT uq_vehicle_id_customer UNIQUE (id, customer_id),
    CONSTRAINT ck_vehicle_model_year CHECK (model_year IS NULL OR model_year BETWEEN 1886 AND 2200)
);
CREATE UNIQUE INDEX uq_vehicle_registration_not_deleted
    ON vehicle (upper(registration_number)) WHERE is_deleted = FALSE;
CREATE INDEX ix_vehicle_customer ON vehicle(customer_id);

CREATE TABLE dealership (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name        VARCHAR(160) NOT NULL,
    time_zone   VARCHAR(64) NOT NULL DEFAULT 'UTC',
    is_deleted  BOOLEAN NOT NULL DEFAULT FALSE,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE service_type (
    id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name                VARCHAR(120) NOT NULL,
    description         TEXT,
    duration_minutes    INTEGER NOT NULL,
    is_deleted          BOOLEAN NOT NULL DEFAULT FALSE,
    created_at          TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at          TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT ck_service_duration CHECK (duration_minutes > 0)
);
CREATE UNIQUE INDEX uq_service_type_name_not_deleted
    ON service_type (lower(name)) WHERE is_deleted = FALSE;

CREATE TABLE service_bay (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    dealership_id   UUID NOT NULL REFERENCES dealership(id),
    bay_code        VARCHAR(40) NOT NULL,
    active          BOOLEAN NOT NULL DEFAULT TRUE,
    is_deleted      BOOLEAN NOT NULL DEFAULT FALSE,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT uq_service_bay_dealership_code UNIQUE (dealership_id, bay_code),
    CONSTRAINT uq_service_bay_id_dealership UNIQUE (id, dealership_id)
);
CREATE INDEX ix_service_bay_dealership_active
    ON service_bay(dealership_id, active) WHERE is_deleted = FALSE;

CREATE TABLE technician (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    dealership_id   UUID NOT NULL REFERENCES dealership(id),
    full_name       VARCHAR(160) NOT NULL,
    active          BOOLEAN NOT NULL DEFAULT TRUE,
    is_deleted      BOOLEAN NOT NULL DEFAULT FALSE,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT uq_technician_id_dealership UNIQUE (id, dealership_id)
);
CREATE INDEX ix_technician_dealership_active
    ON technician(dealership_id, active) WHERE is_deleted = FALSE;

CREATE TABLE technician_qualification (
    technician_id   UUID NOT NULL REFERENCES technician(id),
    service_type_id UUID NOT NULL REFERENCES service_type(id),
    qualified_at    TIMESTAMPTZ NOT NULL DEFAULT now(),
    PRIMARY KEY (technician_id, service_type_id)
);

-- ISO weekday: Monday=1 ... Sunday=7. Multiple rows allow split shifts.
CREATE TABLE dealership_business_hours (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    dealership_id   UUID NOT NULL REFERENCES dealership(id),
    day_of_week     SMALLINT NOT NULL CHECK (day_of_week BETWEEN 1 AND 7),
    is_closed       BOOLEAN NOT NULL DEFAULT FALSE,
    opens_at        TIME,
    closes_at       TIME,
    is_deleted      BOOLEAN NOT NULL DEFAULT FALSE,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT ck_dealership_hours_interval CHECK (
        (is_closed = TRUE AND opens_at IS NULL AND closes_at IS NULL)
        OR (is_closed = FALSE AND opens_at IS NOT NULL AND closes_at IS NOT NULL AND opens_at < closes_at)
    )
);
CREATE INDEX ix_dealership_hours_lookup
    ON dealership_business_hours(dealership_id, day_of_week) WHERE is_deleted = FALSE;

-- An override replaces normal hours for the specified local calendar date.
-- MVP: one interval per date; is_closed=true means closed all day.
CREATE TABLE dealership_date_override (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    dealership_id   UUID NOT NULL REFERENCES dealership(id),
    business_date   DATE NOT NULL,
    is_closed       BOOLEAN NOT NULL DEFAULT TRUE,
    opens_at        TIME,
    closes_at       TIME,
    reason          VARCHAR(300),
    is_deleted      BOOLEAN NOT NULL DEFAULT FALSE,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT uq_dealership_date_override UNIQUE (dealership_id, business_date),
    CONSTRAINT ck_dealership_override_interval CHECK (
        (is_closed = TRUE AND opens_at IS NULL AND closes_at IS NULL)
        OR (is_closed = FALSE AND opens_at IS NOT NULL AND closes_at IS NOT NULL AND opens_at < closes_at)
    )
);

-- Multiple rows per weekday support split shifts.
CREATE TABLE technician_working_hours (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    technician_id   UUID NOT NULL REFERENCES technician(id),
    day_of_week     SMALLINT NOT NULL CHECK (day_of_week BETWEEN 1 AND 7),
    starts_at       TIME NOT NULL,
    ends_at         TIME NOT NULL,
    is_deleted      BOOLEAN NOT NULL DEFAULT FALSE,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT ck_technician_hours_interval CHECK (starts_at < ends_at)
);
CREATE INDEX ix_technician_hours_lookup
    ON technician_working_hours(technician_id, day_of_week) WHERE is_deleted = FALSE;

-- An unavailable override means the technician is off for the whole local date.
-- Otherwise it replaces the normal schedule with one interval (MVP limitation).
CREATE TABLE technician_date_override (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    technician_id   UUID NOT NULL REFERENCES technician(id),
    work_date       DATE NOT NULL,
    is_unavailable  BOOLEAN NOT NULL DEFAULT TRUE,
    starts_at       TIME,
    ends_at         TIME,
    reason          VARCHAR(300),
    is_deleted      BOOLEAN NOT NULL DEFAULT FALSE,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT uq_technician_date_override UNIQUE (technician_id, work_date),
    CONSTRAINT ck_technician_override_interval CHECK (
        (is_unavailable = TRUE AND starts_at IS NULL AND ends_at IS NULL)
        OR (is_unavailable = FALSE AND starts_at IS NOT NULL AND ends_at IS NOT NULL AND starts_at < ends_at)
    )
);

CREATE TABLE appointment (
    id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    customer_id         UUID NOT NULL REFERENCES customer(id),
    vehicle_id          UUID NOT NULL,
    dealership_id       UUID NOT NULL REFERENCES dealership(id),
    service_type_id     UUID NOT NULL REFERENCES service_type(id),
    technician_id       UUID NOT NULL,
    service_bay_id      UUID NOT NULL,
    start_time          TIMESTAMPTZ NOT NULL,
    end_time            TIMESTAMPTZ NOT NULL,
    status              VARCHAR(20) NOT NULL DEFAULT 'CONFIRMED',
    is_deleted          BOOLEAN NOT NULL DEFAULT FALSE,
    version             BIGINT NOT NULL DEFAULT 0,
    cancellation_reason VARCHAR(500),
    created_at          TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at          TIMESTAMPTZ NOT NULL DEFAULT now(),
    -- [start,end) allows adjacent appointments (e.g. 10:00-11:00, 11:00-12:00).
    appointment_period TSTZRANGE GENERATED ALWAYS AS (tstzrange(start_time, end_time, '[)')) STORED,
    CONSTRAINT ck_appointment_time CHECK (start_time < end_time),
    CONSTRAINT ck_appointment_status CHECK (status IN ('CONFIRMED', 'CANCELLED', 'COMPLETED', 'NO_SHOW')),
    -- Prevent booking a vehicle under a customer who does not own it.
    CONSTRAINT fk_appointment_vehicle_customer
        FOREIGN KEY (vehicle_id, customer_id) REFERENCES vehicle(id, customer_id),
    -- Prevent selecting a technician or bay belonging to a different dealership.
    CONSTRAINT fk_appointment_technician_dealership
        FOREIGN KEY (technician_id, dealership_id) REFERENCES technician(id, dealership_id),
    CONSTRAINT fk_appointment_bay_dealership
        FOREIGN KEY (service_bay_id, dealership_id) REFERENCES service_bay(id, dealership_id),
    -- DB-level concurrency protection: overlapping active/historical reservations cannot
    -- use the same technician or bay. Cancelled and soft-deleted appointments release slots.
    CONSTRAINT ex_appointment_technician_no_overlap
        EXCLUDE USING gist (technician_id WITH =, appointment_period WITH &&)
        WHERE (is_deleted = FALSE AND status IN ('CONFIRMED', 'COMPLETED', 'NO_SHOW')),
    CONSTRAINT ex_appointment_bay_no_overlap
        EXCLUDE USING gist (service_bay_id WITH =, appointment_period WITH &&)
        WHERE (is_deleted = FALSE AND status IN ('CONFIRMED', 'COMPLETED', 'NO_SHOW'))
);
CREATE INDEX ix_appointment_customer_start
    ON appointment(customer_id, start_time DESC) WHERE is_deleted = FALSE;
CREATE INDEX ix_appointment_dealership_start
    ON appointment(dealership_id, start_time) WHERE is_deleted = FALSE;
CREATE INDEX ix_appointment_vehicle_start
    ON appointment(vehicle_id, start_time DESC) WHERE is_deleted = FALSE;
CREATE INDEX ix_appointment_status_start
    ON appointment(status, start_time) WHERE is_deleted = FALSE;

-- Append-only history of lifecycle transitions; do not soft-delete these rows.
CREATE TABLE appointment_status_history (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    appointment_id  UUID NOT NULL REFERENCES appointment(id),
    old_status      VARCHAR(20),
    new_status      VARCHAR(20) NOT NULL,
    reason          VARCHAR(500),
    changed_by      UUID,
    changed_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT ck_history_new_status CHECK (new_status IN ('CONFIRMED', 'CANCELLED', 'COMPLETED', 'NO_SHOW')),
    CONSTRAINT ck_history_old_status CHECK (old_status IS NULL OR old_status IN ('CONFIRMED', 'CANCELLED', 'COMPLETED', 'NO_SHOW'))
);
CREATE INDEX ix_appointment_status_history_appointment_time
    ON appointment_status_history(appointment_id, changed_at DESC);

-- Append-only audit events for changes beyond status transitions.
CREATE TABLE appointment_audit_log (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    appointment_id  UUID NOT NULL REFERENCES appointment(id),
    action          VARCHAR(80) NOT NULL,
    details         JSONB NOT NULL DEFAULT '{}'::jsonb,
    actor_id        UUID,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE INDEX ix_appointment_audit_appointment_time
    ON appointment_audit_log(appointment_id, created_at DESC);

-- Notes for implementation:
-- * Validate customer/vehicle/dealership/service/resource soft-delete and active flags in app logic.
-- * Check weekly hours + date overrides + technician schedule before attempting the insert.
-- * The exclusion constraints remain the final guard against concurrent double booking.
-- * Write appointment and its initial status-history row in one transaction.
-- * For production, restrict UPDATE/DELETE permissions on history/audit tables.
-- * Add updated_at maintenance via Spring auditing or a database trigger if desired.
