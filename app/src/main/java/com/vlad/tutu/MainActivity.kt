package com.vlad.tutu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vlad.tutu.auth.AuthFragment
import com.vlad.tutu.navigation.NavigationConstants.AUTH
import com.vlad.tutu.navigation.Navigator
import com.vlad.tutu.navigation.NavigatorHolder
import com.vlad.tutu.navigation.screen.FragmentScreen

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