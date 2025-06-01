package com.fit2081.nutritrack_timming_32619138.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fit2081.nutritrack_timming_32619138.data.foodIntake.FoodIntake
import com.fit2081.nutritrack_timming_32619138.data.foodIntake.FoodIntakeDao
import com.fit2081.nutritrack_timming_32619138.data.nutriCoachTips.NutriCoachTip
import com.fit2081.nutritrack_timming_32619138.data.nutriCoachTips.NutriCoachTipDao
import com.fit2081.nutritrack_timming_32619138.data.patient.Patient
import com.fit2081.nutritrack_timming_32619138.data.patient.PatientDao
import com.fit2081.nutritrack_timming_32619138.data.user.User
import com.fit2081.nutritrack_timming_32619138.data.user.UserDao

@Database(
    entities = [User::class, Patient::class, FoodIntake::class, NutriCoachTip::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(TypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun patientDao(): PatientDao
    abstract fun foodIntakeDao(): FoodIntakeDao
    abstract fun userDao(): UserDao
    abstract fun nutriCoachTipDao(): NutriCoachTipDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun initialize(
            context: Context,
            csvFilePath: String
        ) {
            synchronized(this) {
                if (INSTANCE == null) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    )
                        .addCallback(DatabaseInitializer(context, csvFilePath))
                        .build()
                    INSTANCE = instance
                }
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE
                ?: throw IllegalStateException("Database not initialized. Call initialize() first.")
        }
    }
}