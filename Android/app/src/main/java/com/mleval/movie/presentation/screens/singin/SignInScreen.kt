@file:OptIn(ExperimentalMaterial3Api::class)

package com.mleval.movie.presentation.screens.singin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.mleval.movie.R
import com.mleval.movie.presentation.components.AuthButton
import com.mleval.movie.presentation.components.AuthTextField
import com.mleval.movie.presentation.components.ConfirmationButton
import com.mleval.movie.presentation.components.LoadingOverlay
import com.mleval.movie.presentation.components.PasswordField

@Composable
fun SingInScreen(
    modifier: Modifier = Modifier,
    viewModel: SingInViewModel = hiltViewModel(),
    onSkipClick: () -> Unit,
    onForgetPasswordClick: () -> Unit,
    onSingUpClick: () -> Unit
) {
    Scaffold(
        topBar = {
            SingInAppBar {
                onSkipClick()
            }
        }
    ) { innerPadding ->

        val state by viewModel.state.collectAsState()
        val focusManager = LocalFocusManager.current

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .pointerInput(Unit) {
                    detectTapGestures {
                        focusManager.clearFocus()
                    }
                }
                .padding(horizontal = 24.dp)
                .padding(bottom = 4.dp)
        ) {
            Spacer(Modifier.height(48.dp))

            Text(
                text = stringResource(R.string.sign_in),
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start,
            )
            Spacer(Modifier.height(24.dp))

            Authentication(
                isVisible = state.passwordVisible,
                emailQuery = state.email,
                passwordQuery = state.password,
                onEmailValueChange = {
                    viewModel.processCommand(SingInCommand.InputEmail(it))
                },
                onPasswordValueChange = {
                    viewModel.processCommand(SingInCommand.InputPassword(it))
                },
                changeVisibilityClick = {
                    viewModel.processCommand(SingInCommand.PasswordVisibleChanging)
                },
                singInClick = {
                    viewModel.processCommand(SingInCommand.SingIn(state.email,state.password))
                },
                onGoogleClick = {

                },
                onForgetPasswordClick = {

                },
                emailError = state.emailError,
                passwordError = state.passwordError,
                isEnable = !state.isLoading
            )

            Spacer(Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    4.dp,
                    Alignment.CenterHorizontally
                )

            ) {
                Text(
                    text = stringResource(R.string.don_t_you_have_an_account),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    modifier = Modifier.clickable{
                        onSingUpClick()
                    },
                    text = stringResource(R.string.sign_up),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        if (state.isLoading) {
            LoadingOverlay()
        }
    }
}

@Composable
private fun SingInAppBar(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    TopAppBar(
        title = {},
        modifier = modifier.padding(horizontal = 16.dp),
        actions = {
            Text(
                modifier = Modifier
                    .clickable {
                        onClick()
                    },
                text = stringResource(R.string.skip),
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                color = MaterialTheme.colorScheme.onTertiaryFixed
            )
        }
    )
}

@Composable
private fun Authentication(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    isEnable: Boolean,
    emailQuery: String,
    emailError: Int?,
    passwordQuery: String,
    passwordError: Int?,
    onEmailValueChange: (String) -> Unit,
    onPasswordValueChange: (String) -> Unit,
    onForgetPasswordClick: () -> Unit,
    changeVisibilityClick: () -> Unit,
    singInClick: () -> Unit,
    onGoogleClick: () -> Unit
) {

   Column(
       modifier = modifier.fillMaxWidth()
   ) {


       AuthTextField(
           label = stringResource(R.string.email),
           emailQuery = emailQuery,
           onEmailValueChange = {
               onEmailValueChange(it)
           },
           emailError = emailError != null,
           isEnable = isEnable
       )

       if (emailError != null) {
           Text(
               modifier = Modifier.padding(top = 4.dp),
               text = stringResource(emailError),
               color = MaterialTheme.colorScheme.primary.copy(0.8f)
           )
       }

       Spacer(Modifier.height(16.dp))

       PasswordField(
           isVisible = isVisible,
           changeVisibilityClick = {
               changeVisibilityClick()
           },
           passwordQuery = passwordQuery,
           onPasswordValueChange = {
               onPasswordValueChange(it)
           },
           passwordError = passwordError != null,
           isEnable = isEnable
       )

       if (passwordError != null) {
           Text(
               modifier = Modifier.padding(top = 4.dp),
               text = stringResource(passwordError),
               color = MaterialTheme.colorScheme.primary.copy(0.8f)
           )
       }

       Spacer(Modifier.height(14.dp))

       Text(
           modifier = Modifier
               .fillMaxWidth()
               .clickable {
                   onForgetPasswordClick()
               },
           text = stringResource(R.string.forgot_password),
           color = MaterialTheme.colorScheme.onTertiaryFixed,
           textAlign = TextAlign.End
       )

       Spacer(Modifier.height(28.dp))

       ConfirmationButton(
           singInClick = {
               singInClick()
           },
           label = stringResource(R.string.sign_in),
           isEnable = isEnable
       )

       Spacer(Modifier.height(28.dp))

       Row(
           modifier = Modifier.fillMaxWidth(),
           verticalAlignment = Alignment.CenterVertically
       ) {

           HorizontalDivider(
               modifier = Modifier.weight(1f),
               color = MaterialTheme.colorScheme.onSurfaceVariant
           )

           Text(
               modifier = Modifier.padding(horizontal = 8.dp),
               text = stringResource(R.string.or),
               color = MaterialTheme.colorScheme.onTertiary
           )

           HorizontalDivider(
               modifier = Modifier.weight(1f),
               color = MaterialTheme.colorScheme.onSurfaceVariant
           )
       }

       Spacer(Modifier.height(29.dp))


       Row(
           modifier = Modifier.fillMaxWidth(),
           horizontalArrangement = Arrangement.spacedBy(13.dp)
       ) {

           AuthButton(
               modifier = Modifier.weight(1f),
               onGoogleClick = {
                   onGoogleClick()
               },
               picId = R.drawable.ic_facebook
           )

           AuthButton(
               modifier = Modifier.weight(1f),
               onGoogleClick = {
                   onGoogleClick()
               },
               picId = R.drawable.ic_google
           )

           AuthButton(
               modifier = Modifier.weight(1f),
               onGoogleClick = {
                   onGoogleClick()
               },
               picId = R.drawable.ic_apple
           )
       }

   }
}
