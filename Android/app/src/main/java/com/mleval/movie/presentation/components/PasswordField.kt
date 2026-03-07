package com.mleval.movie.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.mleval.movie.R

@Composable
fun PasswordField(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    isEnable: Boolean,
    passwordError: Boolean,
    changeVisibilityClick: () -> Unit,
    passwordQuery: String,
    onPasswordValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .onFocusChanged { focusState ->
                if (!focusState.isFocused && isVisible) {
                    changeVisibilityClick()
                }
            },
        value = passwordQuery,
        enabled = isEnable,
        onValueChange = {
            onPasswordValueChange(it)
        },
        isError = passwordError,
        singleLine = true,
        label =  {
            Text(text = stringResource(R.string.password))
        },
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable {
                    changeVisibilityClick()
                },
                painter = painterResource(R.drawable.ic_visibility),
                contentDescription = "is making password visible",
                tint = if (isVisible) MaterialTheme.colorScheme.primary.copy(alpha = 0.8f) else Color.Unspecified
            )
        },
        shape = RoundedCornerShape(16.dp)
    )
}