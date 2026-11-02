package com.example.demo.service

import com.example.demo.entity.UserEntity
import com.example.demo.repository.CategoryRepository
import jakarta.transaction.Transactional
import org.apache.catalina.User
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    @Transactional
    fun register(login: String, rawPassword: String): UserEntity {
        if (userRepository.existsByLogin(login)) {
            throw IllegalArgumentException("Login already taken")
        }
        val user = UserEntity(
            login = login,
            passwordHash = passwordEncoder.encode(rawPassword)  // ← BCrypt
        )
        return userRepository.save(user)
    }
}