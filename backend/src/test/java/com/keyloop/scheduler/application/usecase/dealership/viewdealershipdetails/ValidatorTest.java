package com.keyloop.scheduler.application.usecase.dealership.viewdealershipdetails;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.keyloop.scheduler.application.usecase.dealership.viewdealershipdetails.Errors;
import com.keyloop.scheduler.application.usecase.dealership.viewdealershipdetails.Query;
import com.keyloop.scheduler.application.usecase.dealership.viewdealershipdetails.Validators;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorsTest {

    private Validators validators;

    @BeforeEach
    void setUp() {
        validators = new Validators();
    }

    @Test
    void shouldPassWhenDealershipIdIsValidUUID() {
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
    void shouldFailWhenDealershipIdIsInvalidUUID() {
        // Arrange
        Query query = new Query("invalid-uuid");

        // Act
        var errors = validators.validate(query);

        // Assert
        assertEquals(1, errors.size());
        assertEquals(
                Errors.INVALID_DEALERSHIP_ID,
                errors.get(0)
        );
    }

    @Test
    void shouldFailWhenDealershipIdIsNull() {
        // Arrange
        Query query = new Query(null);

        // Act
        var errors = validators.validate(query);

        // Assert
        assertEquals(1, errors.size());
        assertEquals(
                Errors.INVALID_DEALERSHIP_ID,
                errors.get(0)
        );
    }

    @Test
    void shouldFailWhenDealershipIdIsBlank() {
        // Arrange
        Query query = new Query(" ");

        // Act
        var errors = validators.validate(query);

        // Assert
        assertEquals(1, errors.size());
        assertEquals(
                Errors.INVALID_DEALERSHIP_ID,
                errors.get(0)
        );
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
                Errors.INVALID_DEALERSHIP_ID,
                errors.get(0)
        );
    }

    @Test
    void shouldPassWhenDealershipIdContainsUppercaseUUID() {
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
    void shouldFailWhenDealershipIdHasInvalidUUIDFormat() {
        // Arrange
        Query query = new Query(
                "550e8400-e29b-41d4-a716-44665544000"
        );

        // Act
        var errors = validators.validate(query);

        // Assert
        assertFalse(errors.isEmpty());
        assertEquals(
                Errors.INVALID_DEALERSHIP_ID,
                errors.get(0)
        );
    }
}