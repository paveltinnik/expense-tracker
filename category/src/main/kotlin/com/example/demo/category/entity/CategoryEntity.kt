package com.example.demo.category.entity

import com.example.demo.user.entity.UserEntity
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "categories")
class CategoryEntity(
    @Id
    var id: UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "user_id", nullable = true)
    var user: UserEntity? = null,

    @Column(nullable = false, length = 100)
    var name: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var type: CategoryType,
) {
    /** Системная — видна всем, нельзя удалить/изменить. */
    val isSystem: Boolean
        get() = user == null
}