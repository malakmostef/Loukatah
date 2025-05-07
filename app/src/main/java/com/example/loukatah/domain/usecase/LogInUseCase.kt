package com.example.loukatah.domain.usecase
import com. example. loukatah.data.repository.AuthRepositoryImpl
import com.example.loukatah.repository.AuthRepository
import javax.inject.Inject

class LogInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return authRepository.logIn(email, password)
    }
}