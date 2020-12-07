package com.example.chordgalore.ui.register

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chordgalore.R
import com.example.chordgalore.data.DataDbHelper
import com.example.chordgalore.data.LoginRepository
import com.example.chordgalore.data.Result
import com.example.chordgalore.data.SaveSharedPreference
import com.example.chordgalore.data.model.ImageUtilities
import com.example.chordgalore.data.model.UsuariosLocal
import com.example.chordgalore.data.service.APIService

class RegisterViewModel : ViewModel() {
    var DBsqlite= LoginRepository.instance()?.context?.let { DataDbHelper(it) }
    //Esto se usa para guardar el estado de la forma de ingreso
    data class RegisterFormState(
        val usermailError: Int? = null,
        val usernameError: Int? = null,
        val passwordError: Int? = null,
        val passconfirmError: Int? = null,
        val isDataValid: Boolean = false
    )
    data class RegisterResult(
        val success: String? = null,
        val error: Int? = null
    )

    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registerForm

    private val _registerResult = MutableLiveData<RegisterResult>()
    val registerResult: LiveData<RegisterResult> = _registerResult

    fun register(username: String, usermail: String, password: String, passconfirm: String, context: Context) {
        val name = username.substring(0, username.indexOf(" "))
        val last = username.substring(username.indexOf(" ") + 1)
        APIService.registroUsuario(name, last,usermail,password, SaveSharedPreference.bitmapToBase64(
            BitmapFactory.decodeResource(context.resources, R.drawable.user))){res, t ->

            if (res != null) {
                if(res) {
                    _registerResult.value =
                        RegisterResult(success = res.toString())
                    var Userin : UsuariosLocal = UsuariosLocal()
                    Userin.Nombre=name
                    Userin.Apellidos=last
                    Userin.Email=usermail
                    Userin.Password =password
                    Userin.imgArray = ImageUtilities.getByteArrayFromResourse( R.drawable.user ,context)
                    DBsqlite?.insertUsuarios(UsuariosLocal())
                }
                else
                    _registerResult.value = RegisterResult(error = R.string.register_failed)
            } else {
                _registerResult.value = RegisterResult(error = R.string.register_failed)
            }

        }
    }

    fun registerDataChanged(username: String, usermail: String, password: String, passconfirm: String) {


        if (!isUserMailValid(usermail)){
            _registerForm.value = RegisterFormState(usermailError = R.string.invalid_usermail)
            return
        }

        if (!isUsernameValid(username)){
            _registerForm.value = RegisterFormState(usernameError = R.string.invalid_username)
            return
        }

        val res = isPasswordValid(password)
        if(res == 1) {
            _registerForm.value = RegisterFormState(passwordError = R.string.invalid_password_short)
            return
        }
        else if(res == 2){
            _registerForm.value = RegisterFormState(passwordError = R.string.invalid_password_content)
            return
        }

        if(!isPassConfirmValid(password, passconfirm)){
            _registerForm.value = RegisterFormState(passconfirmError = R.string.invalid_passconfirm)
            return
        }


        _registerForm.value = RegisterFormState(isDataValid = true)


    }

    private fun isUsernameValid(username: String): Boolean {
        return username.contains(Regex("[A-Za-z]+\\s[A-Za-z]+"))
    }

    // A placeholder username validation check
    private fun isUserMailValid(usermail: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(usermail).matches()
    }

    private fun isPassConfirmValid(password1: String, password2: String): Boolean {
        return password1 == password2
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Int {
        return if (Regex("^(?=.*\\d+)(?=.*[a-z]+)(?=.*[A-Z]+)(?=.*[a-zA-Z\\d]*).{8,}\$", RegexOption.MULTILINE).matches(password))
            0
        else if(password.length < 8)
            1
        else
            2
    }
}