package com.konstde00.milkmobileapp.db

import android.content.ContentValues.TAG
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.konstde00.milkmobileapp.db.FeedReaderContract.FeedEntry.MILK_ID
import com.konstde00.milkmobileapp.db.FeedReaderContract.FeedEntry.MILK_PRODUCTION
import com.konstde00.milkmobileapp.db.FeedReaderContract.FeedEntry.MILK_COST
import com.konstde00.milkmobileapp.db.FeedReaderContract.FeedEntry.MILK_YEAR
import com.konstde00.milkmobileapp.db.FeedReaderContract.FeedEntry.TABLE_NAME

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "milk.db"
        const val DATABASE_VERSION = 1

        const val SQL_CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
                "$MILK_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$MILK_YEAR INTEGER, " +
                "$MILK_COST REAL, " +
                "$MILK_PRODUCTION REAL)"

        const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    override fun onCreate(db: SQLiteDatabase) {
        Log.d(TAG, "onCreate: $SQL_CREATE_TABLE")
        db.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_TABLE)
        onCreate(db)
    }
}
