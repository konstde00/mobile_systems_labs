package com.konstde00.milkmobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.konstde00.milkmobileapp.db.DBHelper
import com.konstde00.milkmobileapp.ui.screens.MainScreen
import com.konstde00.milkmobileapp.ui.theme.MilkAppTheme

class MainActivity : ComponentActivity() {

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dbHelper = DBHelper(this)

        setContent {
            MilkAppTheme {
                MainScreen(dbHelper)
            }
        }
    }
}
