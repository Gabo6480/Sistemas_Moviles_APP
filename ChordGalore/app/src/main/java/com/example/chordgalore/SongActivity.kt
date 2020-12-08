package com.example.chordgalore

import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.chordgalore.data.LoginRepository
import com.example.chordgalore.data.SaveSharedPreference
import com.example.chordgalore.data.service.APIService
import kotlinx.android.synthetic.main.activity_song.*
import kotlinx.android.synthetic.main.custom_toolbar.*

class SongActivity : AppCompatActivity() {
    var favorito = false
    var publID = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song)

        //Indicamos que vamos a usar una toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //Activamos la flechita de regreso

        val extras = intent.extras
        if (extras != null) {
            publID = extras.getInt("SongID")

            LoginRepository.instance()?.user?.userId?.let { uID ->
                APIService.traer1Publicacion(publID, uID){publi, _ ->
                    if (publi != null) {
                        post_tittle.text = publi.titulo
                        post_subtitle.text = publi.nombre
                        post_genre.text = publi.generoN
                        post_image.setImageBitmap(SaveSharedPreference.base64ToBitmap(publi.imagen.replace("data:image/png;base64,",""), this))
                        TextoCancion.text = publi.texto

                        if(publi.favorito == 1) {
                            buttonFav.setBackgroundColor(ContextCompat.getColor(this,R.color.colorAccent))
                            favorito = true
                        }

                        if(publi.idUser == uID) {
                            buttonDelete.visibility = View.VISIBLE
                        }
                        APIService.traerFotos(publID){fotos, _ ->
                            fotos?.forEach {
                                val newImage = ImageView(this)
                                newImage.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT)
                                newImage.setImageBitmap(SaveSharedPreference.base64ToBitmap(it.imagen.replace("data:image/png;base64,",""), this))
                                newImage.scaleType = ImageView.ScaleType.CENTER_INSIDE
                                newImage.adjustViewBounds = true
                                newImage.setPadding(0,0,16,0)
                                image_container.addView(newImage)
                            }
                        }

                    }
                    else
                        Toast.makeText(
                            applicationContext,
                            "Error de carga de la publicación",
                            Toast.LENGTH_LONG
                        ).show()
                }
            }

        }
        else
            Toast.makeText(
                applicationContext,
                "Error inesperado de la publicación",
                Toast.LENGTH_LONG
            ).show()


        buttonFav.setOnClickListener {
            LoginRepository.instance()?.user?.userId?.let { it1 ->
                APIService.agregaFavorito(publID,
                    it1,
                    if(favorito) 0 else 1
                ){b, _ ->
                    if(b!!){
                        favorito = !favorito
                        buttonFav.setBackgroundColor(ContextCompat.getColor(this, if(favorito) R.color.colorAccent else R.color.design_default_color_background))
                    }
                }
            }
        }

        buttonDelete.setOnClickListener {
            APIService.deletePost(publID){b, _ ->
                if(b!!){
                    finish()
                }
            }
        }
    }
}
