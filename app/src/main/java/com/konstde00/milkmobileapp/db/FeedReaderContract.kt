package com.konstde00.milkmobileapp.db

import android.provider.BaseColumns

object FeedReaderContract {
    object FeedEntry : BaseColumns {
        const val TABLE_NAME = "milk_production"
        const val MILK_ID = "id"
        const val MILK_YEAR = "year"
        const val MILK_PRODUCTION = "production"
        const val MILK_COST = "cost"
    }
}
