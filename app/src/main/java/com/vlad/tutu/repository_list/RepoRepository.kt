package com.vlad.tutu.repository_list

import com.vlad.tutu.data.GithubApi
import com.vlad.tutu.db.ReposDao
import javax.inject.Inject

class RepoRepository @Inject constructor(
    private val githubApi: GithubApi,
    private val reposDao: ReposDao
) {
    suspend fun getUserRepositories(): List<Repository> {
        return githubApi.getPublicRepositories()
    }

    suspend fun insertListOfReposToDb(list: List<Repository>) {
        reposDao.insertListOfRepos(list)
    }

    suspend fun getReposFromDb(): List<Repository> {
        return reposDao.getRepos()
    }
}