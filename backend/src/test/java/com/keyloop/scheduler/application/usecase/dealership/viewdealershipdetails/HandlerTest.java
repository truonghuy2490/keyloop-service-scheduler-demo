package com.keyloop.scheduler.application.usecase.dealership.viewdealershipdetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessResourceFailureException;

import com.keyloop.scheduler.application.abstraction.dataaccess.DealershipRepository;
import com.keyloop.scheduler.application.abstraction.dataaccess.DealershipRepository.DealershipDetailsData;
import com.keyloop.scheduler.application.common.result.Error;
import com.keyloop.scheduler.application.common.result.Result;

@ExtendWith(MockitoExtension.class)
class HandlerTest {

    @Mock
    private DealershipRepository dealershipRepository;

    private Handler handler;

    @BeforeEach
    void setUp() {
        Validators validators = new Validators();

        handler = new Handler(
                dealershipRepository,
                validators
        );
    }

    @Test
    void shouldReturnSuccessWhenDealershipExists() {

        // Arrange
        UUID dealershipId = UUID.randomUUID();

        Query query = new Query(dealershipId.toString());

        DealershipDetailsData details = createDealershipDetails(dealershipId);

        when(dealershipRepository.findDetailsById(dealershipId))
                .thenReturn(Optional.of(details));

        // Act
        Result<Response> result = handler.handle(query).join();

        // Assert
        assertTrue(result.isSuccess());

        Response response = getSuccessValue(result);

        assertEquals(dealershipId, response.id());
        assertEquals("Keyloop HCMC Central", response.name());
        assertEquals("Asia/Ho_Chi_Minh", response.timeZone());

        // Verify working hours
        assertEquals(2, response.workingHours().size());

        Response.WorkingHours monday = response.workingHours().get(0);

        assertEquals(DayOfWeek.MONDAY, monday.dayOfWeek());
        assertFalse(monday.isClosed());
        assertEquals(LocalTime.of(8, 0), monday.opensAt());
        assertEquals(LocalTime.of(17, 0), monday.closesAt());

        Response.WorkingHours sunday = response.workingHours().get(1);

        assertEquals(DayOfWeek.SUNDAY, sunday.dayOfWeek());
        assertTrue(sunday.isClosed());

        // Verify date overrides
        assertEquals(1, response.dateOverrides().size());

        Response.DateOverride dateOverride =
                response.dateOverrides().get(0);

        assertEquals(
                LocalDate.of(2026, 12, 25),
                dateOverride.businessDate()
        );

        assertTrue(dateOverride.isClosed());
        assertEquals("Christmas Holiday", dateOverride.reason());

        // Verify resource summary
        assertEquals(5, response.resourceSummary().serviceBayCount());
        assertEquals(8, response.resourceSummary().technicianCount());
        assertEquals(12, response.resourceSummary().serviceCount());

        verify(dealershipRepository).findDetailsById(dealershipId);
        verifyNoMoreInteractions(dealershipRepository);
    }

    @Test
    void shouldReturnFailureWhenDealershipDoesNotExist() {

        // Arrange
        UUID dealershipId = UUID.randomUUID();

        Query query = new Query(dealershipId.toString());

        when(dealershipRepository.findDetailsById(dealershipId))
                .thenReturn(Optional.empty());

        // Act
        Result<Response> result = handler.handle(query).join();

        // Assert
        assertTrue(result.isFailure());

        Error error = getFirstError(result);

        assertEquals(
                Errors.DEALERSHIP_NOT_FOUND.code(),
                error.code()
        );

        assertEquals(
                Errors.DEALERSHIP_NOT_FOUND.type(),
                error.type()
        );

        assertEquals("dealershipId", error.property());

        verify(dealershipRepository).findDetailsById(dealershipId);
        verifyNoMoreInteractions(dealershipRepository);
    }

    @Test
    void shouldReturnFailureWhenDealershipIdIsInvalid() {

        // Arrange
        Query query = new Query("invalid-uuid");

        // Act
        Result<Response> result = handler.handle(query).join();

        // Assert
        assertTrue(result.isFailure());

        Error error = getFirstError(result);

        assertEquals(
                Errors.INVALID_DEALERSHIP_ID.code(),
                error.code()
        );

        assertEquals(
                Errors.INVALID_DEALERSHIP_ID.type(),
                error.type()
        );

        assertEquals("dealershipId", error.property());

        verifyNoInteractions(dealershipRepository);
    }

    @Test
    void shouldReturnFailureWhenDealershipIdIsNull() {

        // Arrange
        Query query = new Query(null);

        // Act
        Result<Response> result = handler.handle(query).join();

        // Assert
        assertTrue(result.isFailure());

        Error error = getFirstError(result);

        assertEquals(
                Errors.INVALID_DEALERSHIP_ID.code(),
                error.code()
        );

        assertEquals(
                Errors.INVALID_DEALERSHIP_ID.type(),
                error.type()
        );

        verifyNoInteractions(dealershipRepository);
    }

    @Test
    void shouldReturnFailureWhenDatabaseThrowsException() {

        // Arrange
        UUID dealershipId = UUID.randomUUID();

        Query query = new Query(dealershipId.toString());

        when(dealershipRepository.findDetailsById(dealershipId))
                .thenThrow(
                        new DataAccessResourceFailureException(
                                "Database unavailable"
                        )
                );

        // Act
        Result<Response> result = handler.handle(query).join();

        // Assert
        assertTrue(result.isFailure());

        Error error = getFirstError(result);

        assertEquals(
                Errors.DEALERSHIP_RETRIEVAL_FAILED.code(),
                error.code()
        );

        assertEquals(
                Errors.DEALERSHIP_RETRIEVAL_FAILED.type(),
                error.type()
        );

        verify(dealershipRepository).findDetailsById(dealershipId);
        verifyNoMoreInteractions(dealershipRepository);
    }

    private DealershipDetailsData createDealershipDetails(UUID dealershipId) {

        List<DealershipRepository.WorkingHoursData> workingHours = List.of(
                new DealershipRepository.WorkingHoursData(
                        DayOfWeek.MONDAY,
                        false,
                        LocalTime.of(8, 0),
                        LocalTime.of(17, 0)
                ),
                new DealershipRepository.WorkingHoursData(
                        DayOfWeek.SUNDAY,
                        true,
                        null,
                        null
                )
        );

        List<DealershipRepository.DateOverrideData> dateOverrides = List.of(
                new DealershipRepository.DateOverrideData(
                        LocalDate.of(2026, 12, 25),
                        true,
                        null,
                        null,
                        "Christmas Holiday"
                )
        );

        DealershipRepository.ResourceSummaryData resourceSummary =
                new DealershipRepository.ResourceSummaryData(
                        5,
                        8,
                        12
                );

        return new DealershipDetailsData(
                dealershipId,
                "Keyloop HCMC Central",
                "Asia/Ho_Chi_Minh",
                workingHours,
                dateOverrides,
                resourceSummary
        );
    }

    private Response getSuccessValue(Result<Response> result) {

        assertTrue(result.isSuccess());

        Result.Success<?> success =
                (Result.Success<?>) result;

        return (Response) success.value();
    }

    private Error getFirstError(Result<Response> result) {

        assertTrue(result.isFailure());

        Result.Failure<?> failure =
                (Result.Failure<?>) result;

        assertFalse(failure.errorDetails().errors().isEmpty());

        return failure.errorDetails().errors().get(0);
    }
}
