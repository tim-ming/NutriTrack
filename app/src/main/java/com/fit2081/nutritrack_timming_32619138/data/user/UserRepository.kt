package com.fit2081.nutritrack_timming_32619138.data.user

import android.content.Context
import com.fit2081.nutritrack_timming_32619138.ErrorMessages
import com.fit2081.nutritrack_timming_32619138.data.AppDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository(context: Context) {
    private val userDao = AppDatabase.getDatabase(context).userDao()
    suspend fun register(user: User): Result<UserPublic> {
        try {
            userDao.updatePassword(user.userId, user.password)
        } catch (_: Exception) {
            return Result.failure(Exception(ErrorMessages.USER_ALREADY_EXISTS))
        }

        val registered = userDao.getUserById(user.userId)
        return if (registered != null) {
            Result.success(registered)
        } else {
            Result.failure(Exception(ErrorMessages.USER_NOT_FOUND))
        }
    }
//    suspend fun register(user: User): Result<UserPublic> {
//        try {
//            userDao.insertUser(user)
//        } catch (_: Exception) {
//            return Result.failure(Exception("User already exists"))
//        }
//
//        val inserted = userDao.getUserById(user.userId)
//        return if (inserted != null) {
//            Result.success(inserted)
//        } else {
//            Result.failure(Exception("User not found after insertion"))
//        }
//    }

    suspend fun login(userId: String, password: String): Result<UserPublic> {
        return try {
            val user = userDao.login(userId, password)
            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(Exception(ErrorMessages.PASSWORD_NOT_CORRECT))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updatePassword(userId: String, newPassword: String): Result<Unit> {
        return try {
            Result.success(userDao.updatePassword(userId, newPassword))
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    suspend fun getUserById(userId: String): UserPublic? {
        return userDao.getUserById(userId)
    }

    fun getAllUsers(): Flow<List<UserPublic>> {
        return userDao.getAllUsers()
    }

    fun getAllRegisteredUids(): Flow<List<String>> {
        return userDao.getAllRegisteredUsers().map { user -> user.map { it.userId } }
    }

    fun getAllUnregisteredUids(): Flow<List<String>> {
        return userDao.getAllUnregisteredUsers().map { user -> user.map { it.userId } }
    }
}