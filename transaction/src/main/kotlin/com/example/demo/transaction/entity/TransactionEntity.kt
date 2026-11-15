package com.example.demo.transaction.entity

import com.example.demo.account.entity.AccountEntity
import com.example.demo.category.entity.CategoryEntity
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "transactions")
class TransactionEntity(
    @Id
    var id: UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    var account: AccountEntity,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    var category: CategoryEntity,

    @Column(nullable = false, precision = 19, scale = 4)
    var amount: BigDecimal,

    @Column(length = 500)
    var description: String? = null,

    @Column(name = "occurred_at", nullable = false)
    var occurredAt: Instant,

    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant = Instant.now(),
)