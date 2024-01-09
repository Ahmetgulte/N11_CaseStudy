package com.example.casestudyn11.domain

import com.example.casestudyn11.data.repository.GithubRepository
import com.example.casestudyn11.mapper.UserEntityToUserUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetUserListUseCase @Inject constructor(
    private val repository: GithubRepository,
    private val userEntityToUserUiModel: UserEntityToUserUiModel
) {

    suspend fun getUserList() = withContext(Dispatchers.IO) {
        val userList = repository.getUserList()
        return@withContext userList.map { user ->
            userEntityToUserUiModel.map(
                user
            )
        }
    }
}