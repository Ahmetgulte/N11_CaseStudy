package com.example.casestudyn11.mapper

import com.example.casestudyn11.base.Mapper
import com.example.casestudyn11.data.model.UserApiModel
import com.example.casestudyn11.ui.model.UserUiModel
import javax.inject.Inject

class UserApiModelToUserUiModelMapper @Inject constructor() : Mapper<UserApiModel, UserUiModel>() {
    override fun map(from: UserApiModel): UserUiModel {
        return with(from) {
            UserUiModel(id = id!!, login = login!!, avatarUrl = avatarUrl!!, isFavorite = false)
        }
    }
}