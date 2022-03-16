package com.vlad.tutu.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlad.tutu.R
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest

class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val authService: AuthorizationService,
    private val authManager: AuthManager
) : ViewModel() {

    private val eventsChannel = Channel<AuthEvents>(Channel.BUFFERED)
    val events = eventsChannel.receiveAsFlow()

    fun onAuthCodeReceived(tokenRequest: TokenRequest) {
        authRepository.performTokenRequest(
            authService = authService,
            tokenRequest = tokenRequest,
            onComplete = {
                viewModelScope.launch {
                    eventsChannel.send(AuthEvents.Success)
                }
            },
            onError = {
                viewModelScope.launch {
                    eventsChannel.send(AuthEvents.Toast(R.string.auth_canceled))
                }
            }
        )
    }

    fun openLoginPage() {
        val openAuthPageIntent = authService.getAuthorizationRequestIntent(
            authRepository.getAuthRequest()
        )
        viewModelScope.launch {
            eventsChannel.send(AuthEvents.OpenAuthPage(openAuthPageIntent))
        }
    }

    fun containsAccessToken(): Boolean {
        return authManager.containsAccessToken()
    }

    override fun onCleared() {
        super.onCleared()
        authService.dispose()
    }
}