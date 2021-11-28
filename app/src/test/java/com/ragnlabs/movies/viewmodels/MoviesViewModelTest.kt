package com.ragnlabs.movies.viewmodels

import com.ragnlabs.movies.api.ApiService
import com.ragnlabs.movies.models.MovieResponse
import com.ragnlabs.movies.repository.MovieRepository
import com.ragnlabs.movies.util.Resource
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Suite
import retrofit2.Response
import java.lang.reflect.Method

@ExperimentalCoroutinesApi
@RunWith(Suite::class)
@Suite.SuiteClasses(
    MoviesViewModelhandleSearchNewsResponseTest::class
)

class MoviesViewModelTest

@ExperimentalCoroutinesApi
class MoviesViewModelhandleSearchNewsResponseTest {
// https://stackoverflow.com/questions/58303961/kotlin-coroutine-unit-test-fails-with-module-with-the-main-dispatcher-had-faile

    private lateinit var viewModel: MoviesViewModel
    private lateinit var repository: MovieRepository
    private val service = mockk<ApiService>()
    private lateinit var privateHandleResponse: Method
    private var response: Response<MovieResponse> = mockk()
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)

        repository = MovieRepository(service)
        viewModel = MoviesViewModel(repository)

        initConfigMethodHandleResponseToPublic()
    }

    @Test
    fun `handle response is returning Success when response is successful`() {

        val expected = Resource.Success(response)
        // Given
        every { response.isSuccessful } returns true
        // When
        val result = privateHandleResponse.invoke(response)

        // Then
        assertEquals(expected, result)
    }

    private fun initConfigMethodHandleResponseToPublic() {
        // Get the method from the class under test
        privateHandleResponse =
            viewModel.javaClass.getDeclaredMethod(
                "handleResponse",
                MoviesViewModel::class.java
            )
        // Make the private method accessible now (it's no more a private method)
        privateHandleResponse.isAccessible = true
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }
}
