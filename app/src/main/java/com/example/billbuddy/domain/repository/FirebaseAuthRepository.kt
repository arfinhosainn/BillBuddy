package com.example.billbuddy.domain.repository

import android.app.Activity
import com.example.billbuddy.util.Resource
import kotlinx.coroutines.flow.Flow

interface FirebaseAuthRepository {
    fun createUserWithPhone(
        phone: String,
        activity: Activity
    ): Flow<Resource<String>>


    fun signWithCredential(
        otp: String
    ): Flow<Resource<String>>

}
