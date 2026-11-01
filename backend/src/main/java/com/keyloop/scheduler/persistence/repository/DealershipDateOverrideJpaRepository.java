package com.keyloop.scheduler.persistence.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keyloop.scheduler.persistence.entity.DealershipDateOverrideJpaEntity;

public interface DealershipDateOverrideJpaRepository
        extends JpaRepository<DealershipDateOverrideJpaEntity, UUID> {

    Optional<DealershipDateOverrideJpaEntity> findByDealershipIdAndBusinessDateAndDeletedFalse(
            UUID dealershipId,
            LocalDate businessDate
    );

    List<DealershipDateOverrideJpaEntity> findAllByDealershipIdAndBusinessDateBetweenAndDeletedFalse(
            UUID dealershipId,
            LocalDate startDate,
            LocalDate endDate
    );

    boolean existsByDealershipIdAndBusinessDateAndDeletedFalse(
            UUID dealershipId,
            LocalDate businessDate
    );
}