package com.example.chordgalore.data

import android.content.Context
import android.graphics.BitmapFactory
import com.example.chordgalore.R
import com.example.chordgalore.data.model.LoggedInUser
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

    fun login(username: String, password: String): Result<LoggedInUser> {
        // handle login
        val result = loginAuth(username, password)

        if (result is Result.Success) {
            setLoggedInUser(result.data)
        }

        return result
    }

    fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        SaveSharedPreference.setUserData( context, loggedInUser)
    }

    private fun loginAuth(username: String, password: String): Result<LoggedInUser> {
        return try {
            // TODO: handle loggedInUser authentication
            val fakeUser = LoggedInUser(
                java.util.UUID.randomUUID().toString(),
                "Jane Doe",
                BitmapFactory.decodeResource(context.resources, R.drawable.user_image),
                BitmapFactory.decodeResource(context.resources, R.drawable.default_image))
            Result.Success(fakeUser)
        } catch (e: Throwable) {
            Result.Error(IOException("Error logging in", e))
        }
    }
}
