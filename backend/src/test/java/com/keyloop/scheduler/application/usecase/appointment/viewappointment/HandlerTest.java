package com.keyloop.scheduler.application.usecase.appointment.viewappointment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessResourceFailureException;

import com.keyloop.scheduler.application.abstraction.dataaccess.BaseRepository;
import com.keyloop.scheduler.application.common.pagination.PagedList;
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
        handler = new Handler(appointmentRepository);
    }

    @Test
    void shouldReturnSuccessWhenAppointmentsExist() {

        // Arrange
        int pageNumber = 1;
        int pageSize = 10;

        Query query = new Query(pageNumber, pageSize);

        Appointment appointment1 = createAppointment();
        Appointment appointment2 = createAppointment();

        List<Appointment> appointments = List.of(
                appointment1,
                appointment2
        );

        PagedList<Appointment> pagedAppointments =
                PagedList.create(
                        appointments,
                        2,
                        pageNumber,
                        pageSize
                );

        when(appointmentRepository.findAll(pageNumber, pageSize))
                .thenReturn(pagedAppointments);

        // Act
        Result<PagedList<Response>> result = handler.handle(query);

        // Assert
        assertTrue(result.isSuccess());

        Result.Success<?> success =
                (Result.Success<?>) result;

        @SuppressWarnings("unchecked")
        PagedList<Response> response =
                (PagedList<Response>) success.value();

        assertEquals(2, response.items().size());
        assertEquals(2, response.totalCount());
        assertEquals(pageNumber, response.pageNumber());
        assertEquals(pageSize, response.pageSize());

        verify(appointmentRepository).findAll(pageNumber, pageSize);
        verifyNoMoreInteractions(appointmentRepository);
    }

    @Test
    void shouldReturnSuccessWhenNoAppointmentsExist() {

        // Arrange
        int pageNumber = 1;
        int pageSize = 10;

        Query query = new Query(pageNumber, pageSize);

        PagedList<Appointment> emptyAppointments =
                PagedList.create(
                        List.of(),
                        0,
                        pageNumber,
                        pageSize
                );

        when(appointmentRepository.findAll(pageNumber, pageSize))
                .thenReturn(emptyAppointments);

        // Act
        Result<PagedList<Response>> result = handler.handle(query);

        // Assert
        assertTrue(result.isSuccess());

        Result.Success<?> success =
                (Result.Success<?>) result;

        @SuppressWarnings("unchecked")
        PagedList<Response> response =
                (PagedList<Response>) success.value();

        assertTrue(response.items().isEmpty());
        assertEquals(0, response.totalCount());
        assertEquals(pageNumber, response.pageNumber());
        assertEquals(pageSize, response.pageSize());

        verify(appointmentRepository).findAll(pageNumber, pageSize);
        verifyNoMoreInteractions(appointmentRepository);
    }

    @Test
    void shouldPreserveTotalCountWhenRequestedPageHasNoItems() {

        // Arrange
        int pageNumber = 5;
        int pageSize = 10;
        long totalCount = 42;

        Query query = new Query(pageNumber, pageSize);

        PagedList<Appointment> emptyPage =
                PagedList.create(
                        List.of(),
                        totalCount,
                        pageNumber,
                        pageSize
                );

        when(appointmentRepository.findAll(pageNumber, pageSize))
                .thenReturn(emptyPage);

        // Act
        Result<PagedList<Response>> result = handler.handle(query);

        // Assert
        assertTrue(result.isSuccess());

        Result.Success<?> success =
                (Result.Success<?>) result;

        @SuppressWarnings("unchecked")
        PagedList<Response> response =
                (PagedList<Response>) success.value();

        assertTrue(response.items().isEmpty());

        // Total count must still reflect all matching records
        assertEquals(totalCount, response.totalCount());

        assertEquals(pageNumber, response.pageNumber());
        assertEquals(pageSize, response.pageSize());

        verify(appointmentRepository).findAll(pageNumber, pageSize);
        verifyNoMoreInteractions(appointmentRepository);
    }

    @Test
    void shouldReturnFailureWhenDatabaseThrowsException() {

        // Arrange
        int pageNumber = 1;
        int pageSize = 10;

        Query query = new Query(pageNumber, pageSize);

        when(appointmentRepository.findAll(pageNumber, pageSize))
                .thenThrow(
                        new DataAccessResourceFailureException(
                                "Database unavailable"
                        )
                );

        // Act
        Result<PagedList<Response>> result = handler.handle(query);

        // Assert
        assertTrue(result.isFailure());

        Result.Failure<?> failure =
                (Result.Failure<?>) result;

        assertFalse(failure.errorDetails().errors().isEmpty());

        Error error = failure.errorDetails().errors().get(0);

        assertEquals(
                Errors.APPOINTMENT_RETRIEVAL_FAILED.code(),
                error.code()
        );

        assertEquals(
                Errors.APPOINTMENT_RETRIEVAL_FAILED.type(),
                error.type()
        );

        verify(appointmentRepository).findAll(pageNumber, pageSize);
        verifyNoMoreInteractions(appointmentRepository);
    }

    private Appointment createAppointment() {

        Appointment appointment = mock(Appointment.class);

        when(appointment.getId()).thenReturn(UUID.randomUUID());
        when(appointment.getCustomerId()).thenReturn(UUID.randomUUID());
        when(appointment.getVehicleId()).thenReturn(UUID.randomUUID());
        when(appointment.getStatus()).thenReturn("CONFIRMED");

        return appointment;
    }
}
