package com.keyloop.scheduler.persistence.repository;

import java.util.UUID;

import com.keyloop.scheduler.persistence.entity.AppointmentJpaEntity;

public interface AppointmentJpaRepository
        extends BaseJpaRepository<AppointmentJpaEntity, UUID> {
}