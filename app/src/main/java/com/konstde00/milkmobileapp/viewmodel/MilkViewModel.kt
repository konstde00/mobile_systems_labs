package com.konstde00.milkmobileapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konstde00.milkmobileapp.db.DBHelper
import com.konstde00.milkmobileapp.db.DBManager
import com.konstde00.milkmobileapp.model.MilkProduction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MilkViewModel(dbHelper: DBHelper) : ViewModel() {

    private val _milks = MutableStateFlow<List<MilkProduction>>(emptyList())
    val milks: StateFlow<List<MilkProduction>> get() = _milks
    private val dbManager = DBManager(dbHelper)
    private val _averageProduction = MutableStateFlow(0.0)

    private val _isFilterEnabled = MutableStateFlow(false)
    val isFilterEnabled: StateFlow<Boolean> get() = _isFilterEnabled
    init {
        loadMilks()
    }

    private fun loadMilks() {
        viewModelScope.launch {
            val milks = if (_isFilterEnabled.value) {
                dbManager.getProductionAbove(11000000.0)
            } else {
                dbManager.getAll()
            }
            _milks.value = milks
            _averageProduction.value = dbManager.getAverageProduction()
        }
    }

    fun setFilter(enabled: Boolean) {
        viewModelScope.launch {
            _isFilterEnabled.value = enabled
            loadMilks()
        }
    }

    fun insertMilk(milk: MilkProduction) {
        viewModelScope.launch {
            dbManager.insert(milk)
        }
        loadMilks()
    }

    fun updateMilk(milk: MilkProduction) {
        viewModelScope.launch {
            dbManager.update(milk)
        }
        loadMilks()
    }

    fun deleteMilk(id: Int) {
        viewModelScope.launch {
            dbManager.delete(id)
        }
        loadMilks()
    }

    fun getMilkById(id: Int): MilkProduction? {
        return _milks.value.find { it.id == id }
    }
}
