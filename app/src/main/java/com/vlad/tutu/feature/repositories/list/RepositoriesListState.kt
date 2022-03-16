package com.vlad.tutu.feature.repositories.list

data class RepositoriesListState(
    val loading: Boolean,
    val itemsList: List<Repository>,
    val isFirstLoad: Boolean
)