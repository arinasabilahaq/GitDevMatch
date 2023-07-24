package com.arina.githubuser.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.arina.githubuser.databinding.ActivitySettingModeBinding
import kotlinx.coroutines.launch

class SettingMode : AppCompatActivity() {

    private lateinit var binding: ActivitySettingModeBinding
    private lateinit var viewModel: MainViewModel2
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingModeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Setting"

        viewModel = ViewModelProvider(this)[MainViewModel2::class.java]
        dataStoreManager = DataStoreManager(this)

        checkThemeMode()

        binding.apply {
            modeSwitch.setOnCheckedChangeListener { _, isChecked ->
                lifecycleScope.launch {
                    when (isChecked) {
                        true -> viewModel.setTheme(true)
                        false -> viewModel.setTheme(false)

                    }
                }
            }
        }
    }

    private fun checkThemeMode() {
        binding.apply {
            viewModel.getTheme.observe(this@SettingMode) {
                isDarkMode ->
                val mode = if (isDarkMode) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
                AppCompatDelegate.setDefaultNightMode(mode)

                lifecycleScope.launch {
                    dataStoreManager.setTheme(isDarkMode)
                }

                modeSwitch.isChecked = isDarkMode
                modeSwitch.text = if (isDarkMode) "Dark Mode" else "Light Mode"

                viewModel.setTheme(isDarkMode)
            }
        }
    }
}