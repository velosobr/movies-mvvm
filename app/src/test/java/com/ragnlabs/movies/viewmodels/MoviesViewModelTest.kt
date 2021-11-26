package com.ragnlabs.movies.viewmodels

import com.ragnlabs.movies.api.ApiService
import com.ragnlabs.movies.models.MovieResponse
import com.ragnlabs.movies.repository.MovieRepository
import com.ragnlabs.movies.util.Resource
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response
import java.lang.reflect.Method

@RunWith(JUnit4::class)
class MoviesViewModelhandleSearchNewsResponse {

    private lateinit var viewModel: MoviesViewModel
    private lateinit var repository: MovieRepository
    private val service = mockk<ApiService>()
    private lateinit var privatehandleSearchMoviesResponse: Method
    private var response: Response<MovieResponse> = mockk()

    @Before
    fun setup() {
        repository = MovieRepository(service)
        viewModel = MoviesViewModel(repository)
        initConfigMethodhandleSearchMoviesResponseToPublic()
    }

    @Test
    fun `handle search movies response is returning Success when response is successful`() {

        val expected = Resource.Success(response)
        // Given
        every { response.isSuccessful } returns true
        // When
        val result = privatehandleSearchMoviesResponse.invoke(response)

        // Then
        assertEquals(expected, result)
    }

    private fun initConfigMethodhandleSearchMoviesResponseToPublic() {
        // Get the method from the class under test
        privatehandleSearchMoviesResponse =
            viewModel.javaClass.getDeclaredMethod(
                "handleSearchMoviesResponse",
                MoviesViewModel::class.java
            )
        // Make the private method accessible now (it's no more a private method)
        privatehandleSearchMoviesResponse.isAccessible = true
    }
}