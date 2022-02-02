package com.vlad.tutu.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vlad.tutu.repository_list.Repository

@Database(
    entities = [
        Repository::class
    ], version = GithubDatabase.DB_VERSION
)
abstract class GithubDatabase : RoomDatabase() {

    abstract fun reposDao(): ReposDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "github-database"
    }
}