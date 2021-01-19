package hu.daniel.mvilogindemo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class Activity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity2)
        val user = getUserFrom(intent)
        greetUser(user)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val user = getUserFrom(intent)
        greetUser(user)
    }

    private fun greetUser(user: User?) {
        if (user != null) {
            greetings.text = getString(R.string.welcome).replace("[0]", user.name)
        }
    }

    private fun getUserFrom(intent: Intent): User? {
        return if (intent.hasExtra("User")) intent.getSerializableExtra("User") as User else null
    }
}