package com.jarroyo.library.ui.shared

import kotlinx.coroutines.CoroutineScope
import androidx.lifecycle.viewModelScope as androidViewModelScope

typealias AndroidViewModel = androidx.lifecycle.ViewModel

actual open class SharedBaseViewModel : AndroidViewModel() {
    actual val viewModelScope: CoroutineScope = androidViewModelScope
}