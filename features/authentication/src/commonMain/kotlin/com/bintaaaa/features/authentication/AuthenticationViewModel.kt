package com.bintaaaa.features.authentication

import com.bijan.libraries.core.state.Intent
import com.bijan.libraries.core.viewModel.ViewModel
import com.bintaaaa.apis.authentication.AuthenticationRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AuthenticationViewModel(private val authenticationRepository: AuthenticationRepository) :
    ViewModel<AuthenticationState, AuthenticationIntent>(AuthenticationState()) {
    override fun sendIntent(intent: Intent) {
        when(intent){
            is AuthenticationIntent.UpdateName ->{
                updateName(intent.name)
            }
            is AuthenticationIntent.UpdatePassword ->{
                updatePassword(intent.password)
            }
            is AuthenticationIntent.UserLogin ->{
                login()
            }
        }
    }

    private fun login() = viewModelScope.launch {
        val name = uiState.value.name
        val password = uiState.value.password
        authenticationRepository.login(name, password).stateIn(this).collectLatest {
            updateUiState { copy(login = it) }
        }
    }

    private fun updateName(name: String) {
        updateUiState {
            copy(name = name)
        }
    }

    private fun updatePassword(password: String) {
        updateUiState {
            copy(password = password)
        }
    }
}