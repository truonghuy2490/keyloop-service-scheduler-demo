package com.keyloop.scheduler.persistence.mapper;

import com.keyloop.scheduler.domain.entity.Customer;
import com.keyloop.scheduler.persistence.entity.CustomerJpaEntity;

/** Converts persistence models to/from framework-free domain models. */
public final class CustomerPersistenceMapper {
    private CustomerPersistenceMapper() {}

    public static Customer toDomain(CustomerJpaEntity source) {
        if (source == null) return null;
        Customer target = new Customer();
        target.setId(source.getId());
        target.setFullName(source.getFullName());
        target.setEmail(source.getEmail());
        target.setPhone(source.getPhone());
        target.setIsDeleted(source.isDeleted());
        target.setCreatedAt(source.getCreatedAt());
        target.setUpdatedAt(source.getUpdatedAt());
        return target;
    }

    public static CustomerJpaEntity toPersistence(Customer source) {
        if (source == null) return null;
        CustomerJpaEntity target = new CustomerJpaEntity();
        target.setId(source.getId());
        target.setFullName(source.getFullName());
        target.setEmail(source.getEmail());
        target.setPhone(source.getPhone());
        target.setDeleted(source.getIsDeleted());
        target.setCreatedAt(source.getCreatedAt());
        target.setUpdatedAt(source.getUpdatedAt());
        return target;
    }
}
