package com.bintaaaa.features.authentication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bijan.libraries.core.state.AsyncState
import com.bijan.libraries.core.viewModel.rememberViewModel
import com.bintaaaa.apis.authentication.LocalAuthenticationRepository
import com.example.libraries.components.components.TopBarComponent
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(onRedirect: () -> Unit) {

    val authenticationRepository = LocalAuthenticationRepository.current
    val viewModel = rememberViewModel { AuthenticationViewModel(authenticationRepository) }
    val loginState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = {
            TopBarComponent("Login")
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
    ) {
        when (val data = loginState.login) {
            is AsyncState.Success -> {
                onRedirect.invoke()
            }

            is AsyncState.Failure -> {
                val message = data.throwable.message
                scope.launch {
                    snackBarHostState.showSnackbar(message!!)
                }
            }

            else -> {

            }
        }
        Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
            TextFieldCustom(
                name = loginState.name,
                placeHolder = "Username",
                onValueChange = { name ->
                    viewModel.sendIntent(AuthenticationIntent.UpdateName(name))
                }
            )

            TextFieldCustom(
                name = loginState.password,
                placeHolder = "Password",
                onValueChange = { password ->
                    viewModel.sendIntent(AuthenticationIntent.UpdatePassword(password))
                }
            )

            ButtonSubmit(viewModel, loginState)
        }
    }

}

@Composable
fun TextFieldCustom(
    modifier: Modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp),
    name: String,
    onValueChange: (String) -> Unit,
    placeHolder: String
) {
    OutlinedTextField(
        modifier = modifier,
        value = name,
        onValueChange = onValueChange,
        singleLine = true,
        placeholder = {
            Text(placeHolder)
        }
    )
}

@Composable
fun ButtonSubmit(viewModel: AuthenticationViewModel, state: AuthenticationState) {
    Button(
        onClick = {
            viewModel.sendIntent(AuthenticationIntent.UserLogin)
        },
        enabled = state.login !is AsyncState.Loading
    ) {
        if (state.login is AsyncState.Loading) {
            CircularProgressIndicator()
        } else {
            Text("Login")
        }
    }
}