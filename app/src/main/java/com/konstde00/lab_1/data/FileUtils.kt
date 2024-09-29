package com.konstde00.lab_1.data

import android.content.Context
import android.util.Log
import java.io.File

object FileUtils {

    private const val FILE_NAME = "coeffs.txt"

    fun saveCoefficientsToFile(context: Context, coeffs: List<String>) {
        try {
            Log.d("SaveCoefficients", "Coefficients: $coeffs")
            val fileContents = coeffs.joinToString(separator = ",")
            context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use {
                it.write(fileContents.toByteArray())
            }
            Log.d("SaveCoefficients", "File saved successfully as $FILE_NAME")
        } catch (e: Exception) {
            Log.e("SaveCoefficients", "Error saving coefficients: ${e.message}", e)
        }
    }

    fun loadCoefficientsFromFile(context: Context): List<String>? {
        return try {
            val file = context.getFileStreamPath(FILE_NAME)
            if (!file.exists()) {
                Log.e("LoadCoefficients", "File $FILE_NAME does not exist.")
                null
            } else {
                context.openFileInput(FILE_NAME).bufferedReader().use { reader ->
                    val content = reader.readText().trim()
                    if (content.isEmpty()) {
                        Log.e("LoadCoefficients", "File $FILE_NAME is empty.")
                        null
                    } else {
                        content.split(",").map { it.trim() }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("LoadCoefficients", "Error loading coefficients: ${e.message}", e)
            null
        }
    }

    fun clearCoefficientsFile(context: Context) {
        try {
            val file = File(context.filesDir, FILE_NAME)
            if (file.exists()) {
                val deleted = file.delete()
                if (deleted) {
                    Log.d("ClearCoefficients", "File $FILE_NAME deleted successfully.")
                } else {
                    Log.e("ClearCoefficients", "Failed to delete file $FILE_NAME.")
                }
            } else {
                Log.e("ClearCoefficients", "File $FILE_NAME does not exist.")
            }
        } catch (e: Exception) {
            Log.e("ClearCoefficients", "Error deleting coefficients file: ${e.message}", e)
        }
    }
}
