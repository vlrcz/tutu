package com.vlad.tutu.feature.repositories.list.di

import com.vlad.tutu.di.AppComponent
import com.vlad.tutu.feature.repositories.list.RepositoriesListFragment
import dagger.Component

@RepositoriesListScope
@Component(dependencies = [AppComponent::class])
interface RepositoriesListComponent {

    fun inject(repositoriesListFragment: RepositoriesListFragment)

    @Component.Factory
    interface Factory {
        fun create(
            appComponent: AppComponent
        ): RepositoriesListComponent
    }
}