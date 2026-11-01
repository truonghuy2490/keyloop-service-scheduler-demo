package com.keyloop.scheduler.persistence.entity;

import com.keyloop.scheduler.persistence.entity.common.SoftDeletedAuditableJpaEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "dealership")
public class DealershipJpaEntity extends SoftDeletedAuditableJpaEntity {

    @Column(name = "name", nullable = false, length = 160)
    private String name;

    @Column(name = "time_zone", nullable = false, length = 64)
    private String timeZone;

    protected DealershipJpaEntity() {
    }

    private DealershipJpaEntity(Builder builder) {
        super(builder);
        this.name = builder.name;
        this.timeZone = builder.timeZone;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
            extends SoftDeletedAuditableJpaEntity.Builder<Builder> {

        private String name;
        private String timeZone;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder timeZone(String timeZone) {
            this.timeZone = timeZone;
            return this;
        }

        @Override
        protected Builder self() {
            return this;
        }

        public DealershipJpaEntity build() {
            return new DealershipJpaEntity(this);
        }
    }

    public String getName() {
        return name;
    }

    public String getTimeZone() {
        return timeZone;
    }
}