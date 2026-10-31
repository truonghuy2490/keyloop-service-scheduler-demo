package com.keyloop.scheduler.application.usecase.appointment.viewappointmentdetails;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorsTest {

    private Validators validators;

    @BeforeEach
    void setUp() {
        validators = new Validators();
    }

    @Test
    void shouldPassWhenAppointmentIdIsValidUUID() {
        // Arrange
        Query query = new Query(
                "550e8400-e29b-41d4-a716-446655440000"
        );

        // Act
        var errors = validators.validate(query);

        // Assert
        assertTrue(errors.isEmpty());
    }

    @Test
    void shouldFailWhenAppointmentIdIsInvalidUUID() {
        // Arrange
        Query query = new Query("invalid-uuid");

        // Act
        var errors = validators.validate(query);

        // Assert
        assertEquals(1, errors.size());
        assertEquals(
                Errors.INVALID_APPOINTMENT_ID,
                errors.get(0)
        );
    }

    @Test
    void shouldFailWhenAppointmentIdIsNull() {
        // Arrange
        Query query = new Query(null);

        // Act
        var errors = validators.validate(query);

        // Assert
        assertEquals(1, errors.size());
        assertEquals(
                Errors.INVALID_APPOINTMENT_ID,
                errors.get(0)
        );
    }

    @Test
    void shouldFailWhenAppointmentIdIsBlank() {
        // Arrange
        Query query = new Query(" ");

        // Act
        var errors = validators.validate(query);

        // Assert
        assertEquals(1, errors.size());
    }

    @Test
    void shouldFailWhenQueryIsNull() {
        // Arrange
        Query query = null;

        // Act
        var errors = validators.validate(query);

        // Assert
        assertEquals(1, errors.size());
        assertEquals(
                Errors.INVALID_APPOINTMENT_ID,
                errors.get(0)
        );
    }

    @Test
    void shouldPassWhenAppointmentIdContainsUppercaseUUID() {
        // Arrange
        Query query = new Query(
                "550E8400-E29B-41D4-A716-446655440000"
        );

        // Act
        var errors = validators.validate(query);

        // Assert
        assertTrue(errors.isEmpty());
    }

    @Test
    void shouldFailWhenAppointmentIdHasInvalidUUIDFormat() {
        // Arrange: UUID có độ dài và cấu trúc không đúng
        Query query = new Query(
                "550e8400-e29b-41d4-a716-44665544000"
        );

        // Act
        var errors = validators.validate(query);

        // Assert
        assertFalse(errors.isEmpty());
    }
}