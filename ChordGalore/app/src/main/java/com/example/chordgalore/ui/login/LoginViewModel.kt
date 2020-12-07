package com.example.chordgalore.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.example.chordgalore.data.LoginRepository
import com.example.chordgalore.data.Result

import com.example.chordgalore.R
import com.example.chordgalore.data.DataDbHelper

class LoginViewModel : ViewModel() {
    var DBsqlite= LoginRepository.instance()?.context?.let { DataDbHelper(it) }
    //Esto se usa para guardar el estado de la forma de ingreso
    data class LoginFormState(
        val usernameError: Int? = null,
        val passwordError: Int? = null,
        val isDataValid: Boolean = false
    )
    data class LoginResult(
        val success: String? = null,
        val error: Int? = null
    )

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        val result = LoginRepository.instance()?.login(username, password){
            if (it is Result.Success) {
                _loginResult.value =
                    LoginResult(success = it.data.displayName)
            } else {
                _loginResult.value = LoginResult(error = R.string.login_failed)
            }
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_usermail)
            return
        }
        if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password_short)
            return
        }


        _loginForm.value = LoginFormState(isDataValid = true)

    }

    // A placeholder username validation checkit
    private fun isUserNameValid(username: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(username).matches()
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}
