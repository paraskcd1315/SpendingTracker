package com.paraskcd.spendingtracker.screens.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paraskcd.spendingtracker.model.settings.MainSettingsTable
import com.paraskcd.spendingtracker.repository.settings.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: SettingsRepository): ViewModel() {
    private var _settingsDatabase: MutableStateFlow<List<MainSettingsTable>> = MutableStateFlow(emptyList())
    var settingsDatabase: StateFlow<List<MainSettingsTable>> = _settingsDatabase.asStateFlow()

    private var _settingsById: MutableStateFlow<MainSettingsTable?> = MutableStateFlow(null)
    var settingsById: StateFlow<MainSettingsTable?> = _settingsById.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllSettings()
        }
    }

    suspend fun getAllSettings() {
        repository.getAllSettings().distinctUntilChanged().collect {
                listOfAllSettings ->
            if (listOfAllSettings.isEmpty()) {
                _settingsDatabase.value = emptyList()
            } else {
                _settingsDatabase.value = listOfAllSettings
            }
        }
    }

    suspend fun getSettingsById(id: String) {
        repository.getSettingById(id).distinctUntilChanged().collect {
            settingById ->
            if (settingById != null) {
                _settingsById.value = settingById
            } else {
                _settingsById.value = null
            }
        }
    }

    fun saveSetting(setting: MainSettingsTable) = viewModelScope.launch(Dispatchers.IO) {
        repository.saveSettings(setting)
        repository.getAllSettings()
    }

    fun updateSetting(setting: MainSettingsTable) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateSettings(setting)
        repository.getAllSettings()
    }

    fun deleteSetting(setting: MainSettingsTable) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteSettings(setting)
        repository.getAllSettings()
    }
}