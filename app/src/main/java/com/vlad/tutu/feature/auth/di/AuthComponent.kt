package com.vlad.tutu.feature.auth.di

import com.vlad.tutu.di.AppComponent
import com.vlad.tutu.feature.auth.AuthFragment
import dagger.Component

@AuthScope
@Component(
    dependencies = [AppComponent::class]
)
interface AuthComponent {

    fun inject(authFragment: AuthFragment)

    @Component.Factory
    interface Factory {
        fun create(
            appComponent: AppComponent
        ): AuthComponent
    }
}