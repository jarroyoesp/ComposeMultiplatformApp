package com.jarroyo.feature.common.interactor

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import org.koin.java.KoinJavaComponent.get

actual fun openUrl(url: String) {
    val context: Context = get(clazz = Context::class.java)
    val uri = url.toUri()
    val intent = Intent().apply {
        action = Intent.ACTION_VIEW
        data = uri
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(intent)
}
