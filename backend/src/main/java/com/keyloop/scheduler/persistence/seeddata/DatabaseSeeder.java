
package com.keyloop.scheduler.persistence.seeddata;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatabaseSeeder {

    @Bean
    @ConditionalOnProperty(
        name = "app.seed.enabled",
        havingValue = "false"
    )
    ApplicationRunner seedDatabase(JdbcTemplate jdbc) {
        return args -> {
            seedDealership(jdbc);
            seedCustomers(jdbc);
            seedServiceTypes(jdbc);
            seedVehicles(jdbc);
            seedServiceBays(jdbc);
            seedTechnicians(jdbc);
            seedTechnicianQualifications(jdbc);
            seedBusinessHours(jdbc);
            seedTechnicianWorkingHours(jdbc);
            seedAppointments(jdbc);

            System.out.println("Database seed completed.");
        };
    }
    private void seedDealership(JdbcTemplate jdbc) {
        jdbc.update("""
            INSERT INTO dealership (id, name, time_zone)
            VALUES (
                '10000000-0000-0000-0000-000000000001',
                'Keyloop Demo Dealership',
                'Asia/Ho_Chi_Minh'
            )
            ON CONFLICT (id) DO NOTHING
            """);
    }

    private void seedCustomers(JdbcTemplate jdbc) {
        jdbc.update("""
            INSERT INTO customer (id, full_name, email, phone)
            VALUES
            ('20000000-0000-0000-0000-000000000001',
             'Nguyen Van An', 'an@example.com', '0901234567'),
            ('20000000-0000-0000-0000-000000000002',
             'Tran Thi Binh', 'binh@example.com', '0902345678'),
            ('20000000-0000-0000-0000-000000000003',
             'Le Van Cuong', 'cuong@example.com', '0903456789')
            ON CONFLICT (id) DO NOTHING
            """);
    }

    private void seedServiceTypes(JdbcTemplate jdbc) {
        jdbc.update("""
            INSERT INTO service_type
                (id, name, description, duration_minutes)
            VALUES
            ('30000000-0000-0000-0000-000000000001',
             'Oil Change', 'Engine oil and filter replacement', 60),
            ('30000000-0000-0000-0000-000000000002',
             'General Inspection', 'General vehicle inspection', 90),
            ('30000000-0000-0000-0000-000000000003',
             'Brake Service', 'Brake system inspection and maintenance', 120)
            ON CONFLICT (id) DO NOTHING
            """);
    }

    private void seedVehicles(JdbcTemplate jdbc) {
        jdbc.update("""
            INSERT INTO vehicle
                (id, customer_id, registration_number, make, model, model_year)
            VALUES
            ('40000000-0000-0000-0000-000000000001',
             '20000000-0000-0000-0000-000000000001',
             '51A-12345', 'Toyota', 'Vios', 2022),

            ('40000000-0000-0000-0000-000000000002',
             '20000000-0000-0000-0000-000000000001',
             '51A-12346', 'Honda', 'Civic', 2023),

            ('40000000-0000-0000-0000-000000000003',
             '20000000-0000-0000-0000-000000000002',
             '51B-23456', 'Mazda', 'CX-5', 2021),

            ('40000000-0000-0000-0000-000000000004',
             '20000000-0000-0000-0000-000000000003',
             '51C-34567', 'Hyundai', 'Tucson', 2024)
            ON CONFLICT (id) DO NOTHING
            """);
    }

    private void seedServiceBays(JdbcTemplate jdbc) {
        jdbc.update("""
            INSERT INTO service_bay
                (id, dealership_id, bay_code, active)
            VALUES
            ('50000000-0000-0000-0000-000000000001',
             '10000000-0000-0000-0000-000000000001', 'BAY-01', TRUE),

            ('50000000-0000-0000-0000-000000000002',
             '10000000-0000-0000-0000-000000000001', 'BAY-02', TRUE),

            ('50000000-0000-0000-0000-000000000003',
             '10000000-0000-0000-0000-000000000001', 'BAY-03', TRUE)
            ON CONFLICT (id) DO NOTHING
            """);
    }

    private void seedTechnicians(JdbcTemplate jdbc) {
        jdbc.update("""
            INSERT INTO technician
                (id, dealership_id, full_name, active)
            VALUES
            ('60000000-0000-0000-0000-000000000001',
             '10000000-0000-0000-0000-000000000001',
             'Nguyen Minh Duc', TRUE),

            ('60000000-0000-0000-0000-000000000002',
             '10000000-0000-0000-0000-000000000001',
             'Tran Quoc Huy', TRUE),

            ('60000000-0000-0000-0000-000000000003',
             '10000000-0000-0000-0000-000000000001',
             'Le Thanh Nam', TRUE)
            ON CONFLICT (id) DO NOTHING
            """);
    }

    private void seedTechnicianQualifications(JdbcTemplate jdbc) {
        jdbc.update("""
            INSERT INTO technician_qualification
                (technician_id, service_type_id)
            VALUES
            ('60000000-0000-0000-0000-000000000001',
             '30000000-0000-0000-0000-000000000001'),

            ('60000000-0000-0000-0000-000000000001',
             '30000000-0000-0000-0000-000000000002'),

            ('60000000-0000-0000-0000-000000000002',
             '30000000-0000-0000-0000-000000000002'),

            ('60000000-0000-0000-0000-000000000002',
             '30000000-0000-0000-0000-000000000003'),

            ('60000000-0000-0000-0000-000000000003',
             '30000000-0000-0000-0000-000000000001'),

            ('60000000-0000-0000-0000-000000000003',
             '30000000-0000-0000-0000-000000000003')
            ON CONFLICT (technician_id, service_type_id) DO NOTHING
            """);
    }

    private void seedBusinessHours(JdbcTemplate jdbc) {
        jdbc.update("""
            INSERT INTO dealership_business_hours
                (id, dealership_id, day_of_week,
                 is_closed, opens_at, closes_at)
            SELECT
                gen_random_uuid(),
                '10000000-0000-0000-0000-000000000001',
                days.day,
                FALSE,
                TIME '08:00',
                TIME '17:00'
            FROM generate_series(1, 5) AS days(day)
            WHERE NOT EXISTS (
                SELECT 1
                FROM dealership_business_hours h
                WHERE h.dealership_id =
                    '10000000-0000-0000-0000-000000000001'
                  AND h.day_of_week = days.day
                  AND h.is_deleted = FALSE
            )
            """);
    }

    private void seedTechnicianWorkingHours(JdbcTemplate jdbc) {
        jdbc.update("""
            INSERT INTO technician_working_hours
                (id, technician_id, day_of_week, starts_at, ends_at)
            SELECT
                gen_random_uuid(),
                tech.id,
                days.day,
                TIME '08:00',
                TIME '17:00'
            FROM (
                SELECT unnest(ARRAY[
                    '60000000-0000-0000-0000-000000000001'::uuid,
                    '60000000-0000-0000-0000-000000000002'::uuid,
                    '60000000-0000-0000-0000-000000000003'::uuid
                ]) AS id
            ) tech
            CROSS JOIN generate_series(1, 5) AS days(day)
            WHERE NOT EXISTS (
                SELECT 1
                FROM technician_working_hours h
                WHERE h.technician_id = tech.id
                  AND h.day_of_week = days.day
                  AND h.is_deleted = FALSE
            )
            """);
    }

    private void seedAppointments(JdbcTemplate jdbc) {
        jdbc.update("""
            INSERT INTO appointment (
                id,
                customer_id,
                vehicle_id,
                dealership_id,
                service_type_id,
                technician_id,
                service_bay_id,
                start_time,
                end_time,
                status
            )
            VALUES (
                '70000000-0000-0000-0000-000000000001',
                '20000000-0000-0000-0000-000000000001',
                '40000000-0000-0000-0000-000000000001',
                '10000000-0000-0000-0000-000000000001',
                '30000000-0000-0000-0000-000000000001',
                '60000000-0000-0000-0000-000000000001',
                '50000000-0000-0000-0000-000000000001',
                date_trunc('day', now()) + INTERVAL '1 day' + TIME '09:00',
                date_trunc('day', now()) + INTERVAL '1 day' + TIME '10:00',
                'CONFIRMED'
            )
            ON CONFLICT (id) DO NOTHING
            """);

        jdbc.update("""
            INSERT INTO appointment_status_history (
                id, appointment_id, old_status, new_status, reason
            )
            SELECT
                '80000000-0000-0000-0000-000000000001',
                '70000000-0000-0000-0000-000000000001',
                NULL,
                'CONFIRMED',
                'Initial appointment confirmation'
            WHERE EXISTS (
                SELECT 1 FROM appointment
                WHERE id = '70000000-0000-0000-0000-000000000001'
            )
            ON CONFLICT (id) DO NOTHING
            """);
    }
}
