package com.keyloop.scheduler.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keyloop.scheduler.persistence.entity.DealershipBusinessHoursJpaEntity;

public interface DealershipBusinessHoursJpaRepository
        extends JpaRepository<DealershipBusinessHoursJpaEntity, UUID> {

    List<DealershipBusinessHoursJpaEntity>
    findAllByDealershipIdAndDeletedFalseOrderByDayOfWeekAsc(
            UUID dealershipId
    );
}