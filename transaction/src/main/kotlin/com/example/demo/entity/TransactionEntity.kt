package com.example.demo.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.Instant
import java.util.*

@Entity
@Table(name = "transactions")
class TransactionEntity(
    @Id
    var id: UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    var account: AccountEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    var category: CategoryEntity? = null,

    @Column(nullable = false, precision = 19, scale = 2)
    var amount: BigDecimal,

    @Column(nullable = false, length = 3)
    var currency: String = "RUB",

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    var type: TransactionType,

    @Column(length = 500)
    var note: String? = null,

    @Column(name = "occurred_at", nullable = false)
    var occurredAt: Instant,

    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant = Instant.now()
)