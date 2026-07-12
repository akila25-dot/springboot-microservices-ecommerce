package com.authservice.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 64)
    private String username;

    @Column(unique = true, nullable = false, length = 128)
    private String email;

    // ✅ Rename field to "passwordHash" for clarity
    @Column(name = "password_hash", nullable = false, length = 128)
    private String passwordHash;

    @Column(nullable = false, length = 32)
    private String role; // values: USER or ADMIN

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true; // default to true
}
