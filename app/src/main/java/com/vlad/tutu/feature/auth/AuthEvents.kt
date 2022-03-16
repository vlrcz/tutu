package com.vlad.tutu.feature.auth

import android.content.Intent

sealed class AuthEvents {
    data class OpenAuthPage(val intent: Intent) : AuthEvents()
    data class Toast(val text: Int) : AuthEvents()
    object Success : AuthEvents()
}
