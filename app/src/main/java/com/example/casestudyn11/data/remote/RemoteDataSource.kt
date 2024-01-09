package com.example.casestudyn11.data.remote

import com.example.casestudyn11.data.api.GithubService
import com.example.casestudyn11.data.model.SearchUserResponse
import com.example.casestudyn11.data.model.UserApiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val githubService: GithubService
) {
    suspend fun fetchUserList(): List<UserApiModel> = githubService.getUsers()


    suspend fun searchUsers(query: String): SearchUserResponse = githubService.getSearchUsers(query)
}
