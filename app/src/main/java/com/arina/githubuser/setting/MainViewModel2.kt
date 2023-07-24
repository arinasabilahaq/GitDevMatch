package com.arina.githubuser.setting

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel2(application: Application) : AndroidViewModel(application) {
    private val dataStore = DataStoreManager(application)

    val getTheme = dataStore.getTheme().asLiveData()

    fun setTheme(isDarkMode: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.setTheme(isDarkMode)
        }
    }
}