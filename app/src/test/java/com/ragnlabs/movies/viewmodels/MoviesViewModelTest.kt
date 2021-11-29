package com.ragnlabs.movies.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ragnlabs.movies.models.MovieResponse
import com.ragnlabs.movies.util.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class MoviesViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val repository: MovieRepository = mockk()
    private val observer: Observer<Resource<MovieResponse>> = mockk(relaxed = true)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `when get upcoming movies is called then it should call repository get up`() {
        val response: Response<MovieResponse> = mockk()
        coEvery { repository.getUpcomingMovies(1) } returns response
        instantiate().getUpcomingMovies()

        coVerify { repository.getUpcomingMovies(1) }
    }

    private fun instantiate(): MoviesViewModel {
        val viewModel = MoviesViewModel(repository)
        viewModel.upcomingMoviesList.observeForever(observer)
        return viewModel
    }
}
