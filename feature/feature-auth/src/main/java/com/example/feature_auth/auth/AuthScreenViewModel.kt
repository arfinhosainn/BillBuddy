package com.example.feature_auth.auth

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.example.core_domain.repository.FirebaseAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthScreenViewModel @Inject constructor(
    private val repository: FirebaseAuthRepository
) : ViewModel() {

    fun createUserWithPhone(
        mobile: String,
        activity: Activity
    ) = repository.createUserWithPhone(mobile, activity)

    fun signInWithCredential(
        code: String
    ) = repository.signWithCredential(code)
}