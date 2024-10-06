package com.konstde00.milkmobileapp.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.konstde00.milkmobileapp.db.DBHelper
import com.konstde00.milkmobileapp.db.FeedReaderContract.FeedEntry
import com.konstde00.milkmobileapp.model.MilkProduction

class MilkRepository(context: Context) {

    private val dbHelper = DBHelper(context.applicationContext)

    fun getAll(): List<MilkProduction> {
        val db = dbHelper.readableDatabase
        val projection = arrayOf(
            FeedEntry.MILK_ID,
            FeedEntry.MILK_YEAR,
            FeedEntry.MILK_PRODUCTION,
            FeedEntry.MILK_COST
        )

        val cursor: Cursor = db.query(
            FeedEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            "${FeedEntry.MILK_YEAR} ASC"
        )

        val milkList = mutableListOf<MilkProduction>()
        with(cursor) {
            while (moveToNext()) {
                val milk = MilkProduction(
                    id = getInt(getColumnIndexOrThrow(FeedEntry.MILK_ID)),
                    year = getInt(getColumnIndexOrThrow(FeedEntry.MILK_YEAR)),
                    production = getDouble(getColumnIndexOrThrow(FeedEntry.MILK_PRODUCTION)),
                    cost = getDouble(getColumnIndexOrThrow(FeedEntry.MILK_COST))
                )
                milkList.add(milk)
            }
            close()
        }
        return milkList
    }

    fun insert(milk: MilkProduction) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(FeedEntry.MILK_YEAR, milk.year)
            put(FeedEntry.MILK_PRODUCTION, milk.production)
            put(FeedEntry.MILK_COST, milk.cost)
        }
        db.insert(FeedEntry.TABLE_NAME, null, values)
    }

    fun update(milk: MilkProduction) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(FeedEntry.MILK_YEAR, milk.year)
            put(FeedEntry.MILK_PRODUCTION, milk.production)
            put(FeedEntry.MILK_COST, milk.cost)
        }
        val selection = "${FeedEntry.MILK_ID} = ?"
        val selectionArgs = arrayOf(milk.id.toString())
        db.update(FeedEntry.TABLE_NAME, values, selection, selectionArgs)
    }

    fun delete(milk: MilkProduction) {
        val db = dbHelper.writableDatabase
        val selection = "${FeedEntry.MILK_ID} = ?"
        val selectionArgs = arrayOf(milk.id.toString())
        db.delete(FeedEntry.TABLE_NAME, selection, selectionArgs)
    }

    fun getProductionAbove(amount: Double): List<MilkProduction> {
        val db = dbHelper.readableDatabase
        val projection = arrayOf(
            FeedEntry.MILK_ID,
            FeedEntry.MILK_YEAR,
            FeedEntry.MILK_PRODUCTION,
            FeedEntry.MILK_COST
        )
        val selection = "${FeedEntry.MILK_PRODUCTION} > ?"
        val selectionArgs = arrayOf(amount.toString())

        val cursor: Cursor = db.query(
            FeedEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            "${FeedEntry.MILK_YEAR} ASC"
        )

        val milkList = mutableListOf<MilkProduction>()
        with(cursor) {
            while (moveToNext()) {
                val milk = MilkProduction(
                    id = getInt(getColumnIndexOrThrow(FeedEntry.MILK_ID)),
                    year = getInt(getColumnIndexOrThrow(FeedEntry.MILK_YEAR)),
                    production = getDouble(getColumnIndexOrThrow(FeedEntry.MILK_PRODUCTION)),
                    cost = getDouble(getColumnIndexOrThrow(FeedEntry.MILK_COST))
                )
                milkList.add(milk)
            }
            close()
        }
        return milkList
    }

    fun getAverageProduction(): Double {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT AVG(${FeedEntry.MILK_PRODUCTION}) FROM ${FeedEntry.TABLE_NAME}", null)
        var average = 0.0
        if (cursor.moveToFirst()) {
            average = cursor.getDouble(0)
        }
        cursor.close()
        return average
    }
}
