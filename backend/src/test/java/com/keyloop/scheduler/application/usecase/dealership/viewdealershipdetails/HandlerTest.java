package com.keyloop.scheduler.application.usecase.dealership.viewdealershipdetails;

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
import com.keyloop.scheduler.application.usecase.dealership.viewdealershipdetails.Errors;
import com.keyloop.scheduler.application.usecase.dealership.viewdealershipdetails.Handler;
import com.keyloop.scheduler.application.usecase.dealership.viewdealershipdetails.Query;
import com.keyloop.scheduler.application.usecase.dealership.viewdealershipdetails.Response;
import com.keyloop.scheduler.application.usecase.dealership.viewdealershipdetails.Validators;
import com.keyloop.scheduler.domain.entity.Dealership;

@ExtendWith(MockitoExtension.class)
class HandlerTest {

    @Mock
    private BaseRepository<Dealership, UUID> dealershipRepository;

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

        Dealership dealership = mock(Dealership.class);

        when(dealership.getId()).thenReturn(dealershipId);
        when(dealership.getName()).thenReturn("Keyloop HCMC Central");
        when(dealership.getTimeZone()).thenReturn("Asia/Ho_Chi_Minh");

        when(dealershipRepository.findById(dealershipId))
                .thenReturn(Optional.of(dealership));

        // Act
        Result<Response> result = handler.handle(query).join();

        // Assert
        assertTrue(result.isSuccess());

        Result.Success<?> success =
                (Result.Success<?>) result;

        Response response = (Response) success.value();

        assertEquals(dealershipId, response.id());
        assertEquals("Keyloop HCMC Central", response.name());
        assertEquals("Asia/Ho_Chi_Minh", response.timeZone());

        verify(dealershipRepository).findById(dealershipId);
        verifyNoMoreInteractions(dealershipRepository);
    }

    @Test
    void shouldReturnFailureWhenDealershipDoesNotExist() {

        // Arrange
        UUID dealershipId = UUID.randomUUID();

        Query query = new Query(dealershipId.toString());

        when(dealershipRepository.findById(dealershipId))
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

        verify(dealershipRepository).findById(dealershipId);
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

        // Repository must not be called when validation fails
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

        when(dealershipRepository.findById(dealershipId))
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

        verify(dealershipRepository).findById(dealershipId);
        verifyNoMoreInteractions(dealershipRepository);
    }

    private Error getFirstError(Result<Response> result) {

        assertTrue(result.isFailure());

        Result.Failure<?> failure =
                (Result.Failure<?>) result;

        assertFalse(failure.errorDetails().errors().isEmpty());

        return failure.errorDetails().errors().get(0);
    }
}