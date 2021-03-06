package com.code.wars.viewModels

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.code.wars.repositories.FakeUserRepository
import com.code.wars.view.search.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class SearchViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: SearchViewModel

    private val fakeRepository = FakeUserRepository()

    @Mock
    private var context: Context = mock(Context::class.java)

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(context)

        viewModel = SearchViewModel(repository = fakeRepository)
    }

    @Test
    fun `should fetch user and return a generic error`() = runBlockingTest {
         networkError()

        viewModel.getUser("john123")

        val response = viewModel.networkErrorLiveData.getOrAwaitValueTest()
        val loading = viewModel.loadingLiveData.getOrAwaitValueTest()

        assert(response)
        assert(!loading)
    }

    @Test
    fun `should fetch user and return http error`() = runBlocking {
        httpError()

        viewModel.getUser("john123")

        val response = viewModel.errorLiveData.getOrAwaitValueTest()
        val loading = viewModel.loadingLiveData.getOrAwaitValueTest()

        assert(response.isNotEmpty())
        assert(!loading)
    }

    @Test
    fun `should fetch user and return empty`() = runBlockingTest {
        emptyValues()

        viewModel.getUser("john123")

        val response = viewModel.emptyValuesLiveData.getOrAwaitValueTest()
        val loading = viewModel.loadingLiveData.getOrAwaitValueTest()

        assert(response)
        assert(!loading)
    }

    @Test
    fun `should fetch user and return some user`() = runBlockingTest {
        success()

        viewModel.getUser("john123")

        val response = viewModel.userLiveData.getOrAwaitValueTest()
        val loading = viewModel.loadingLiveData.getOrAwaitValueTest()

        assert(response.username.isNotEmpty())
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