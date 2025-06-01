package com.fit2081.nutritrack_timming_32619138.data
import android.content.Context
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.fit2081.nutritrack_timming_32619138.data.patient.Patient
import com.fit2081.nutritrack_timming_32619138.data.user.User
import com.fit2081.nutritrack_timming_32619138.data.user.UserRole
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

class DatabaseInitializer(
    private val context: Context,
    private val csvFilePath: String
) : RoomDatabase.Callback() {

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        // This is only called when the app is launched! Because it is a
        // RoomDatabase.Callback() type. Thus part of the lifecycle
        // GlobalScope is fine, because it is only called once
        GlobalScope.launch(Dispatchers.IO) {
            Log.d("DatabaseInitializer", "onCreate called")
            initializeFromCsv(context, csvFilePath)
        }
    }

    private suspend fun initializeFromCsv(context: Context, csvFilePath: String) {
        try {
            val patientDao = AppDatabase.getDatabase(context).patientDao()
            val userDao = AppDatabase.getDatabase(context).userDao()
            val inputStream = context.assets.open(csvFilePath)
            val reader = BufferedReader(InputStreamReader(inputStream))

            // prevent re-initialisation!
            if (patientDao.getPatientCount() > 0 && userDao.getUserCount() > 0) {
                Log.e("DatabaseInitializer", "Tried to initialize already initialized DB!")
                return
            }

            val headers = withContext(Dispatchers.IO) {
                reader.readLine()
            }.split(",")

            val patients = reader.useLines { lines ->
                lines.map { line ->
                    val values = line.split(",")
                    val rowMap = headers.zip(values).toMap()
                    createPatientFromMap(rowMap)
                }.toList()
            }
            val users = patients.map{
                patient -> User(patient.userId, "", UserRole.PATIENT)
            }
            if (patients.isNotEmpty()) {
                // must insert user first, because userid in patients table is a foreign key
                userDao.insertAllUsers(users)
                Log.d("DatabaseInitializer", "Inserted ${users.size} users")
                patientDao.insertAllPatients(patients)
                Log.d("DatabaseInitializer", "Inserted ${patients.size} patients")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun createPatientFromMap(row: Map<String, String>): Patient {
        val userId = row["User_ID"] ?: ""
        val phoneNumber = row["PhoneNumber"] ?: ""
        val sex = row["Sex"] ?: ""

        fun parseFloat(key: String): Float = row[key]!!.toFloat()

        return Patient(
            userId = userId,
            phoneNumber = phoneNumber,
            name = "", // not in CSV
            sex = sex,

            heifaTotalScore = parseFloat("HEIFAtotalscore$sex"),

            discretionaryHeifaScore = parseFloat("DiscretionaryHEIFAscore$sex"),
            discretionaryServeSize = parseFloat("Discretionaryservesize"),

            vegetablesHeifaScore = parseFloat("VegetablesHEIFAscore$sex"),
            vegetablesWithLegumesAllocatedServeSize = parseFloat("Vegetableswithlegumesallocatedservesize"),
            legumesAllocatedVegetables = parseFloat("LegumesallocatedVegetables"),
            vegetablesVariationsScore = parseFloat("Vegetablesvariationsscore"),
            vegetablesCruciferous = parseFloat("VegetablesCruciferous"),
            vegetablesTubeAndBulb = parseFloat("VegetablesTuberandbulb"),
            vegetablesOther = parseFloat("VegetablesOther"),
            legumes = parseFloat("Legumes"),
            vegetablesGreen = parseFloat("VegetablesGreen"),
            vegetablesRedAndOrange = parseFloat("VegetablesRedandorange"),

            fruitHeifaScore = parseFloat("FruitHEIFAscore$sex"),
            fruitServeSize = parseFloat("Fruitservesize"),
            fruitVariationsScore = parseFloat("Fruitvariationsscore"),
            fruitPome = parseFloat("FruitPome"),
            fruitTropicalAndSubtropical = parseFloat("FruitTropicalandsubtropical"),
            fruitBerry = parseFloat("FruitBerry"),
            fruitStone = parseFloat("FruitStone"),
            fruitCitrus = parseFloat("FruitCitrus"),
            fruitOther = parseFloat("FruitOther"),

            grainsAndCerealsHeifaScore = parseFloat("GrainsandcerealsHEIFAscore$sex"),
            grainsAndCerealsServeSize = parseFloat("Grainsandcerealsservesize"),
            grainsAndCerealsNonWholeGrains = parseFloat("GrainsandcerealsNonwholegrains"),

            wholeGrainsHeifaScore = parseFloat("WholegrainsHEIFAscore$sex"),
            wholeGrainsServeSize = parseFloat("Wholegrainsservesize"),

            meatAndAlternativesHeifaScore = parseFloat("MeatandalternativesHEIFAscore$sex"),
            meatAndAlternativesWithLegumesAllocatedServeSize = parseFloat("Meatandalternativeswithlegumesallocatedservesize"),
            legumesAllocatedMeatAndAlternatives = parseFloat("LegumesallocatedMeatandalternatives"),

            dairyAndAlternativesHeifaScore = parseFloat("DairyandalternativesHEIFAscore$sex"),
            dairyAndAlternativesServeSize = parseFloat("Dairyandalternativesservesize"),

            sodiumHeifaScore = parseFloat("SodiumHEIFAscore$sex"),
            sodiumMgMilligrams = parseFloat("Sodiummgmilligrams"),

            alcoholHeifaScore = parseFloat("AlcoholHEIFAscore$sex"),
            alcoholStandardDrinks = parseFloat("Alcoholstandarddrinks"),

            waterHeifaScore = parseFloat("WaterHEIFAscore$sex"),
            water = parseFloat("Water"),
            waterTotalMl = parseFloat("WaterTotalmL"),
            beverageTotalMl = parseFloat("BeverageTotalmL"),

            sugarHeifaScore = parseFloat("SugarHEIFAscore$sex"),
            sugar = parseFloat("Sugar"),

            saturatedFatHeifaScore = parseFloat("SaturatedFatHEIFAscore$sex"),
            saturatedFat = parseFloat("SaturatedFat"),

            unsaturatedFatHeifaScore = parseFloat("UnsaturatedFatHEIFAscore$sex"),
            unsaturatedFatServeSize = parseFloat("UnsaturatedFatservesize")
        )
    }
}