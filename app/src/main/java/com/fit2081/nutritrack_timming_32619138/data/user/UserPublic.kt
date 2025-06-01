package com.fit2081.nutritrack_timming_32619138.data.user

/**
 * Data class for user, excluding password for safety
 */
data class UserPublic(
    val userId: String,
    val role: String
)