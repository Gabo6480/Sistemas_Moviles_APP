package com.example.chordgalore

import android.os.Bundle
import android.widget.Toast
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
    }
}
