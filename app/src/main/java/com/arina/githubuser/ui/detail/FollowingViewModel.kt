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

class FollowingViewModel : ViewModel() {
    private val followingDataList = MutableLiveData<ArrayList<User>>()

    fun  setListFollowing(username: String) {
        val callback = object : Callback<ArrayList<User>> {
            override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                if (response.isSuccessful) {
                    followingDataList.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                val errorMessage = t.message
                errorMessage?.let { Log.e("FollowingViewModel", "Error: ${t.message}", t) }
            }
        }
        ApiConfig.getApiService().getFollowing(username).enqueue(callback)
    }

    fun getListFollowing(): LiveData<ArrayList<User>> {
        return followingDataList
    }
}