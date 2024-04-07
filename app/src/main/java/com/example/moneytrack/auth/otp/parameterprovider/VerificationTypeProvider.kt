package com.example.moneytrack.auth.otp.parameterprovider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.moneytrack.auth.otp.VerificationType

class VerificationTypeProvider : PreviewParameterProvider<VerificationType> {
    override val values: Sequence<VerificationType>
        get() = sequenceOf(VerificationType.LoginVerification, VerificationType.EmailVerification)
}