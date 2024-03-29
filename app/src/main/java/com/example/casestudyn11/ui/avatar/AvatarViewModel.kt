package com.example.casestudyn11.ui.avatar

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AvatarViewModel @Inject constructor(): ViewModel() {
    private val _avatarUrl = MutableStateFlow("")
    val avatarUrl = _avatarUrl.asStateFlow()


    fun setAvatarUrl(avatarUrl: String) {
        _avatarUrl.update {
            avatarUrl
        }
    }
}