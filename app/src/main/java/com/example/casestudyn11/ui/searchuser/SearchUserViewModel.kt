package com.example.casestudyn11.ui.searchuser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.casestudyn11.domain.GetSearchedUsersUseCase
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
class SearchUserViewModel @Inject constructor(
    private val getSearchedUsersUseCase: GetSearchedUsersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<SearchUserState>(SearchUserState.Initial)
    val state get() = _state.asStateFlow()

    private val _events = Channel<SearchUserEvent>()
    val events get() = _events.receiveAsFlow()

    fun searchUsers(query: String) {
        viewModelScope.launch {
            _state.update {
                SearchUserState.Loading
            }
            val users = getSearchedUsersUseCase.searchUsers(query = query)
            val newState = if (users.isNotEmpty()) {
                SearchUserState.Content(users)
            } else {
                SearchUserState.Empty
            }
            _state.update {
                newState
            }
        }
    }

    fun onUserItemClicked(userId: Int) {
        viewModelScope.launch {
            _events.send(SearchUserEvent.OnUserItemClicked(userId))
        }
    }
}


sealed class SearchUserState {
    data object Initial : SearchUserState()
    data object Loading : SearchUserState()
    data class Content(val userList: List<UserUiModel>) : SearchUserState()
    data object Empty : SearchUserState()
}

sealed class SearchUserEvent {
    data class OnUserItemClicked(val userId: Int) : SearchUserEvent()
}