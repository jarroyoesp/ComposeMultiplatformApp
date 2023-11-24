package com.jarroyo.library.ui.shared

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<UiEvent : ViewEvent, UiState : ViewState, UiEffect : ViewEffect>: SharedBaseViewModel() {
    // State (current state of views)
    // Everything is lazy in order to be able to use SavedStateHandle as initial value
    private val initialState: UiState by lazy { provideInitialState() }
    private val _viewState: MutableState<UiState> by lazy { mutableStateOf(initialState) }
    val viewState: State<UiState> by lazy { _viewState }

    // JAE private val runningJobs = AtomicInteger(0)

    // Event (user actions)
    private val _event: MutableSharedFlow<UiEvent> = MutableSharedFlow()

    // Effect (side effects like error messages which we want to show only once)
    private val _effect: Channel<UiEffect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        _event.onEach {
            handleEvent(it)
        }.launchIn(viewModelScope)
    }

    abstract fun provideInitialState(): UiState

    open fun onLoadingChanged(loading: Boolean) {}

    protected fun updateState(reducer: UiState.() -> UiState) {
        val newState = viewState.value.reducer()
        _viewState.value = newState
    }

    fun onUiEvent(event: UiEvent) {
        viewModelScope.launch { _event.emit(event) }
    }

    abstract fun handleEvent(event: UiEvent)

    protected fun sendEffect(effectBuilder: () -> UiEffect) {
        viewModelScope.launch { _effect.send(effectBuilder()) }
    }

    protected suspend fun load(block: suspend () -> Unit) {
        // JAE require(runningJobs.get() >= 0)
        // JAE if (runningJobs.get() == 0) {
        // JAE     onLoadingChanged(true)
        // JAE }
        // JAE runningJobs.incrementAndGet()
        block()
        // JAE if (runningJobs.decrementAndGet() == 0) {
        // JAE     onLoadingChanged(false)
        // JAE }
    }
}

interface ViewState

interface ViewEvent

interface ViewEffect
