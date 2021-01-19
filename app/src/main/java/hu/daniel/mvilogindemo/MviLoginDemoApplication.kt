package hu.daniel.mvilogindemo

import android.app.Application
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MviLoginDemoApplication: Application() {

    @ExperimentalCoroutinesApi
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MviLoginDemoApplication)
            modules(KoinModule)
        }
    }
}