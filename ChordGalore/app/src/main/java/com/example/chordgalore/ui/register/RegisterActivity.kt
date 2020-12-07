package com.example.chordgalore.ui.register

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.chordgalore.R
import com.example.chordgalore.ui.login.afterTextChanged
import kotlinx.android.synthetic.main.custom_toolbar.*
import com.example.chordgalore.ui.register.RegisterViewModel
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //Indicamos que vamos a usar una toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true); //Activamos la flechita de regreso

        formLogic()
    }

    private fun formLogic() {
        registerViewModel = ViewModelProvider(this)
            .get(RegisterViewModel::class.java)

        //Esto revisa la validez de ambos campos del login
        registerViewModel.registerFormState.observe(this@RegisterActivity, Observer {
            val registerState = it ?: return@Observer

            register_button.isEnabled = registerState.isDataValid

            register_edit_name.error = if (registerState.usernameError != null) getString(registerState.usernameError) else ""
            register_edit_mail.error = if (registerState.usermailError != null) getString(registerState.usermailError) else ""
            register_edit_pass.error = if (registerState.passwordError != null) getString(registerState.passwordError) else ""
            register_edit_confirm.error = if (registerState.passconfirmError != null) getString(registerState.passconfirmError) else ""
        })

        //Una vez que se haya completado de procesar el resultado del login
        registerViewModel.registerResult.observe(this@RegisterActivity, Observer {
            val registerResult = it ?: return@Observer

            //Desactivamos el icono de login
            register_loading.visibility = View.GONE
            if (registerResult.error != null) {
                Toast.makeText(applicationContext, "Ha fallado el registro", Toast.LENGTH_SHORT).show()
            }
            setResult(Activity.RESULT_OK)
            if(registerResult.success != null) {
                Toast.makeText(applicationContext, "¡Cuenta registrada exitosamente!", Toast.LENGTH_SHORT).show()
                finish()
            }
        })

        register_edit_mail.editText?.afterTextChanged {
            registerViewModel.registerDataChanged(
                register_edit_name.editText?.text.toString(),
                register_edit_mail.editText?.text.toString(),
                register_edit_pass.editText?.text.toString(),
                register_edit_confirm.editText?.text.toString()
            )
        }

        register_edit_name.editText?.afterTextChanged {
            registerViewModel.registerDataChanged(
                register_edit_name.editText?.text.toString(),
                register_edit_mail.editText?.text.toString(),
                register_edit_pass.editText?.text.toString(),
                register_edit_confirm.editText?.text.toString()
            )
        }

        register_edit_pass.editText?.afterTextChanged {
            registerViewModel.registerDataChanged(
                register_edit_name.editText?.text.toString(),
                register_edit_mail.editText?.text.toString(),
                register_edit_pass.editText?.text.toString(),
                register_edit_confirm.editText?.text.toString()
            )
        }

        register_edit_confirm.editText?.apply {
            afterTextChanged {
                registerViewModel.registerDataChanged(
                    register_edit_name.editText?.text.toString(),
                    register_edit_mail.editText?.text.toString(),
                    register_edit_pass.editText?.text.toString(),
                    register_edit_confirm.editText?.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        registerViewModel.register(
                            register_edit_name.editText?.text.toString(),
                            register_edit_mail.editText?.text.toString(),
                            register_edit_pass.editText?.text.toString(),
                            register_edit_confirm.editText?.text.toString(),
                            context
                        )
                }
                false
            }

            //Creamos el evento onClick del botón
            register_button.setOnClickListener {
                //Activamos la visibilidad de el icono de login
                register_loading.visibility = View.VISIBLE
                registerViewModel.register(
                    register_edit_name.editText?.text.toString(),
                    register_edit_mail.editText?.text.toString(),
                    register_edit_pass.editText?.text.toString(),
                    register_edit_confirm.editText?.text.toString(),
                    context
                )
            }
        }
    }
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
