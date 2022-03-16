package com.vlad.tutu.di.module

import android.app.Application
import androidx.room.Room
import com.vlad.tutu.core.data.db.GithubDatabase
import com.vlad.tutu.core.data.db.RepositoriesDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(application: Application): GithubDatabase {
        return Room.databaseBuilder(
            application,
            GithubDatabase::class.java,
            GithubDatabase.DB_NAME
        )
            .build()
    }

    @Provides
    fun providesRepositoriesDao(db: GithubDatabase): RepositoriesDao {
        return db.repositoriesDao()
    }
}