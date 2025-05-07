package com.example.loukatah.repository

import com.example.loukatah.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

//class AuthRepository @Inject constructor(
//    private val firebaseAuth: FirebaseAuth
//) {
//    fun login(email: String, password: String): Flow<Resource<FirebaseUser?>> = flow {
//        emit(Resource.Loading())
//        try {
//            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
//            emit(Resource.Success(result.user))
//        } catch (e: Exception) {
//            emit(Resource.Error(e.message ?: "Login failed"))
//        }
//    }
//
//    fun signup(email: String, password: String): Flow<Resource<FirebaseUser?>> = flow {
//        emit(Resource.Loading())
//        try {
//            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
//            emit(Resource.Success(result.user))
//        } catch (e: Exception) {
//            emit(Resource.Error(e.message ?: "Signup failed"))
//        }
//    }
//
//    fun logout() {
//        firebaseAuth.signOut()
//    }
//}
interface AuthRepository {
    val currentUser: FirebaseUser?
    val authState: Flow<Boolean> // تدفق لحالة المصادقة

    suspend fun logIn(email: String, password: String): Result<Unit>
    suspend fun signUp(email: String, password: String): Result<Unit>
    fun signOut()
}
