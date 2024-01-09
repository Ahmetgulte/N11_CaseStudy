package com.example.casestudyn11.ui.userlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.casestudyn11.helper.FavoriteUpdatesDispatcher
import com.example.casestudyn11.domain.GetUserListUseCase
import com.example.casestudyn11.ui.model.UserUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getUserListUseCase: GetUserListUseCase,
    private val favoriteUpdatesDispatcher: FavoriteUpdatesDispatcher
): ViewModel(){

    private val _uiState = MutableStateFlow<UserListState>(UserListState.Loading)
    val uiState: StateFlow<UserListState> = _uiState.asStateFlow()

    init {
        subscribeFavoriteUpdates()
        viewModelScope.launch {
            val userList = getUserListUseCase.getUserList()
            _uiState.update {
                UserListState.Content(userList)
            }
        }
    }

    fun updateFavorite(userId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            favoriteUpdatesDispatcher.updateUser(userId, isFavorite = isFavorite)
        }
    }

    private fun subscribeFavoriteUpdates() {
        viewModelScope.launch {
            favoriteUpdatesDispatcher.getUpdates().collect { favoriteResult ->
                if (favoriteResult.isSuccess) {
                    val contentState = _uiState.value as? UserListState.Content ?: return@collect
                    val (userId, isFavorite) = favoriteResult.getOrNull()!!
                    val userList = contentState.userList.toMutableList()
                    val index = userList.indexOfFirst { user ->
                        user.id == userId
                    }
                    userList[index] = userList[index].copy(isFavorite = isFavorite)
                    _uiState.update { contentState.copy(userList = userList) }

                } else {
                    Log.v("Tag", "error")
                }
            }
        }
    }
}


sealed class UserListState {
    data object Loading : UserListState()
    data class Content(val userList: List<UserUiModel>) : UserListState()
    data class Error(val errorInfo: String) : UserListState()
}