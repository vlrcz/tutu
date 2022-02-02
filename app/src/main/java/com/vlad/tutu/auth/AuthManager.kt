package com.vlad.tutu.auth

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthManager @Inject constructor(private val sharedPrefs: SharedPreferences) {

    fun login(accessToken: String) {
        sharedPrefs.edit()
            .putString(ACCESS_TOKEN, accessToken)
            .apply()
    }

    fun fetchAccessToken(): String? {
        return sharedPrefs.getString(ACCESS_TOKEN, null)
    }

    companion object {
        private const val ACCESS_TOKEN = "Access Token"
    }
}