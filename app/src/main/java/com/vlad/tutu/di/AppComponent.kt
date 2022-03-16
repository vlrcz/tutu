package com.vlad.tutu.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.vlad.tutu.feature.auth.AuthManager
import com.vlad.tutu.core.data.GithubApi
import com.vlad.tutu.core.data.db.RepositoriesDao
import com.vlad.tutu.di.module.AuthModule
import com.vlad.tutu.di.module.DatabaseModule
import com.vlad.tutu.di.module.NetworkModule
import com.vlad.tutu.di.module.StorageModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton
import net.openid.appauth.AuthorizationService

@Singleton
@Component(modules = [NetworkModule::class, StorageModule::class, AuthModule::class, DatabaseModule::class])
interface AppComponent {

    fun authManager(): AuthManager
    fun sharedPrefs(): SharedPreferences
    fun authService(): AuthorizationService
    fun githubApi(): GithubApi
    fun repositoriesDao(): RepositoriesDao

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application,
            @BindsInstance context: Context
        ): AppComponent
    }
}