package com.fit2081.nutritrack_timming_32619138.data.patient
import android.content.Context
import com.fit2081.nutritrack_timming_32619138.ErrorMessages
import com.fit2081.nutritrack_timming_32619138.data.AppDatabase
import kotlinx.coroutines.flow.Flow

class PatientRepository(context: Context) {
    private val patientDao: PatientDao = AppDatabase.getDatabase(context).patientDao()

    fun getAllPatients(): Flow<List<Patient>> = patientDao.getAllPatients()

    suspend fun getPatientById(userId: String): Patient? = patientDao.getPatientById(userId)

    suspend fun updatePatientName(userId: String, name: String): Result<Unit> {
        return try {
            Result.success(patientDao.updatePatientName(userId, name))
        } catch (_: Exception) {
            Result.failure(Exception(ErrorMessages.PATIENT_UPDATE_NAME_FAILED))
        }
    }

}

