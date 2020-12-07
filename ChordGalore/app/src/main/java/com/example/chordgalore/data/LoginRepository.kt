package com.example.chordgalore.data

import android.content.Context
import android.graphics.BitmapFactory
import com.example.chordgalore.R
import com.example.chordgalore.data.model.LoggedInUser
import com.example.chordgalore.data.service.APIService
import java.io.IOException

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository private constructor() {
    //singleton
    companion object{
        private val holder: LoginRepository? = LoginRepository()
        fun instance(): LoginRepository? {
            return holder
        }
    }

    lateinit var context : Context

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun autoLogin(){
        if(user == null)
            user = SaveSharedPreference.getUserData(context)
    }

    fun logout() {
        user = null
        SaveSharedPreference.clearUserName(context)
    }

    fun login(username: String, password: String, onResult : (result:Result<LoggedInUser>) -> Unit){
        // handle login
        APIService.logInUsuario(username, password) { it, t ->
            if(it != null){
                val user = LoggedInUser(
                    it.id.toString(),
                    it.nombre,
                    SaveSharedPreference.base64ToBitmap(it.imagen.replace("data:image/png;base64,",""),context))
                setLoggedInUser(user)
                onResult(Result.Success(user))
            }
            else{
                onResult(Result.Error(IOException("Error logging in", t)))
            }
        }
    }

    fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        SaveSharedPreference.setUserData( context, loggedInUser)
    }

}
