package com.vlad.tutu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vlad.tutu.feature.auth.AuthFragment
import com.vlad.tutu.core.navigation.NavigationConstants.AUTH
import com.vlad.tutu.core.navigation.Navigator
import com.vlad.tutu.core.navigation.NavigatorHolder
import com.vlad.tutu.core.navigation.screen.FragmentScreen

class MainActivity : AppCompatActivity(R.layout.activity_main), NavigatorHolder {

    lateinit var navigator: Navigator

    override fun navigator(): Navigator {
        return navigator
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigator = Navigator(this)

        if (savedInstanceState == null) {
            navigator.navigate(FragmentScreen(AuthFragment(), AUTH))
        }
    }
}