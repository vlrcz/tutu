package com.vlad.tutu.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vlad.tutu.repository_list.Repository

@Dao
interface ReposDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfRepos(list: List<Repository>)

    @Query("SELECT * FROM ${ReposContract.TABLE_NAME}")
    suspend fun getRepos(): List<Repository>
}