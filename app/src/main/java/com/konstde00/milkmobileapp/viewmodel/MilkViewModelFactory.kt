package com.konstde00.milkmobileapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.konstde00.milkmobileapp.db.DBHelper
import com.konstde00.milkmobileapp.db.DBManager

class MilkViewModelFactory(private val dbHelper: DBHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MilkViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MilkViewModel(dbHelper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
