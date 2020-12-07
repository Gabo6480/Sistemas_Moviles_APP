package com.example.chordgalore.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.chordgalore.MainActivity
import com.example.chordgalore.R
import com.example.chordgalore.ui.register.RegisterActivity
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

        login_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        //Esta funcion crea toda la logica de la validación
        formLogic()
    }

    private fun formLogic() {
        loginViewModel = ViewModelProvider(this)
            .get(LoginViewModel::class.java)

        //Esto revisa la validez de ambos campos del login
        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // desactivar el boton de login a menos que ambos campos dean validos
            login_button.isEnabled = loginState.isDataValid

            login_edit_mail.error = if (loginState.usernameError != null) getString(loginState.usernameError) else ""
            login_edit_pass.error = if (loginState.passwordError != null) getString(loginState.passwordError) else ""
        })

        //Una vez que se haya completado de procesar el resultado del login
        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            //Desactivamos el icono de login
            login_loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            setResult(Activity.RESULT_OK)
            if(loginResult.success != null) {
                Toast.makeText(applicationContext, "¡Bienvenido " + loginResult.success + "!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
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

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
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
