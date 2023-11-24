package com.jarroyo.library.ui.shared

import kotlinx.coroutines.CoroutineScope

expect open class SharedBaseViewModel {
    val viewModelScope: CoroutineScope
}
