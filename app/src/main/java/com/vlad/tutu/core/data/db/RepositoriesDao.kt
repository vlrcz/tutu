package com.vlad.tutu.core.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vlad.tutu.feature.repositories.list.Repository

@Dao
interface RepositoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfRepositories(list: List<Repository>)

    @Query("SELECT * FROM ${Repository.TABLE_NAME}")
    suspend fun fetchRepositories(): List<Repository>
}