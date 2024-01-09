package com.example.casestudyn11.data.model

import com.google.gson.annotations.SerializedName

data class SearchUserResponse(
    @SerializedName("total_count") val count: Int,
    @SerializedName("items") val users: List<UserApiModel>
)
