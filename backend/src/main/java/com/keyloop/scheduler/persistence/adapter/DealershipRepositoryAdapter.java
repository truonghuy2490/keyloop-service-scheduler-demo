package com.keyloop.scheduler.persistence.adapter;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.keyloop.scheduler.domain.entity.Dealership;
import com.keyloop.scheduler.persistence.entity.DealershipJpaEntity;
import com.keyloop.scheduler.persistence.repository.DealershipJpaRepository;

@Repository
public class DealershipRepositoryAdapter
        extends BaseRepositoryAdapter<
            Dealership,
            DealershipJpaEntity,
            UUID> {

     public DealershipRepositoryAdapter(
            DealershipJpaRepository dealershipRepository) {
        super(dealershipRepository);
    }

    @Override
    protected Dealership toDomain(DealershipJpaEntity source) {
        if (source == null) {
            return null;
        }

        Dealership target = new Dealership();

        // Inherited properties
        target.setId(source.getId());
        target.setIsDeleted(source.isDeleted());
        target.setCreatedAt(source.getCreatedAt());
        target.setUpdatedAt(source.getUpdatedAt());

        // Dealership properties
        target.setName(source.getName());
        target.setTimeZone(source.getTimeZone());

        return target;
    }

    @Override
    protected DealershipJpaEntity toPersistence(Dealership source) {
        if (source == null) {
            return null;
        }

        DealershipJpaEntity target = new DealershipJpaEntity();

        // Inherited properties
        target.setId(source.getId());
        target.setDeleted(source.getIsDeleted());
        target.setCreatedAt(source.getCreatedAt());
        target.setUpdatedAt(source.getUpdatedAt());

        // Dealership properties
        target.setName(source.getName());
        target.setTimeZone(source.getTimeZone());

        return target;
    }
}