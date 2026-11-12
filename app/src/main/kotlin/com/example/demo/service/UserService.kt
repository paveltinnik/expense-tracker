package com.example.demo.service

import com.example.com.example.demo.entity.UserEntity
import com.example.demo.exception.NotFoundException
import com.example.com.example.demo.repository.UserRepository
import com.example.demo.controller.dto.user.RegisterUserRequest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    @Transactional
    fun register(req: RegisterUserRequest): UserEntity {
        if (userRepository.existsByLogin(req.login)) {
            throw IllegalArgumentException("Login already taken")
        }
        val user = UserEntity(
            login = req.login.trim(),
            passwordHash = passwordEncoder.encode(req.password) ?: ""
        )
        return userRepository.save(user)
    }

    @Transactional(readOnly = true)
    fun getById(id: UUID): UserEntity =
        userRepository.findById(id).orElseThrow { NotFoundException("User $id not found") }
}