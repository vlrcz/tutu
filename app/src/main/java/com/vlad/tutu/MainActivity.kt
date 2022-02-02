package com.vlad.tutu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.vlad.tutu.auth.AuthFragment
import com.vlad.tutu.detail.DetailFragment
import com.vlad.tutu.navigation.Navigator
import com.vlad.tutu.repository_list.RepoListFragment
import com.vlad.tutu.repository_list.Repository

class MainActivity : AppCompatActivity(R.layout.activity_main), Navigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showAuthFragment()
    }

    override fun showAuthFragment() {
        launchFragment(AuthFragment())
    }

    override fun showRepositoryListFragment() {
        launchFragment(RepoListFragment())
    }

    override fun showDetailFragment(repo: Repository) {
        launchFragment(DetailFragment.newInstance(repo))
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container, fragment)
            .commit()
    }
}