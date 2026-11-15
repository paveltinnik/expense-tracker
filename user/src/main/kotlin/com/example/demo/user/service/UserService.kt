package com.example.demo.user.service

import com.example.demo.common.exception.ConflictException
import com.example.demo.user.controller.dto.RegisterUserRequest
import com.example.demo.user.entity.UserEntity
import com.example.demo.common.exception.NotFoundException
import com.example.demo.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    @Transactional
    fun register(req: RegisterUserRequest): UserEntity {
        if (userRepository.existsByLogin(req.login)) {
            throw ConflictException("Login '${req.login}' already exists")
        }

        return userRepository.save(
            UserEntity(
                login = req.login,
                passwordHash = passwordEncoder.encode(req.password) ?: "",
            )
        )
    }

    fun getById(id: UUID): UserEntity =
        userRepository.findById(id)
            .orElseThrow { NotFoundException("User not found: $id") }
}