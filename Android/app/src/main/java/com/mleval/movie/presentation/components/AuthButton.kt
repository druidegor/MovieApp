package com.mleval.movie.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mleval.movie.R


@Composable
fun AuthButton(
    modifier: Modifier = Modifier,
    picId: Int,
    onGoogleClick: () -> Unit
) {
    Button(
        onClick = {
            onGoogleClick()
        },
        modifier = modifier
            .height(54.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Icon(
            painter = painterResource(picId),
            contentDescription = "Sing In with google"
        )
    }
}