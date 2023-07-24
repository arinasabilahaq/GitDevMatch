package com.arina.githubuser.data.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("items")
    val items: ArrayList<User>
)

data class User(
    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("avatar_url")
    val avatar_url: String
)

data class DetailUserResponse(
    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("avatar_url")
    val avatar_url: String,

    @field:SerializedName("followers_url")
    val followers_url : String,

    @field:SerializedName("following_url")
    val following_url : String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("following")
    val following: Int,

    @field:SerializedName("followers")
    val followers: Int
)


