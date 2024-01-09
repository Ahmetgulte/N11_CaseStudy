package com.example.casestudyn11.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.casestudyn11.data.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUsers(userEntities: List<UserEntity>)


    @Query("SELECT * FROM users")
    fun getUsers(): List<UserEntity>

    @Query("SELECT * FROM users where uid = :userId")
     suspend fun getUserById(userId: Int): UserEntity

    @Query("UPDATE users SET is_favorite =:isFavorite WHERE uid = :userId")
    fun updateFavorite(userId: Int, isFavorite: Boolean)
}