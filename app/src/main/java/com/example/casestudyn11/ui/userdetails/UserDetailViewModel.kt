package com.example.casestudyn11.ui.userdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.casestudyn11.helper.FavoriteUpdatesDispatcher
import com.example.casestudyn11.domain.GetUserUseCase
import com.example.casestudyn11.ui.model.UserUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val favoriteUpdatesDispatcher: FavoriteUpdatesDispatcher
) : ViewModel() {
    private val _userUiModel = MutableStateFlow(
        UserUiModel(
            id = -1,
            login = "",
            avatarUrl = "",
            false
        )
    )
    val userUiModel = _userUiModel.asStateFlow()

    private val _events = Channel<UserDetailEvent>()
    val event get() = _events.receiveAsFlow()

    fun init(userId: Int) {
        viewModelScope.launch {
            val user = getUserUseCase.getUserById(userId)
            _userUiModel.update {
                user
            }
        }
    }

    fun updateFavorite(userId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            favoriteUpdatesDispatcher.updateUser(userId, isFavorite = isFavorite)
        }
    }

    fun onAvatarClicked(avatarUrl: String) {
        viewModelScope.launch {
            _events.send(UserDetailEvent.OnAvatarClicked(avatarUrl))
        }
    }
}


sealed class UserDetailEvent {
    data class OnAvatarClicked(val avatarUrl: String): UserDetailEvent()
}