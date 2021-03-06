package com.vlad.tutu.feature.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.vlad.tutu.R
import com.vlad.tutu.R.layout
import com.vlad.tutu.app.appComponent
import com.vlad.tutu.databinding.FragmentAuthBinding
import com.vlad.tutu.di.ViewModelFactory
import com.vlad.tutu.core.navigation.NavigationConstants.REPOSITORIES
import com.vlad.tutu.core.navigation.navigate
import com.vlad.tutu.core.navigation.screen.FragmentScreen
import com.vlad.tutu.feature.auth.AuthEvents.OpenAuthPage
import com.vlad.tutu.feature.auth.AuthEvents.Success
import com.vlad.tutu.feature.auth.AuthEvents.Toast
import com.vlad.tutu.feature.auth.di.DaggerAuthComponent
import com.vlad.tutu.feature.repositories.list.RepositoriesListFragment
import com.vlad.tutu.toast
import javax.inject.Inject
import javax.inject.Provider
import kotlinx.coroutines.flow.collect
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse

class AuthFragment : Fragment(layout.fragment_auth) {

    @Inject
    lateinit var viewModelProvider: Provider<AuthViewModel>
    private val binding: FragmentAuthBinding by viewBinding(FragmentAuthBinding::bind)
    private val viewModel: AuthViewModel by viewModels { ViewModelFactory { viewModelProvider.get() } }

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val tokenExchangeRequest = result.data?.let {
                    AuthorizationResponse.fromIntent(it)
                        ?.createTokenExchangeRequest()
                }
                val exception = AuthorizationException.fromIntent(result.data)
                when {
                    tokenExchangeRequest != null && exception == null ->
                        viewModel.onAuthCodeReceived(tokenExchangeRequest)
                    exception != null -> toast(R.string.auth_canceled)
                }
            } else {
                toast(R.string.request_failed)
            }
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerAuthComponent.factory().create(context.appComponent).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpBtn.setOnClickListener { viewModel.openLoginPage() }

        if (viewModel.containsAccessToken()) {
            navigate(FragmentScreen(RepositoriesListFragment(), REPOSITORIES))
        }

        lifecycleScope.launchWhenStarted {
            viewModel.events.collect {
                when (it) {
                    is OpenAuthPage -> activityResultLauncher.launch(it.intent)
                    is Toast -> toast(it.text)
                    is Success -> navigate(FragmentScreen(RepositoriesListFragment(), REPOSITORIES))
                }
            }
        }
    }
}