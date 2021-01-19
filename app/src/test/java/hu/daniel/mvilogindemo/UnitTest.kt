package hu.daniel.mvilogindemo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class UnitTest : KoinTest {
    private val viewModel : ViewModel by inject()
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        startKoin { modules(KoinModule) }
    }

    @Test
    fun successLoginTest() {
        val user = User("john@doe.com", "12345aA&")
        val intent = ViewModel.Intent.Login(user.name, user.password)
        runBlockingTest {
            viewModel.send(intent)
            assertTrue(viewModel.state.value is ViewModel.State.Success)
        }
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
        stopKoin()
    }
}