package com.keyloop.scheduler.persistence.adapter;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.keyloop.scheduler.domain.entity.DealershipBusinessHours;
import com.keyloop.scheduler.persistence.entity.DealershipBusinessHoursJpaEntity;
import com.keyloop.scheduler.persistence.repository.DealershipBusinessHoursJpaRepository;

@Repository
public class DealershipBusinessHoursRepositoryAdapter
        extends BaseRepositoryAdapter<
            DealershipBusinessHours,
            DealershipBusinessHoursJpaEntity,
            UUID> {

    public DealershipBusinessHoursRepositoryAdapter(
            DealershipBusinessHoursJpaRepository repository) {
        super(repository);
    }

    @Override
    protected DealershipBusinessHours toDomain(
            DealershipBusinessHoursJpaEntity source) {

        if (source == null) {
            return null;
        }

        return DealershipBusinessHours.builder()
                .id(source.getId())
                .isDeleted(source.isDeleted())
                .createdAt(source.getCreatedAt())
                .dealershipId(source.getDealershipId())
                .dayOfWeek(source.getDayOfWeek())
                .closed(source.isClosed())
                .opensAt(source.getOpensAt())
                .closesAt(source.getClosesAt())
                .build();
    }

    @Override
    protected DealershipBusinessHoursJpaEntity toPersistence(
            DealershipBusinessHours source) {

        if (source == null) {
            return null;
        }

        return DealershipBusinessHoursJpaEntity.builder()
                .id(source.getId())
                .deleted(source.getIsDeleted())
                .dealershipId(source.getDealershipId())
                .dayOfWeek(source.getDayOfWeek())
                .closed(source.getIsClosed())
                .opensAt(source.getOpensAt())
                .closesAt(source.getClosesAt())
                .build();
    }
}