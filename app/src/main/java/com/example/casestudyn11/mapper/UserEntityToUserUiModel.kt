package com.example.casestudyn11.mapper

import com.example.casestudyn11.base.Mapper
import com.example.casestudyn11.data.entity.UserEntity
import com.example.casestudyn11.ui.model.UserUiModel
import javax.inject.Inject

class UserEntityToUserUiModel @Inject constructor() : Mapper<UserEntity, UserUiModel>() {
    override fun map(from: UserEntity): UserUiModel = with(from) {
        UserUiModel(
            id = uid,
            login = login ?: "",
            avatarUrl = avatarUrl ?: "",
            isFavorite = isFavorite
        )
    }
}