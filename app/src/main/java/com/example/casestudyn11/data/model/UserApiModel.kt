package com.example.casestudyn11.data.model

import com.google.gson.annotations.SerializedName

data class UserApiModel(
    @SerializedName("login")
    val login: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("avatar_url")
    val avatarUrl: String?
)
