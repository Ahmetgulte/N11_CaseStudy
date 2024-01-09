package com.example.casestudyn11.data.api

import com.example.casestudyn11.data.model.SearchUserResponse
import com.example.casestudyn11.data.model.UserApiModel
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    @GET("users")
    suspend fun getUsers(): List<UserApiModel>

    @GET("search/users")
    suspend fun getSearchUsers(
        @Query("q") query: String
    ): SearchUserResponse
}