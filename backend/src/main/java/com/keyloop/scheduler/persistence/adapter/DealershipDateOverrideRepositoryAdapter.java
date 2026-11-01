package com.keyloop.scheduler.persistence.adapter;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.keyloop.scheduler.domain.entity.DealershipDateOverride;
import com.keyloop.scheduler.persistence.entity.DealershipDateOverrideJpaEntity;
import com.keyloop.scheduler.persistence.repository.DealershipDateOverrideJpaRepository;

@Repository
public class DealershipDateOverrideRepositoryAdapter
        extends BaseRepositoryAdapter<
            DealershipDateOverride,
            DealershipDateOverrideJpaEntity,
            UUID> {

    public DealershipDateOverrideRepositoryAdapter(
            DealershipDateOverrideJpaRepository dealershipDateOverrideRepository) {
        super(dealershipDateOverrideRepository);
    }

    @Override
    protected DealershipDateOverride toDomain(
            DealershipDateOverrideJpaEntity source) {

        if (source == null) {
            return null;
        }

        return DealershipDateOverride.builder()
                .id(source.getId())
                .isDeleted(source.isDeleted())
                .createdAt(source.getCreatedAt())
                .updatedAt(source.getUpdatedAt())
                .dealershipId(source.getDealershipId())
                .businessDate(source.getBusinessDate())
                .isClosed(source.isClosed())
                .opensAt(source.getOpensAt())
                .closesAt(source.getClosesAt())
                .reason(source.getReason())
                .build();
    }

    @Override
    protected DealershipDateOverrideJpaEntity toPersistence(
            DealershipDateOverride source) {

        if (source == null) {
            return null;
        }

        return DealershipDateOverrideJpaEntity.builder()
                .id(source.getId())
                .deleted(source.getIsDeleted())
                .dealershipId(source.getDealershipId())
                .businessDate(source.getBusinessDate())
                .closed(source.getIsClosed())
                .opensAt(source.getOpensAt())
                .closesAt(source.getClosesAt())
                .reason(source.getReason())
                .build();
    }
}