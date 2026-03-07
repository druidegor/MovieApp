package com.mleval.movie.presentation.utils

import android.util.Patterns
import com.mleval.movie.R


fun emailValidation(email: String): Int? {

    if (email.isBlank()) {
        return R.string.email_can_t_be_empty
    }

    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        return R.string.incorrect_email
    }

    return null
}

fun passwordValidation(password: String): Int? {

    if (password.length < 8) {
        return R.string.minimum_8_characters
    }

    return null
}