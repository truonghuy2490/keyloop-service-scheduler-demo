package com.keyloop.scheduler.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "customer")
public class CustomerJpaEntity extends SoftDeletedAuditableJpaEntity {
    @Column(name = "full_name", nullable = false, length = 160)
    private String fullName;

    @Column(name = "email", nullable = false, length = 254)
    private String email;

    @Column(name = "phone", length = 40)
    private String phone;

    public CustomerJpaEntity() {}

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

}
