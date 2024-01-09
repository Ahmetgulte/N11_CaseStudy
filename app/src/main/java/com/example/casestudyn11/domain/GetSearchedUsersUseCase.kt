package com.example.casestudyn11.domain

import com.example.casestudyn11.data.repository.GithubRepository
import com.example.casestudyn11.mapper.UserApiModelToUserUiModelMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSearchedUsersUseCase @Inject constructor(
    private val githubRepository: GithubRepository,
    private val apiModelToUserUiModelMapper: UserApiModelToUserUiModelMapper
) {
    suspend fun searchUsers(query: String) = withContext(Dispatchers.IO) {
        val userList = githubRepository.searchUsers(query)
        return@withContext userList.map { apiModelToUserUiModelMapper.map(it) }
    }
}