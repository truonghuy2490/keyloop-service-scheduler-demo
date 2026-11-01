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

        return Appointment.builder()
                .id(source.getId())
                .isDeleted(source.isDeleted())
                .createdAt(source.getCreatedAt())
                .updatedAt(source.getUpdatedAt())

                .customerId(source.getCustomerId())
                .vehicleId(source.getVehicleId())
                .dealershipId(source.getDealershipId())
                .serviceTypeId(source.getServiceTypeId())
                .technicianId(source.getTechnicianId())
                .serviceBayId(source.getServiceBayId())

                .startTime(source.getStartTime())
                .endTime(source.getEndTime())

                .status(source.getStatus())
                .version(source.getVersion())
                .cancellationReason(source.getCancellationReason())
                .build();
    }

    @Override
    protected AppointmentJpaEntity toPersistence(Appointment source) {

        if (source == null) {
            return null;
        }

        return AppointmentJpaEntity.builder()
                .id(source.getId())
                .deleted(source.getIsDeleted())

                .customerId(source.getCustomerId())
                .vehicleId(source.getVehicleId())
                .dealershipId(source.getDealershipId())
                .serviceTypeId(source.getServiceTypeId())
                .technicianId(source.getTechnicianId())
                .serviceBayId(source.getServiceBayId())

                .startTime(source.getStartTime())
                .endTime(source.getEndTime())

                .status(source.getStatus())
                .version(source.getVersion())
                .cancellationReason(source.getCancellationReason())
                .build();
    }
}