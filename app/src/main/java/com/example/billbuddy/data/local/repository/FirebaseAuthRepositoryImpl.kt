package com.example.billbuddy.data.local.repository

import android.app.Activity
import com.example.billbuddy.domain.repository.FirebaseAuthRepository
import com.example.billbuddy.util.Resource
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : FirebaseAuthRepository {

    private lateinit var onVerificationCode: String

    override fun createUserWithPhone(phone: String, activity: Activity): Flow<Resource<String>> =
        callbackFlow {
            trySend(Resource.Loading())
            val onVerificationCallback =
                object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    }

                    override fun onVerificationFailed(p0: FirebaseException) {
                        trySend(Resource.Error(p0.toString()))
                    }

                    override fun onCodeSent(
                        verificationCode: String,
                        p1: PhoneAuthProvider.ForceResendingToken
                    ) {
                        super.onCodeSent(verificationCode, p1)
                        trySend(Resource.Success("OTP Sent Successfully"))
                        onVerificationCode = verificationCode
                    }
                }

            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(onVerificationCallback)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
            awaitClose {
                close()
            }
        }

    override fun signWithCredential(otp: String): Flow<Resource<String>> = callbackFlow {
        trySend(Resource.Loading())
        val credential = PhoneAuthProvider.getCredential(onVerificationCode, otp)
        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    trySend(Resource.Success("otp verified"))
                }
            }.addOnFailureListener {
                trySend(Resource.Error(it.toString()))
            }
        awaitClose {
            close()
        }
    }
}
