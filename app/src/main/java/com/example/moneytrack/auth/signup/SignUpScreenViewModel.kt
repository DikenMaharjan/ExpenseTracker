package com.example.moneytrack.auth.signup

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpScreenViewModel @Inject constructor(

) : ViewModel() {

    fun signUp(
        name: String,
        email: String,
        password: String
    ) {

    }

}