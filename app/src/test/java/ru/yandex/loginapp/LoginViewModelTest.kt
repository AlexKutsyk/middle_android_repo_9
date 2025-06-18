package ru.yandex.loginapp

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class LoginViewModelTest {
    private lateinit var loginViewModel: LoginViewModel
    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        loginViewModel = LoginViewModel()
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getState() {
    }

    @Test
    fun emptyValueLoginTest() {
        val testEmail = ""
        val testLogin = ""
        val expect = LoginScreenState.EmptyFieldsError
        loginViewModel.login(testEmail, testLogin)
        val actual = loginViewModel.state.value
        assertEquals(actual, expect)
    }

    @Test
    fun wrongValueLoginTest() {
        val testEmail = "email"
        val testLogin = "pass"
        val expect = LoginScreenState.EmailValidationError
        loginViewModel.login(testEmail, testLogin)
        val actual = loginViewModel.state.value
        assertEquals(actual, expect)
    }

    @Test
    fun loadingStateLoginTest() {
        val testEmail = "email@ya.ru"
        val testLogin = "pass"
        val expect = LoginScreenState.Loading
        loginViewModel.login(testEmail, testLogin)
        testDispatcher.scheduler.runCurrent()
        val actual = loginViewModel.state.value
        assertEquals(actual, expect)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun successStateLoginTest() {
        val testEmail = "email@ya.ru"
        val testLogin = "pass"
        val expect = LoginScreenState.Success
        loginViewModel.login(testEmail, testLogin)
        testDispatcher.scheduler.runCurrent()
        testDispatcher.scheduler.advanceTimeBy(3001L)
        val actual = loginViewModel.state.value
        assertEquals(actual, expect)
    }
}