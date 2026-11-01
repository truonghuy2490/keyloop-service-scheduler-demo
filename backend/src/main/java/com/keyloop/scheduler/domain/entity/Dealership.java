package com.keyloop.scheduler.domain.entity;

import com.keyloop.scheduler.domain.common.SoftDeletableAuditableEntity;

public class Dealership extends SoftDeletableAuditableEntity {

    private String name;
    private String timeZone;

    protected Dealership() {
    }

    private Dealership(Builder builder) {
        super(builder);
        this.name = builder.name;
        this.timeZone = builder.timeZone;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
            extends SoftDeletableAuditableEntity.Builder<Builder> {

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

        public Dealership build() {
            return new Dealership(this);
        }
    }

    public String getName() {
        return name;
    }

    public String getTimeZone() {
        return timeZone;
    }
}