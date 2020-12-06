package com.example.chordgalore

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.custom_toolbar.*

class SongActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song)

        //Indicamos que vamos a usar una toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //Activamos la flechita de regreso

        val extras = intent.extras
        if (extras != null) {
            val value = extras.getInt("SongID")

            Toast.makeText(
                applicationContext,
                value.toString(),
                Toast.LENGTH_LONG
            ).show()
        }

        val imageContainer = findViewById<LinearLayout>(R.id.image_container)

        repeat(3) {
            val newImage = ImageView(this)
            newImage.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT)
            newImage.setImageResource(R.drawable.default_image)
            newImage.scaleType = ImageView.ScaleType.CENTER_INSIDE
            newImage.adjustViewBounds = true
            newImage.setPadding(0,0,16,0)
            imageContainer.addView(newImage)
        }

        val textoConatiner = findViewById<TextView>(R.id.TextoCancion)
        textoConatiner.text = "Esla hora de jugar"
    }
}
