package com.keyloop.scheduler.application.usecase.appointment.viewappointmentdetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessResourceFailureException;

import com.keyloop.scheduler.application.abstraction.dataaccess.BaseRepository;
import com.keyloop.scheduler.application.common.result.Error;
import com.keyloop.scheduler.application.common.result.Result;
import com.keyloop.scheduler.domain.entity.Appointment;

@ExtendWith(MockitoExtension.class)
class HandlerTest {

    @Mock
    private BaseRepository<Appointment, UUID> appointmentRepository;

    private Handler handler;

    @BeforeEach
    void setUp() {
        Validators validators = new Validators();

        handler = new Handler(
                appointmentRepository,
                validators
        );
    }

    @Test
    void shouldReturnSuccessWhenAppointmentExists() {

        // Arrange
        UUID appointmentId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        UUID vehicleId = UUID.randomUUID();

        Query query = new Query(appointmentId.toString());

        Appointment appointment = mock(Appointment.class);

        when(appointment.getId()).thenReturn(appointmentId);
        when(appointment.getCustomerId()).thenReturn(customerId);
        when(appointment.getVehicleId()).thenReturn(vehicleId);
        when(appointment.getStatus()).thenReturn("CONFIRMED");

        when(appointmentRepository.findById(appointmentId))
                .thenReturn(Optional.of(appointment));

        // Act
        Result<Response> result = handler.handle(query);

        // Assert
        assertTrue(result.isSuccess());

        Result.Success<?> success =
                (Result.Success<?>) result;

        Response response = (Response) success.value();

        assertEquals(appointmentId, response.id());
        assertEquals(customerId, response.customerId());
        assertEquals(vehicleId, response.vehicleId());
        assertEquals("CONFIRMED", response.status());

        verify(appointmentRepository).findById(appointmentId);
        verifyNoMoreInteractions(appointmentRepository);
    }

    @Test
    void shouldReturnFailureWhenAppointmentDoesNotExist() {

        // Arrange
        UUID appointmentId = UUID.randomUUID();

        Query query = new Query(appointmentId.toString());

        when(appointmentRepository.findById(appointmentId))
                .thenReturn(Optional.empty());

        // Act
        Result<Response> result = handler.handle(query);

        // Assert
        assertTrue(result.isFailure());

        Error error = getFirstError(result);

        assertEquals(
                Errors.APPOINTMENT_NOT_FOUND.code(),
                error.code()
        );

        assertEquals(
                Errors.APPOINTMENT_NOT_FOUND.type(),
                error.type()
        );

        assertEquals("appointmentId", error.property());

        verify(appointmentRepository).findById(appointmentId);
        verifyNoMoreInteractions(appointmentRepository);
    }

    @Test
    void shouldReturnFailureWhenAppointmentIdIsInvalid() {

        // Arrange
        Query query = new Query("invalid-uuid");

        // Act
        Result<Response> result = handler.handle(query);

        // Assert
        assertTrue(result.isFailure());

        Error error = getFirstError(result);

        assertEquals(
                Errors.INVALID_APPOINTMENT_ID.code(),
                error.code()
        );

        assertEquals(
                Errors.INVALID_APPOINTMENT_ID.type(),
                error.type()
        );

        assertEquals("appointmentId", error.property());

        // Repository must not be called when validation fails
        verifyNoInteractions(appointmentRepository);
    }

    @Test
    void shouldReturnFailureWhenAppointmentIdIsNull() {

        // Arrange
        Query query = new Query(null);

        // Act
        Result<Response> result = handler.handle(query);

        // Assert
        assertTrue(result.isFailure());

        Error error = getFirstError(result);

        assertEquals(
                Errors.INVALID_APPOINTMENT_ID.code(),
                error.code()
        );

        verifyNoInteractions(appointmentRepository);
    }

    @Test
    void shouldReturnFailureWhenDatabaseThrowsException() {

        // Arrange
        UUID appointmentId = UUID.randomUUID();

        Query query = new Query(appointmentId.toString());

        when(appointmentRepository.findById(appointmentId))
                .thenThrow(
                        new DataAccessResourceFailureException(
                                "Database unavailable"
                        )
                );

        // Act
        Result<Response> result = handler.handle(query);

        // Assert
        assertTrue(result.isFailure());

        Error error = getFirstError(result);

        assertEquals(
                Errors.APPOINTMENT_RETRIEVAL_FAILED.code(),
                error.code()
        );

        assertEquals(
                Errors.APPOINTMENT_RETRIEVAL_FAILED.type(),
                error.type()
        );

        verify(appointmentRepository).findById(appointmentId);
        verifyNoMoreInteractions(appointmentRepository);
    }

    private Error getFirstError(Result<Response> result) {

        assertTrue(result.isFailure());

        Result.Failure<?> failure =
                (Result.Failure<?>) result;

        assertFalse(failure.errorDetails().errors().isEmpty());

        return failure.errorDetails().errors().get(0);
    }
}
