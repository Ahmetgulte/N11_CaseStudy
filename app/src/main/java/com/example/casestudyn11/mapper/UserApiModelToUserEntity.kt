package com.example.casestudyn11.mapper

import com.example.casestudyn11.base.Mapper
import com.example.casestudyn11.data.entity.UserEntity
import com.example.casestudyn11.data.model.UserApiModel
import javax.inject.Inject

class UserApiModelToUserEntity @Inject constructor() : Mapper<UserApiModel, UserEntity>() {
    override fun map(from: UserApiModel): UserEntity = with(from) {
        UserEntity(
            uid = id ?: -1,
            login = login.orEmpty(),
            avatarUrl = avatarUrl.orEmpty(),
            isFavorite = false
        )
    }
}