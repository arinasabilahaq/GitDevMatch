package com.arina.githubuser.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arina.githubuser.api.ApiConfig
import com.arina.githubuser.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel: ViewModel() {
    val followersList = MutableLiveData <ArrayList<User>>()

    fun setListFollowers(username: String) {
        val callback = object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                if (response.isSuccessful) {
                    followersList.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                val errorMessage = t.message
                errorMessage?.let { Log.e("FollowersViewModel", "Error: ${t.message}", t) }
            }
        }
        ApiConfig.getApiService().getFollowers(username).enqueue(callback)
    }

    fun getListFollowers (): LiveData<ArrayList<User>> {
        return followersList
    }
}