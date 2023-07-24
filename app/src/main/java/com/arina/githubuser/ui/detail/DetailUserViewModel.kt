package com.arina.githubuser.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arina.githubuser.api.ApiConfig
import com.arina.githubuser.data.database.FavoriteUser
import com.arina.githubuser.data.database.UserDatabase
import com.arina.githubuser.data.model.DetailUserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<DetailUserResponse>()

    private val userDb by lazy { UserDatabase.getDatabase(application) }
    private val userDao by lazy { userDb.favoriteUserDao() }

    fun setUserDetail(username: String) {
        val callback = object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if (response.isSuccessful) {
                    user.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                val errorMessage = t.message
                errorMessage?.let { Log.e("DetailUserViewModel", "Error: ${t.message}", t) }
            }
        }
        ApiConfig.getApiService().getUserDetail(username).enqueue(callback)
    }

    fun getUserDetail(): LiveData<DetailUserResponse> {
        return user
    }

    fun addToFavorite(username: String, id: Int, avatarUrl: String) {
        val user = FavoriteUser(username, id, avatarUrl)
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.addFavoriteUser(user)
        }
    }

    fun removeFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)
}