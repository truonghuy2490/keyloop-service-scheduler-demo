package com.keyloop.scheduler.persistence.adapter;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.keyloop.scheduler.domain.entity.Appointment;
import com.keyloop.scheduler.persistence.entity.AppointmentJpaEntity;
import com.keyloop.scheduler.persistence.repository.AppointmentJpaRepository;

@Repository
public class AppointmentRepositoryAdapter
        extends BaseRepositoryAdapter<
            Appointment,
            AppointmentJpaEntity,
            UUID> {

    public AppointmentRepositoryAdapter(
            AppointmentJpaRepository appointmentRepository) {
        super(appointmentRepository);
    }

    @Override
    protected Appointment toDomain(AppointmentJpaEntity source) {
        if (source == null) {
            return null;
        }

        Appointment target = new Appointment();

        // Inherited properties
        target.setId(source.getId());
        target.setIsDeleted(source.isDeleted());
        target.setCreatedAt(source.getCreatedAt());
        target.setUpdatedAt(source.getUpdatedAt());

        // Appointment properties
        target.setCustomerId(source.getCustomerId());
        target.setVehicleId(source.getVehicleId());
        target.setDealershipId(source.getDealershipId());
        target.setServiceTypeId(source.getServiceTypeId());
        target.setTechnicianId(source.getTechnicianId());
        target.setServiceBayId(source.getServiceBayId());
        target.setStartTime(source.getStartTime());
        target.setEndTime(source.getEndTime());
        target.setStatus(source.getStatus());
        target.setVersion(source.getVersion());
        target.setCancellationReason(source.getCancellationReason());

        return target;
    }

    @Override
    protected AppointmentJpaEntity toPersistence(Appointment source) {
        if (source == null) {
            return null;
        }

        AppointmentJpaEntity target = new AppointmentJpaEntity();

        // Inherited properties
        target.setId(source.getId());
        target.setDeleted(source.getIsDeleted());
        target.setCreatedAt(source.getCreatedAt());
        target.setUpdatedAt(source.getUpdatedAt());

        // Appointment properties
        target.setCustomerId(source.getCustomerId());
        target.setVehicleId(source.getVehicleId());
        target.setDealershipId(source.getDealershipId());
        target.setServiceTypeId(source.getServiceTypeId());
        target.setTechnicianId(source.getTechnicianId());
        target.setServiceBayId(source.getServiceBayId());
        target.setStartTime(source.getStartTime());
        target.setEndTime(source.getEndTime());
        target.setStatus(source.getStatus());
        target.setVersion(source.getVersion());
        target.setCancellationReason(source.getCancellationReason());

        return target;
    }
}