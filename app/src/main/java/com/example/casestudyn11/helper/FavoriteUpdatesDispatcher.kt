package com.example.casestudyn11.helper

import android.util.Log
import com.example.casestudyn11.data.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

interface FavoriteUpdatesDispatcher {

    fun getUpdates(): Flow<Result<Pair<Int, Boolean>>>

    suspend fun updateUser(userId: Int, isFavorite: Boolean)
}

class FavoriteUpdatesDispatcherImpl @Inject constructor(
    private val githubRepository: GithubRepository
): FavoriteUpdatesDispatcher {
    private val _favoriteUpdates = MutableSharedFlow<Result<Pair<Int, Boolean>>>()

    override fun getUpdates() = _favoriteUpdates.asSharedFlow()

    override suspend fun updateUser(userId: Int, isFavorite: Boolean) {
        runCatching {
            githubRepository.updateFavoriteState(userId, isFavorite = isFavorite)
        }.onSuccess {
            _favoriteUpdates.emit(Result.success(Pair(userId, isFavorite)))
        }.onFailure { Log.v("Tag", it.message.toString()) }
    }

}