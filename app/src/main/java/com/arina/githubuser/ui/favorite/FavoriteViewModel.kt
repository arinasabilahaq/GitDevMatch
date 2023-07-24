package com.arina.githubuser.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.arina.githubuser.data.database.FavoriteUser
import com.arina.githubuser.data.database.FavoriteUserDao
import com.arina.githubuser.data.database.UserDatabase

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao: FavoriteUserDao by lazy {
        UserDatabase.getDatabase(application).favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>? = userDao.getFavoriteUser()
}