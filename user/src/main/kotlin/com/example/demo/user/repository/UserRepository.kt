package com.example.demo.user.repository

import com.example.demo.user.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository : JpaRepository<UserEntity, UUID> {

    @Query("""
        SELECT u FROM UserEntity u
        WHERE u.login = :login
    """)
    fun findByLogin(@Param("login") login: String): UserEntity?

    @Query("""
        SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END
        FROM UserEntity u
        WHERE u.login = :login
    """)
    fun existsByLogin(@Param("login") login: String): Boolean
}