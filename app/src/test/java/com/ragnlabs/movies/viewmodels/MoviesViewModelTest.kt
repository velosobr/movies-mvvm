package com.ragnlabs.movies.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ragnlabs.movies.models.Movie
import com.ragnlabs.movies.repository.MovieRepository
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

@ExperimentalCoroutinesApi
class MoviesViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val repository: MovieRepository = mockk(relaxed = true)
    private val observer: Observer<List<Movie>> = mockk(relaxed = true)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `when get upcoming movies is called then it should call repository get up`() {
        val movie = Movie(
            false,
            "/70nxSw3mFBsGmtkvcs91PbjerwD.jpg",
            listOf(
                878,
                28,
                12
            ),
            580489,
            "en",
            "Venom: Let There Be Carnage",
            "After finding a host body in investigative reporter Eddie Brock, the alien symbiote must face a new enemy, Carnage, the alter ego of serial killer Cletus Kasady.",
            22273.069,
            "/rjkmN1dniUHVYAtwuV3Tji7FsDO.jpg",
            "2021-09-30",
            "Venom: Let There Be Carnage",
            false,
            7.2,
            3295
        )
        val response = listOf(movie)
        coEvery { repository.getTopRatedMovies(1) } returns response
        instantiate().getTopRatedMovies()

        coVerify { repository.getTopRatedMovies(1) }
    }

    private fun instantiate(): MoviesViewModel {
        val viewModel = MoviesViewModel(repository)
        viewModel.topRatedMoviesList.observeForever(observer)
        return viewModel
    }
}
