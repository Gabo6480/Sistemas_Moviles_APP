package com.example.chordgalore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //login_confirm_button.setOnClickListener{object: View.OnClickListener{
        //    override fun onClick(p0: View?) {
        //        if(!validateEmail() || !validatePassword()){
        //            return
        //        }
        //    }
        //} }

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    //private fun validateEmail(): Boolean {
    //    val emailInput = login_edit_mail.editText?.text.toString().trim()
//
    //    return if(emailInput.isEmpty()){
    //        login_edit_mail.error = "Este campo no puede estar vacío"
    //        false
    //    } else{
    //        login_edit_mail.error = ""
    //        true
    //    }
    //}
//
    //private fun validatePassword(): Boolean {
    //    val passInput = login_edit_pass.editText?.text.toString().trim()
//
    //    return if(passInput.isEmpty()){
    //        login_edit_pass.error = "Este campo no puede estar vacío"
    //        false
    //    } else{
    //        login_edit_pass.error = ""
    //        true
    //    }
    //}
}
