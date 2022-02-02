package com.vlad.tutu.data

import com.vlad.tutu.repository_list.Repository
import retrofit2.http.GET

interface GithubApi {

    @GET("/repositories")
    suspend fun getPublicRepositories(): List<Repository>

}