
package com.keyloop.scheduler.application.usecase.dealership.viewdealership;

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
import com.keyloop.scheduler.domain.entity.Dealership;

@ExtendWith(MockitoExtension.class)
class HandlerTest {

    @Mock
    private BaseRepository<Dealership, UUID> dealershipRepository;

    private Handler handler;

    @BeforeEach
    void setUp() {
        handler = new Handler(dealershipRepository);
    }

    @Test
    void shouldReturnSuccessWhenDealershipsExist() {

        // Arrange
        int pageNumber = 1;
        int pageSize = 10;

        Query query = new Query(pageNumber, pageSize);

        Dealership dealership1 = createDealership();
        Dealership dealership2 = createDealership();

        List<Dealership> dealerships = List.of(
                dealership1,
                dealership2
        );

        PagedList<Dealership> pagedDealerships =
                PagedList.create(
                        dealerships,
                        2,
                        pageNumber,
                        pageSize
                );

        when(dealershipRepository.findAll(pageNumber, pageSize))
                .thenReturn(pagedDealerships);

        // Act
        Result<PagedList<Response>> result =
                handler.handle(query).join();

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

        verify(dealershipRepository).findAll(pageNumber, pageSize);
        verifyNoMoreInteractions(dealershipRepository);
    }

    @Test
    void shouldReturnSuccessWhenNoDealershipsExist() {

        // Arrange
        int pageNumber = 1;
        int pageSize = 10;

        Query query = new Query(pageNumber, pageSize);

        PagedList<Dealership> emptyDealerships =
                PagedList.create(
                        List.of(),
                        0,
                        pageNumber,
                        pageSize
                );

        when(dealershipRepository.findAll(pageNumber, pageSize))
                .thenReturn(emptyDealerships);

        // Act
        Result<PagedList<Response>> result =
                handler.handle(query).join();

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

        verify(dealershipRepository).findAll(pageNumber, pageSize);
        verifyNoMoreInteractions(dealershipRepository);
    }

    @Test
    void shouldPreserveTotalCountWhenRequestedPageHasNoItems() {

        // Arrange
        int pageNumber = 5;
        int pageSize = 10;
        long totalCount = 42;

        Query query = new Query(pageNumber, pageSize);

        PagedList<Dealership> emptyPage =
                PagedList.create(
                        List.of(),
                        totalCount,
                        pageNumber,
                        pageSize
                );

        when(dealershipRepository.findAll(pageNumber, pageSize))
                .thenReturn(emptyPage);

        // Act
        Result<PagedList<Response>> result =
                handler.handle(query).join();

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

        verify(dealershipRepository).findAll(pageNumber, pageSize);
        verifyNoMoreInteractions(dealershipRepository);
    }

    @Test
    void shouldReturnFailureWhenDatabaseThrowsException() {

        // Arrange
        int pageNumber = 1;
        int pageSize = 10;

        Query query = new Query(pageNumber, pageSize);

        when(dealershipRepository.findAll(pageNumber, pageSize))
                .thenThrow(
                        new DataAccessResourceFailureException(
                                "Database unavailable"
                        )
                );

        // Act
        Result<PagedList<Response>> result =
                handler.handle(query).join();

        // Assert
        assertTrue(result.isFailure());

        Result.Failure<?> failure =
                (Result.Failure<?>) result;

        assertFalse(failure.errorDetails().errors().isEmpty());

        Error error = failure.errorDetails().errors().get(0);

        assertEquals(
                Errors.DEALERSHIP_RETRIEVAL_FAILED.code(),
                error.code()
        );

        assertEquals(
                Errors.DEALERSHIP_RETRIEVAL_FAILED.type(),
                error.type()
        );

        verify(dealershipRepository).findAll(pageNumber, pageSize);
        verifyNoMoreInteractions(dealershipRepository);
    }

    private Dealership createDealership() {

        Dealership dealership = mock(Dealership.class);

        when(dealership.getId()).thenReturn(UUID.randomUUID());
        when(dealership.getName()).thenReturn("Keyloop Central Dealership");
        when(dealership.getTimeZone()).thenReturn("Asia/Ho_Chi_Minh");

        return dealership;
    }
}
