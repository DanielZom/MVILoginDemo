package hu.daniel.mvilogindemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import androidx.lifecycle.lifecycleScope
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class Activity : AppCompatActivity() {

    private val viewModel by viewModel<ViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity)

        observeState()

        login.setOnClickListener {
            viewModel.send(ViewModel.Intent.Login(name.text.toString(), password.text.toString()))
        }
    }

    private fun observeState() {
        viewModel.state
                .onEach { state -> handleState(state) }
                .launchIn(lifecycleScope)
    }

    private fun handleState(state: ViewModel.State) {
        when (state) {
            is ViewModel.State.Idle -> {
                if (name.text.isNotEmpty() || password.text.isNotEmpty()) {
                    loginTitle.text = getString(R.string.login_title)
                    name.text.clear()
                    password.text.clear()
                }
            }
            is ViewModel.State.Error -> loginTitle.text = getString(R.string.error)
            is ViewModel.State.Loading -> loginTitle.text = getString(R.string.loading)
            is ViewModel.State.Success -> startActivity(Intent(this, Activity2::class.java).apply {
                putExtra("User", state.user)
            })
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.send(ViewModel.Intent.Idle)
    }
}