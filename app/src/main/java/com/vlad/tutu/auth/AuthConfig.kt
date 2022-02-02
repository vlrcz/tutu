package com.vlad.tutu.auth

import net.openid.appauth.ResponseTypeValues

object AuthConfig {
    const val AUTH_URI = "https://github.com/login/oauth/authorize"
    const val TOKEN_URI = "https://github.com/login/oauth/access_token"
    const val RESPONSE_TYPE = ResponseTypeValues.CODE
    const val SCOPE = "user,repo"
    const val API_URL = "https://api.github.com/"

    const val CLIENT_ID = "3fa334bfec5c16127ce6"
    const val CLIENT_SECRET = "b15616104b4358df18a54e3684c7b80c037287e6"
    const val CALLBACK_URL = "tutu://tutu.ru"
}