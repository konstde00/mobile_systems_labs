package com.konstde00.lab_1.data

import android.content.Context
import android.util.Log

object FileUtils {

    fun saveCoefficientsToFile(context: Context, coeffs: List<String>) {
        try {
            Log.d("SaveCoefficients", "Coefficients: ${coeffs}")
            val fileName = "coefficients.txt"
            val fileContents = coeffs.joinToString(separator = ",")
            context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
                it.write(fileContents.toByteArray())
            }
            Log.d("SaveCoefficients", "File saved successfully as $fileName")
        } catch (e: Exception) {
            Log.e("SaveCoefficients", "Error saving coefficients: ${e.message}", e)
        }
    }
}
