package com.example.eattendace

import android.content.Context
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.CancellationSignal
import android.util.Log
import android.widget.ImageView
import androidx.annotation.RequiresApi

@Suppress("DEPRECATION")
@RequiresApi(api = Build.VERSION_CODES.M)
class FingerprintHandler(
    private val image: ImageView,
    private val context: Context
) : FingerprintManager.AuthenticationCallback() {
    override fun onAuthenticationError(
        errorCode: Int,
        errString: CharSequence
    ) {
        super.onAuthenticationError(errorCode, errString)
        image.setImageDrawable(
            context.resources.getDrawable(R.drawable.ic_check_black_24dp, context.theme)
        )
    }

    override fun onAuthenticationHelp(
        helpCode: Int,
        helpString: CharSequence
    ) {
        super.onAuthenticationHelp(helpCode, helpString)
//        image.setImageDrawable(
//            context.resources.getDrawable(R.drawable.ic_check_black_24dp, context.theme)
//        )
    }

    override fun onAuthenticationSucceeded(result: FingerprintManager.AuthenticationResult) {
        super.onAuthenticationSucceeded(result)
        image.setImageDrawable(
            context.resources.getDrawable(R.drawable.ic_check_black_24dp, context.theme)
        )
    }

    override fun onAuthenticationFailed() {
        super.onAuthenticationFailed()
        image.setImageDrawable(
            context.resources.getDrawable(R.drawable.ic_check_black_24dp, context.theme)
        )
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    fun doAuth(manager: FingerprintManager?, obj: FingerprintManager.CryptoObject?) {
        val signal = CancellationSignal()
        try {
            manager?.authenticate(obj, signal, 0, this, null)
        } catch (sce: SecurityException) {
        }
    }

}