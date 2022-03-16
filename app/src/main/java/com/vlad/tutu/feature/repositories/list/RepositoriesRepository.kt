package com.vlad.tutu.feature.repositories.list

import com.vlad.tutu.core.data.GithubApi
import com.vlad.tutu.core.data.db.RepositoriesDao
import javax.inject.Inject

class RepositoriesRepository @Inject constructor(
    private val githubApi: GithubApi,
    private val repositoriesDao: RepositoriesDao
) {
    suspend fun fetchPublicRepositories(): List<Repository> {
        return githubApi.fetchPublicRepositories()
    }

    suspend fun insertListOfRepositoriesToDb(list: List<Repository>) {
        repositoriesDao.insertListOfRepositories(list)
    }

    suspend fun fetchRepositoriesFromDb(): List<Repository> {
        return repositoriesDao.fetchRepositories()
    }
}