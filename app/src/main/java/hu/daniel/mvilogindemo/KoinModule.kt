package hu.daniel.mvilogindemo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val KoinModule = module {
    viewModel { ViewModel(get()) }
    single <IRepository> { Repository() }
}