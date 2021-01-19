package hu.daniel.mvilogindemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class ViewModel(
    private val repository: IRepository
) : ViewModel() {
    private val intentChannel = Channel<Intent>(Channel.UNLIMITED)
    private val stateFlow = MutableStateFlow<State>(State.Idle)
    val state: StateFlow<State>
        get() = stateFlow

    init {
        viewModelScope.launch { handleIntents() }
    }

    private suspend fun handleIntents() {
        intentChannel.consumeEach { intent ->
            when (intent) {
                is Intent.Login -> {
                    val user: User
                    stateFlow.value = State.Loading
                    try {
                        repository.validateLoginCredentials(intent.name, intent.password)
                        user = repository.login(intent.name, intent.password)
                    } catch (exp: LoginErrorException) {
                        stateFlow.value = State.Error
                        return@consumeEach
                    }
                    stateFlow.value = State.Success(user)
                }
                Intent.Idle -> stateFlow.value = State.Idle
            }
        }
    }

    fun send(intent: Intent) = viewModelScope.launch { intentChannel.send(intent) }

    sealed class Intent {
        object Idle : Intent()
        data class Login(val name: String, val password: String) : Intent()
    }

    sealed class State {
        object Idle : State()
        object Error : State()
        object Loading : State()
        data class Success(val user: User) : State()
    }
}