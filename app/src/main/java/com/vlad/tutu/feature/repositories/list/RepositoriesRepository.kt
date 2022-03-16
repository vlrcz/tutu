package com.vlad.tutu.feature.repositories.list

import com.vlad.tutu.core.data.GithubApi
import com.vlad.tutu.core.data.db.RepositoriesDao
import javax.inject.Inject

class RepositoriesRepository @Inject constructor(
    private val githubApi: GithubApi,
    private val repositoriesDao: RepositoriesDao
) {
    suspend fun getUserRepositories(): List<Repository> {
        return githubApi.getPublicRepositories()
    }

    suspend fun insertListOfReposToDb(list: List<Repository>) {
        repositoriesDao.insertListOfRepositories(list)
    }

    suspend fun getReposFromDb(): List<Repository> {
        return repositoriesDao.fetchRepositories()
    }
}