package com.fit2081.nutritrack_timming_32619138.data.user

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class for user table
 */
@Entity(tableName = "users")
data class User(
    @PrimaryKey val userId: String,
    val password: String,
    val role: UserRole
)