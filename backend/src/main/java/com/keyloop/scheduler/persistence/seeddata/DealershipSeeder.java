package com.keyloop.scheduler.persistence.seeddata;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DealershipSeeder {

    public static final UUID DEALERSHIP_001 =
        UUID.fromString("10000000-0000-0000-0000-000000000001");

    public static final UUID DEALERSHIP_002 =
        UUID.fromString("10000000-0000-0000-0000-000000000002");

    public static final UUID DEALERSHIP_003 =
        UUID.fromString("10000000-0000-0000-0000-000000000003");

    public static final UUID DEALERSHIP_004 =
        UUID.fromString("10000000-0000-0000-0000-000000000004");

    public static final UUID DEALERSHIP_005 =
        UUID.fromString("10000000-0000-0000-0000-000000000005");

    public static final UUID DEALERSHIP_006 =
        UUID.fromString("10000000-0000-0000-0000-000000000006");

    public static final UUID DEALERSHIP_007 =
        UUID.fromString("10000000-0000-0000-0000-000000000007");

    public static final UUID DEALERSHIP_008 =
        UUID.fromString("10000000-0000-0000-0000-000000000008");

    public static final UUID DEALERSHIP_009 =
        UUID.fromString("10000000-0000-0000-0000-000000000009");

    public static final UUID DEALERSHIP_010 =
        UUID.fromString("10000000-0000-0000-0000-000000000010");

    public void seed(JdbcTemplate jdbc) {
        seedDealerships(jdbc);
        seedBusinessHours(jdbc);
        seedDateOverrides(jdbc);
    }

    private void seedDealerships(JdbcTemplate jdbc) {
        jdbc.update("""
            INSERT INTO dealership (id, name, time_zone)
            VALUES
                (?, 'Keyloop HCMC Central', 'Asia/Ho_Chi_Minh'),
                (?, 'Keyloop Thu Duc', 'Asia/Ho_Chi_Minh'),
                (?, 'Keyloop Binh Thanh', 'Asia/Ho_Chi_Minh'),
                (?, 'Keyloop District 7', 'Asia/Ho_Chi_Minh'),
                (?, 'Keyloop Da Nang', 'Asia/Ho_Chi_Minh'),
                (?, 'Keyloop Hanoi Central', 'Asia/Ho_Chi_Minh'),
                (?, 'Keyloop Hai Phong', 'Asia/Ho_Chi_Minh'),
                (?, 'Keyloop Can Tho', 'Asia/Ho_Chi_Minh'),
                (?, 'Keyloop Bien Hoa', 'Asia/Ho_Chi_Minh'),
                (?, 'Keyloop Nha Trang', 'Asia/Ho_Chi_Minh')
            ON CONFLICT (id) DO NOTHING
            """,
            DEALERSHIP_001,
            DEALERSHIP_002,
            DEALERSHIP_003,
            DEALERSHIP_004,
            DEALERSHIP_005,
            DEALERSHIP_006,
            DEALERSHIP_007,
            DEALERSHIP_008,
            DEALERSHIP_009,
            DEALERSHIP_010
        );
    }

    private void seedBusinessHours(JdbcTemplate jdbc) {
        jdbc.update("""
            INSERT INTO dealership_business_hours (
                id,
                dealership_id,
                day_of_week,
                is_closed,
                opens_at,
                closes_at
            )
            SELECT
                gen_random_uuid(),
                d.dealership_id,
                days.day,
                CASE
                    WHEN days.day = 7 THEN TRUE
                    ELSE FALSE
                END,
                CASE
                    WHEN days.day = 7 THEN NULL
                    ELSE TIME '08:00'
                END,
                CASE
                    WHEN days.day = 7 THEN NULL
                    WHEN days.day = 6 THEN TIME '12:00'
                    ELSE TIME '17:00'
                END
            FROM (
                VALUES
                    (?::uuid),
                    (?::uuid),
                    (?::uuid),
                    (?::uuid),
                    (?::uuid),
                    (?::uuid),
                    (?::uuid),
                    (?::uuid),
                    (?::uuid),
                    (?::uuid)
            ) AS d(dealership_id)
            CROSS JOIN generate_series(1, 7) AS days(day)
            WHERE NOT EXISTS (
                SELECT 1
                FROM dealership_business_hours h
                WHERE h.dealership_id = d.dealership_id
                  AND h.day_of_week = days.day
                  AND h.is_deleted = FALSE
            )
            """,
            DEALERSHIP_001,
            DEALERSHIP_002,
            DEALERSHIP_003,
            DEALERSHIP_004,
            DEALERSHIP_005,
            DEALERSHIP_006,
            DEALERSHIP_007,
            DEALERSHIP_008,
            DEALERSHIP_009,
            DEALERSHIP_010
        );
    }

    private void seedDateOverrides(JdbcTemplate jdbc) {
        jdbc.update("""
            INSERT INTO dealership_date_override (
                id,
                dealership_id,
                business_date,
                is_closed,
                opens_at,
                closes_at,
                reason
            )
            VALUES
                -- Dealership 001
                ('11000000-0000-0000-0000-000000000001',
                 ?, DATE '2026-12-24', FALSE,
                 TIME '08:00', TIME '12:00',
                 'Christmas Eve - Early Closing'),

                ('11000000-0000-0000-0000-000000000002',
                 ?, DATE '2026-12-25', TRUE,
                 NULL, NULL, 'Christmas Day'),

                -- Dealership 002
                ('11000000-0000-0000-0000-000000000003',
                 ?, DATE '2026-12-24', FALSE,
                 TIME '08:00', TIME '13:00',
                 'Christmas Eve - Early Closing'),

                ('11000000-0000-0000-0000-000000000004',
                 ?, DATE '2026-12-25', TRUE,
                 NULL, NULL, 'Christmas Day'),

                -- Dealership 003
                ('11000000-0000-0000-0000-000000000005',
                 ?, DATE '2027-01-01', TRUE,
                 NULL, NULL, 'New Year Holiday'),

                ('11000000-0000-0000-0000-000000000006',
                 ?, DATE '2027-01-02', FALSE,
                 TIME '09:00', TIME '15:00',
                 'New Year - Special Opening Hours'),

                -- Dealership 004
                ('11000000-0000-0000-0000-000000000007',
                 ?, DATE '2027-01-01', TRUE,
                 NULL, NULL, 'New Year Holiday'),

                ('11000000-0000-0000-0000-000000000008',
                 ?, DATE '2027-01-02', FALSE,
                 TIME '08:00', TIME '12:00',
                 'New Year - Early Closing'),

                -- Dealership 005
                ('11000000-0000-0000-0000-000000000009',
                 ?, DATE '2027-02-05', FALSE,
                 TIME '08:00', TIME '12:00',
                 'Tet Preparation - Early Closing'),

                ('11000000-0000-0000-0000-000000000010',
                 ?, DATE '2027-02-06', TRUE,
                 NULL, NULL, 'Tet Holiday'),

                -- Dealership 006
                ('11000000-0000-0000-0000-000000000011',
                 ?, DATE '2027-02-06', TRUE,
                 NULL, NULL, 'Tet Holiday'),

                ('11000000-0000-0000-0000-000000000012',
                 ?, DATE '2027-02-07', TRUE,
                 NULL, NULL, 'Tet Holiday'),

                -- Dealership 007
                ('11000000-0000-0000-0000-000000000013',
                 ?, DATE '2027-03-08', FALSE,
                 TIME '08:00', TIME '12:00',
                 'International Womens Day - Early Closing'),

                ('11000000-0000-0000-0000-000000000014',
                 ?, DATE '2027-04-30', TRUE,
                 NULL, NULL, 'Reunification Day'),

                -- Dealership 008
                ('11000000-0000-0000-0000-000000000015',
                 ?, DATE '2027-04-30', TRUE,
                 NULL, NULL, 'Reunification Day'),

                ('11000000-0000-0000-0000-000000000016',
                 ?, DATE '2027-05-01', TRUE,
                 NULL, NULL, 'International Workers Day'),

                -- Dealership 009
                ('11000000-0000-0000-0000-000000000017',
                 ?, DATE '2027-05-01', TRUE,
                 NULL, NULL, 'International Workers Day'),

                ('11000000-0000-0000-0000-000000000018',
                 ?, DATE '2027-05-02', FALSE,
                 TIME '09:00', TIME '15:00',
                 'Special Weekend Opening'),

                -- Dealership 010
                ('11000000-0000-0000-0000-000000000019',
                 ?, DATE '2027-09-02', TRUE,
                 NULL, NULL, 'National Day'),

                ('11000000-0000-0000-0000-000000000020',
                 ?, DATE '2027-09-03', FALSE,
                 TIME '08:00', TIME '12:00',
                 'National Day - Early Closing')

            ON CONFLICT (id) DO NOTHING
            """,
            DEALERSHIP_001, DEALERSHIP_001,
            DEALERSHIP_002, DEALERSHIP_002,
            DEALERSHIP_003, DEALERSHIP_003,
            DEALERSHIP_004, DEALERSHIP_004,
            DEALERSHIP_005, DEALERSHIP_005,
            DEALERSHIP_006, DEALERSHIP_006,
            DEALERSHIP_007, DEALERSHIP_007,
            DEALERSHIP_008, DEALERSHIP_008,
            DEALERSHIP_009, DEALERSHIP_009,
            DEALERSHIP_010, DEALERSHIP_010
        );
    }
}
