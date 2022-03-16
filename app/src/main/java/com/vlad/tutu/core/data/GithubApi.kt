package com.vlad.tutu.core.data

import com.vlad.tutu.feature.repositories.list.Repository
import retrofit2.http.GET

interface GithubApi {

    @GET("/repositories")
    suspend fun getPublicRepositories(): List<Repository>

}