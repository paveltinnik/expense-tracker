package com.example.demo.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    var id: UUID = UUID.randomUUID(),

    @Column(nullable = false, length = 100)
    var login: String,

    @Column(name = "password_hash", nullable = false, length = 60)
    var passwordHash: String,

    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant = Instant.now()
)