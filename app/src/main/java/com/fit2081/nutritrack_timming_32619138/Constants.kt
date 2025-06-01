package com.fit2081.nutritrack_timming_32619138

const val WELCOME_DISCLAIMER =
    "This app provides general health and nutrition information for educational purposes only. It is not intended as medical advice, diagnosis, or treatment. Always consult a qualified healthcare professional before making any changes to your diet, exercise, or health regimen.\n\nUse this app at your own risk.\n\nIf you’d like to consult an Accredited Practicing Dietitian (APD), please visit the Monash Nutrition/Dietetics Clinic (discounted rates for students):"

const val WELCOME_MONASH_LINK = "https://www.monash.edu/medicine/scs/nutrition/clinics/nutrition"

const val CLINICIAN_PASSPHRASE = "dollar-entry-apples"

object ErrorMessages {
    const val NOT_LOGGED_IN = "User is not logged in."
    const val PATIENT_NOT_FOUND = "Patient not found."
    const val MISSING_UID_PASSWORD = "Missing user ID or password."
    const val PHONE_NUMBER_NOT_MATCH = "Phone number does not match patient's record."
    const val PHONE_NUMBER_EMPTY = "Phone number cannot be empty."
    const val REGISTRATION_FAILED = "Registration failed."
    const val PASSWORD_TOO_SHORT = "Password must be at least 8 characters long."
    const val PASSWORD_NOT_MATCH = "Passwords do not match."
    const val PASSWORD_NOT_CONFIRM = "Please confirm your password."
    const val USER_ALREADY_EXISTS = "User already exists."
    const val USER_NOT_FOUND = "User not found."
    const val NAME_EMPTY = "Name cannot be empty"
    const val PATIENT_UPDATE_NAME_FAILED = "Failed to update patient's name"
    const val NAME_NOT_MEET_CONDITION = "Name can only contain letters, spaces, hyphens, and apostrophes"
    const val INVALID_INPUT = "Invalid input."
    const val NETWORK_ERROR = "Network request failed."
    const val MISSING_REGISTRATION_DETAILS = "Please fill in all registration details"
    const val PHONE_NUMBER_NOT_DIGITS = "Phone number must contain only digits"
    const val PASSWORD_NOT_CORRECT = "Incorrect password."
    const val PASSPHRASE_NOT_CORRECT = "Incorrect passphrase."
    const val UNKNOWN_ERROR = "Unknown error"
}