package com.aliyara.smartshop.model;


import com.aliyara.smartshop.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Entity
@Table (name = "users")
@SQLDelete(sql = "update users set deleted = true, deleted_at = NOW() where id = ?")
@SQLRestriction("deleted = false")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column (name = "first_name", nullable = false)
    private String firstName;

    @Column (name = "last_name")
    private String lastName;

    @Column (unique = true, nullable = false)
    private String email;

    @Column (name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void updateCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

}