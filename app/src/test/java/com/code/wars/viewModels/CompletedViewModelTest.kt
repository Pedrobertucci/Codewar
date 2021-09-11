package com.code.wars.viewModels

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.code.wars.repositories.FakeUserRepository
import com.code.wars.view.profile.completed.CompletedViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class CompletedViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: CompletedViewModel

    private val fakeRepository = FakeUserRepository()

    @Mock
    private var context: Context = mock(Context::class.java)

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(context)

        viewModel = CompletedViewModel(repository = fakeRepository)
    }

    @Test
    fun `should fetch user completed code challenge and return a error`() = runBlockingTest {
        networkError()

        viewModel.getCompletedCodeChallenge("john123")

        val response = viewModel.errorLiveData.getOrAwaitValueTest()
        val loading = viewModel.loadingLiveData.getOrAwaitValueTest()

        assert(response.isNotEmpty())
        assert(!loading)
    }

    @Test
    fun `should fetch user completed code challenge and return http error`() = runBlockingTest {
        httpError()

        viewModel.getCompletedCodeChallenge("john123")

        val response = viewModel.errorLiveData.getOrAwaitValueTest()
        val loading = viewModel.loadingLiveData.getOrAwaitValueTest()

        assert(response.isNotEmpty())
        assert(!loading)
    }

    @Test
    fun `should fetch user complete code challenge and return empty`() = runBlockingTest {
        emptyValues()

        viewModel.getCompletedCodeChallenge("john123")

        val response = viewModel.emptyValuesLiveData.getOrAwaitValueTest()
        val loading = viewModel.loadingLiveData.getOrAwaitValueTest()

        assert(response)
        assert(!loading)
    }

    @Test
    fun `should fetch user complete code challenge and return some user`() = runBlockingTest {
        success()

        viewModel.getCompletedCodeChallenge("john123")

        val response = viewModel.completedLiveData.getOrAwaitValueTest(10)
        val loading = viewModel.loadingLiveData.getOrAwaitValueTest()

        assert(response.completed.isNotEmpty())
        assert(!loading)
    }

    private fun networkError() {
        fakeRepository.shouldReturnNetworkError = true
        fakeRepository.shouldReturnEmptyValues = false
        fakeRepository.shouldReturnHTTPError = false
    }

    private fun httpError() {
        fakeRepository.shouldReturnNetworkError = false
        fakeRepository.shouldReturnEmptyValues = false
        fakeRepository.shouldReturnHTTPError = true
    }

    private fun emptyValues() {
        fakeRepository.shouldReturnNetworkError = false
        fakeRepository.shouldReturnEmptyValues = true
        fakeRepository.shouldReturnHTTPError = false
    }

    private fun success() {
        fakeRepository.shouldReturnNetworkError = false
        fakeRepository.shouldReturnEmptyValues = false
        fakeRepository.shouldReturnHTTPError = false
    }
}