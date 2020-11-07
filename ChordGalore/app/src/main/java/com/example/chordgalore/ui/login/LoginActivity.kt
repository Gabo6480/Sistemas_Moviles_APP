package com.example.chordgalore.ui.login

import android.app.Activity
import android.graphics.Color
import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.view.Menu
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

import com.example.chordgalore.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.custom_toolbar.*

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Declaramos el contenido de esta actividad
        setContentView(R.layout.activity_login)

        //Indicamos que vamos a usar una toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true); //Activamos la flechita de regreso

        //Esta funcion crea toda la logica de la validación
        formLogic()
    }

    private fun formLogic() {
        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        //Esto revisa la validez de ambos campos del login
        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // desactivar el boton de login a menos que ambos campos dean validos
            login_button.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                login_edit_mail.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                login_edit_pass.error = getString(loginState.passwordError)
            }
        })

        //Una vez que se haya completado de procesar el resultado del login
        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            //Desactivamos el icono de login
            login_loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })

        login_edit_mail.editText?.afterTextChanged {
            loginViewModel.loginDataChanged(
                login_edit_mail.editText?.text.toString(),
                login_edit_pass.editText?.text.toString()
            )
        }

        login_edit_pass.editText?.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    login_edit_mail.editText?.text.toString(),
                    login_edit_pass.editText?.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            login_edit_mail.editText?.text.toString(),
                            login_edit_pass.editText?.text.toString()
                        )
                }
                false
            }

            //Creamos el evento onClick del botón
            login_button.setOnClickListener {
                //Activamos la visibilidad de el icono de login
                login_loading.visibility = View.VISIBLE
                loginViewModel.login(
                    login_edit_mail.editText?.text.toString(),
                    login_edit_pass.editText?.text.toString()
                )
            }
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
