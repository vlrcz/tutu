package com.vlad.tutu.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.vlad.tutu.auth.AuthFragment
import com.vlad.tutu.auth.AuthManager
import com.vlad.tutu.data.GithubApi
import com.vlad.tutu.db.ReposDao
import com.vlad.tutu.di.module.AuthModule
import com.vlad.tutu.di.module.DatabaseModule
import com.vlad.tutu.di.module.NetworkModule
import com.vlad.tutu.di.module.StorageModule
import com.vlad.tutu.repository_list.RepoListFragment
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
    fun reposDao(): ReposDao
    fun inject(authFragment: AuthFragment)
    fun inject(repoListFragment: RepoListFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application,
            @BindsInstance context: Context
        ): AppComponent
    }
}