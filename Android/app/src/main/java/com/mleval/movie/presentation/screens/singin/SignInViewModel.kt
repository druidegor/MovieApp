package com.mleval.movie.presentation.screens.singin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mleval.movie.domain.entity.SignInData
import com.mleval.movie.domain.usecase.AddTokenUseCase
import com.mleval.movie.domain.usecase.SingInUseCase
import com.mleval.movie.presentation.utils.emailValidation
import com.mleval.movie.presentation.utils.passwordValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingInViewModel @Inject constructor(
    private val singInUseCase: SingInUseCase,
    private val addTokenUseCase: AddTokenUseCase
): ViewModel() {

    private val _state = MutableStateFlow(SingInState())
    val state = _state.asStateFlow()

    fun processCommand(command: SingInCommand) {
            when(command) {
                is SingInCommand.InputEmail -> {
                    _state.update { previousState ->
                        previousState.copy(email = command.email, emailError = null)
                    }
                }
                is SingInCommand.InputPassword -> {
                    _state.update { previousState ->
                        previousState.copy(password = command.password, passwordError = null)
                    }
                }
                SingInCommand.PasswordVisibleChanging -> {
                    _state.update { previousState ->
                        previousState.copy(passwordVisible = !previousState.passwordVisible)
                    }
                }
                is SingInCommand.SingIn -> {
                    viewModelScope.launch {
                        _state.update { previousState -> previousState.copy(isLoading = true) }
                        val emailError = emailValidation(command.email)
                        val passwordError = passwordValidation(command.password)

                        if ( emailError != null || passwordError != null) {
                            _state.update { previousState ->
                                previousState.copy(
                                    emailError = emailError,
                                    passwordError = passwordError,
                                    isLoading = false
                                )
                            }
                        }
                        else {
                            try {
                                val token = singInUseCase(
                                    SignInData(command.email, command.password)
                                ).token
                                addTokenUseCase(token)
                                Log.d("viewModel", "token = $token")
                            } catch(e: Exception) {

                            } finally {
                                _state.update { previousState -> previousState.copy(isLoading = false) }
                            }

                        }
                    }
                }
            }
    }
}

sealed interface SingInCommand {

    data class SingIn(val email: String, val password: String): SingInCommand

    data class InputEmail(val email: String): SingInCommand

    data class InputPassword(val password: String): SingInCommand

    object PasswordVisibleChanging: SingInCommand

}

data class SingInState(
    val email: String = "",
    val password: String = "",
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val passwordVisible: Boolean = false,
    val isLoading: Boolean = false
)