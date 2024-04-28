package com.paraskcd.spendingtracker.repository.settings

import com.paraskcd.spendingtracker.data.settings.SettingsDatabaseDao
import com.paraskcd.spendingtracker.model.incomes.IncomeTable
import com.paraskcd.spendingtracker.model.settings.MainSettingsTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SettingsRepository @Inject constructor(private val settingsDatabaseDao: SettingsDatabaseDao) {
    suspend fun saveSettings(setting: MainSettingsTable) = settingsDatabaseDao.save(setting)
    suspend fun updateSettings(setting: MainSettingsTable) = settingsDatabaseDao.update(setting)
    suspend fun deleteSettings(setting: MainSettingsTable) = settingsDatabaseDao.delete(setting)
    fun getSettingById(id: String) = settingsDatabaseDao.getBySettingId(id).flowOn(Dispatchers.IO).conflate()
    fun getAllSettings() = settingsDatabaseDao.getAll().flowOn(Dispatchers.IO).conflate()
}