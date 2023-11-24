package com.jarroyo.library.ui.shared

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

actual open class SharedBaseViewModel {
    actual val viewModelScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
}
