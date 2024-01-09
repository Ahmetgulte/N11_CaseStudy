package com.example.casestudyn11.data.repository

import com.example.casestudyn11.data.entity.UserEntity
import com.example.casestudyn11.data.local.LocalDataSource
import com.example.casestudyn11.data.model.UserApiModel
import com.example.casestudyn11.data.remote.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    suspend fun getUserList(): List<UserEntity> {
        val cachedUsers = localDataSource.getAllUsers()
        return if (cachedUsers.isEmpty()) {
            val users = remoteDataSource.fetchUserList()
            localDataSource.insertUsers(users)
            localDataSource.getAllUsers()
        } else {
            cachedUsers
        }
    }

     suspend fun getUserById(userId: Int) = localDataSource.getUserById(userId)

    suspend fun searchUsers(query: String): List<UserApiModel>{
        val searchResult = remoteDataSource.searchUsers(query)
        localDataSource.insertUsers(searchResult.users)

        return searchResult.users
    }

    suspend fun updateFavoriteState(userId: Int, isFavorite: Boolean) = withContext(Dispatchers.IO){
        localDataSource.updateFavoriteState(userId, isFavorite)
    }
}