package com.fit2081.nutritrack_timming_32619138.data.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fit2081.nutritrack_timming_32619138.data.patient.Patient
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: User)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAllUsers(users: List<User>)

//    @Query("SELECT * FROM users WHERE userId = :userId")
//    suspend fun getUserPrivateById(userId: String): User?


    @Query("SELECT userId, role FROM users WHERE userId = :userId")
    suspend fun getUserById(userId: String): UserPublic?

    @Query("SELECT userId, role FROM users WHERE userId = :userId AND password = :password")
    suspend fun login(userId: String, password: String): UserPublic?


    /**
     * Get all public users
     */
    @Query("SELECT userId, role FROM users")
    fun getAllUsers(): Flow<List<UserPublic>>

    /**
     * Get all registered users. Registered users are ones with non-empty password.
     */
    @Query("SELECT userId, role FROM users WHERE password != ''")
    fun getAllRegisteredUsers(): Flow<List<UserPublic>>

    /**
     *  Get all unregistered users. Unregistered users are ones with empty password.
     */
    @Query ("SELECT userId, role FROM users WHERE password == ''")
    fun getAllUnregisteredUsers(): Flow<List<UserPublic>>

    @Query("UPDATE users SET password = :newPassword WHERE userId = :userId")
    suspend fun updatePassword(userId: String, newPassword: String)

    @Query("SELECT COUNT(*) FROM users")
    suspend fun getUserCount(): Int
}