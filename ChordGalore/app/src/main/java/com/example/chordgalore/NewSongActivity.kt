package com.example.chordgalore

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_new_song.*
import kotlinx.android.synthetic.main.custom_toolbar.*


class NewSongActivity : AppCompatActivity() {
    val RESULT_LOAD_IMAGE  = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_song)

        //Indicamos que vamos a usar una toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //Activamos la flechita de regreso

        buttonLoadPicture.setOnClickListener{
            val getIntent = Intent(Intent.ACTION_GET_CONTENT)
            getIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            getIntent.type = "image/*"
            startActivityForResult(getIntent, RESULT_LOAD_IMAGE);
        }
    }

    var _currentImages : MutableList<ImageView> = mutableListOf()
    var _currentBitmap : MutableList<Bitmap> = mutableListOf()


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == RESULT_LOAD_IMAGE && data != null) {
            val clipData = data.clipData

            for (view in _currentImages){
                image_container.removeView(view)
            }
            _currentImages.clear()
            _currentBitmap.clear()

            if (clipData != null) {
                for (i in 0 until clipData.itemCount) {
                    if(i >= 3)
                        break
                    val imageUri: Uri = clipData.getItemAt(i).uri

                    _currentBitmap.add(loadBitmapFromUri(imageUri))
                    _currentImages.add(createImageViewFromBitmap(_currentBitmap.last()));
                }
            } else {
                if(data.data != null) {
                    val uri: Uri? = data.data
                    // your codefor single image selection
                    uri?.let { _currentBitmap.add(loadBitmapFromUri(it)) }?.let { _currentImages.add(createImageViewFromBitmap(_currentBitmap.last())) };
                }
            }
        }
    }

    private fun createImageViewFromBitmap(bitmap: Bitmap): ImageView {
        val newImage = ImageView(this)
        newImage.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        newImage.setImageBitmap(bitmap)
        newImage.scaleType = ImageView.ScaleType.CENTER_INSIDE
        newImage.adjustViewBounds = true
        newImage.setPadding(0, 0, 16, 0)
        image_container.addView(newImage)

        return newImage
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
