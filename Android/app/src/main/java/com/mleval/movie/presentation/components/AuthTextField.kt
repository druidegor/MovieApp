package com.mleval.movie.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mleval.movie.R

@Composable
fun AuthTextField(
    modifier: Modifier = Modifier,
    label: String,
    isEnable: Boolean,
    emailError: Boolean,
    emailQuery: String,
    onEmailValueChange: (String) -> Unit
) {

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        value = emailQuery,
        enabled = isEnable,
        onValueChange = {
            onEmailValueChange(it)
        },
        singleLine = true,
        label = {
            Text(text = label)
        },
        isError = emailError,
        shape = RoundedCornerShape(16.dp)
    )
}