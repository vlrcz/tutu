package com.vlad.tutu.di.module

import android.app.Application
import androidx.room.Room
import com.vlad.tutu.db.GithubDatabase
import com.vlad.tutu.db.ReposDao
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
    fun providesActivitiesDao(db: GithubDatabase): ReposDao {
        return db.reposDao()
    }
}