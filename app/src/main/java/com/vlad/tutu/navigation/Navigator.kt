package com.vlad.tutu.navigation

import androidx.fragment.app.Fragment
import com.vlad.tutu.repository_list.Repository

interface Navigator {
    fun showAuthFragment()
    fun showRepositoryListFragment()
    fun showDetailFragment(repo: Repository)
}

fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}