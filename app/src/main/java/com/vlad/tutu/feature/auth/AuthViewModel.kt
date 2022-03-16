package com.vlad.tutu.feature.auth

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vlad.tutu.R
import javax.inject.Inject
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest

class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val authService: AuthorizationService,
    private val authManager: AuthManager
) : ViewModel() {

    private val openAuthPageLiveData = MutableLiveData<Intent>()
    private val toastLiveData = MutableLiveData<Int>()
    private val authSuccessLiveData = MutableLiveData<Unit>()

    val openAuthPage: LiveData<Intent>
        get() = openAuthPageLiveData

    val toast: LiveData<Int>
        get() = toastLiveData

    val authSuccess: LiveData<Unit>
        get() = authSuccessLiveData

    fun onAuthCodeReceived(tokenRequest: TokenRequest) {
        authRepository.performTokenRequest(
            authService = authService,
            tokenRequest = tokenRequest,
            onComplete = {
                authSuccessLiveData.postValue(Unit)
            },
            onError = {
                toastLiveData.postValue(R.string.auth_canceled)
            }
        )
    }

    fun onAuthCodeFailed(exception: AuthorizationException) {
        toastLiveData.postValue(R.string.auth_canceled)
    }

    fun openLoginPage() {
        val openAuthPageIntent = authService.getAuthorizationRequestIntent(
            authRepository.getAuthRequest()
        )
        openAuthPageLiveData.postValue(openAuthPageIntent)
    }

    fun containsAccessToken(): Boolean {
        return authManager.containsAccessToken()
    }

    override fun onCleared() {
        super.onCleared()
        authService.dispose()
    }

}