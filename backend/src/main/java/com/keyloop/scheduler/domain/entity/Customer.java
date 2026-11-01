package com.keyloop.scheduler.domain.entity;

import com.keyloop.scheduler.domain.common.SoftDeletableAuditableEntity;


public class Customer extends SoftDeletableAuditableEntity {
    private String fullName;
    private String email;
    private String phone;
    public Customer() {
    }
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
