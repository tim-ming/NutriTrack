package com.fit2081.nutritrack_timming_32619138.data

import PersonaType
import androidx.room.TypeConverter
import com.fit2081.nutritrack_timming_32619138.data.user.UserRole
import java.time.LocalDateTime

class TypeConverter {
    @TypeConverter
    fun fromUserRole(value: UserRole): String = value.name

    @TypeConverter
    fun toUserRole(value: String): UserRole = UserRole.valueOf(value)

    @TypeConverter
    fun fromLocalDateTime(time: LocalDateTime): String = time.toString()

    @TypeConverter
    fun toLocalDateTime(value: String): LocalDateTime = LocalDateTime.parse(value)

    @TypeConverter
    fun fromPersonaType(type: PersonaType): String = type.name

    @TypeConverter
    fun toPersonaType(name: String): PersonaType = PersonaType.valueOf(name)
}