package com.example.casestudyn11.domain

import com.example.casestudyn11.data.repository.GithubRepository
import com.example.casestudyn11.mapper.UserEntityToUserUiModel
import com.example.casestudyn11.ui.model.UserUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: GithubRepository,
    private val userEntityToUserUiModel: UserEntityToUserUiModel
) {
     suspend fun getUserById(userId: Int): UserUiModel = withContext(Dispatchers.IO){
        val user = repository.getUserById(userId)
        return@withContext userEntityToUserUiModel.map(user)
    }
}