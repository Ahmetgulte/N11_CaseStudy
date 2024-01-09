package com.example.casestudyn11.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.casestudyn11.data.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}
