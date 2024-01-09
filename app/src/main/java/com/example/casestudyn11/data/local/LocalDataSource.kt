package com.example.casestudyn11.data.local

import com.example.casestudyn11.data.entity.UserEntity
import com.example.casestudyn11.data.model.UserApiModel
import com.example.casestudyn11.database.UserDao
import com.example.casestudyn11.mapper.UserApiModelToUserEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val userDao: UserDao,
    private val userApiModelToUserEntity: UserApiModelToUserEntity
) {

    fun insertUsers(users: List<UserApiModel>) {
        val entityUsers = users.map { userApiModelToUserEntity.map(it) }
        userDao.insertUsers(entityUsers)
    }

    fun getAllUsers(): List<UserEntity> = userDao.getUsers()


    suspend fun getUserById(userId: Int): UserEntity = userDao.getUserById(userId)

    fun updateFavoriteState(userId: Int, isFavorite: Boolean) {
        userDao.updateFavorite(userId, isFavorite)
    }
}