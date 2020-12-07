package com.example.chordgalore

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.example.chordgalore.data.LoginRepository
import com.example.chordgalore.data.model.LoggedInUser
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.custom_toolbar.*

class ProfileActivity : AppCompatActivity() {
    val RESULT_LOAD_PROFILE  = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        //Indicamos que vamos a usar una toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true); //Activamos la flechita de regreso

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ListFragment(0)).commit()


        profile_name.text = LoginRepository.instance()?.user?.displayName
        profile_image.setImageBitmap(LoginRepository.instance()?.user?.profileBitmap)


        buttonLoadPP.setOnClickListener {
            val getIntent = Intent(Intent.ACTION_GET_CONTENT)
            getIntent.type = "image/*"
            startActivityForResult(getIntent, RESULT_LOAD_PROFILE);
        }
    }

    var newProfileBitmap : Bitmap? = null
    var newPortadaBitmap : Bitmap? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            val uri: Uri? = data.data

            if(requestCode == RESULT_LOAD_PROFILE){
                uri?.let { loadBitmapFromUri(it) }?.let { newProfileBitmap = it }
                profile_image.setImageBitmap(newProfileBitmap)
            }
        }
    }

    override fun onStop () {
        super.onStop()

        if(newProfileBitmap != null) {
            val user = LoginRepository.instance()?.user

            val newUser = user?.let { LoggedInUser(it.userId, it.displayName, newProfileBitmap ?: it.profileBitmap) }
            if (newUser != null) {
                LoginRepository.instance()?.setLoggedInUser(newUser)
            }
        }


    }

    private fun loadBitmapFromUri(imageUri: Uri): Bitmap {
        return if (Build.VERSION.SDK_INT >= 29) {
            ImageDecoder.decodeBitmap(
                ImageDecoder.createSource(
                    contentResolver,
                    imageUri
                )
            )
        }
        else
            MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
    }
}
