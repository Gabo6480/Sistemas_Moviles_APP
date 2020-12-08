package com.example.chordgalore

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chordgalore.data.LoginRepository
import com.example.chordgalore.data.SaveSharedPreference
import com.example.chordgalore.data.model.Categoria
import com.example.chordgalore.data.service.APIService
import kotlinx.android.synthetic.main.activity_new_song.*
import kotlinx.android.synthetic.main.custom_toolbar.*


class NewSongActivity : AppCompatActivity() {
    val RESULT_LOAD_IMAGE  = 1
    val arrayList = ArrayList<Categoria>()
    var added = false

    var publID = 0
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

        APIService.traerCategoria{cates, _ ->
            cates?.forEach {
                arrayList.add(it)
            }

            newSongGenreSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayList)
        }

        val extras = intent.extras
        if (extras != null) {
            publID = extras.getInt("SongID")

            LoginRepository.instance()?.user?.userId?.let { uID ->
                APIService.traer1Publicacion(publID,
                    uID
                ){ publi, t ->
                    if(publi != null){
                        newSongTitleEdit.setText(publi.titulo)
                        newSongContentEdit.setText(publi.texto)
                        arrayList.forEachIndexed { index, it ->
                            if(it.id == publi.genero)
                                newSongGenreSpinner.setSelection(index)
                        }

                        APIService.traerFotos(publi.id){ fotos, t ->
                            if (fotos != null) {
                                fotos.forEach {
                                    _currentBitmap.add(SaveSharedPreference.base64ToBitmap(it.imagen.replace("data:image/png;base64,", ""), this))
                                    _currentImages.add(createImageViewFromBitmap(_currentBitmap.last()));
                                }

                                APIService.deleteFoto(publi.id){_,_ ->

                                }
                            }
                        }
                    }
                }
            }
        }

        newSongPublicarBtn.setOnClickListener {
            val titulo = newSongTitleEdit.text.toString()
            var genero : Int = 0
            arrayList.forEach {
                if(it.nombre == newSongGenreSpinner.selectedItem.toString())
                    genero = it.id
            }
            val contenido = newSongContentEdit.text.toString()


            if(titulo.isNotEmpty() && genero != 0 && contenido.isNotEmpty() && _currentBitmap.size > 0) {
                var problem = true
                LoginRepository.instance()?.user?.userId?.let { it1 ->
                    if(publID == 0)
                        APIService.agregaPost(
                        titulo, genero, contenido, "Publicado",
                        it1
                    ) { pub, _ ->
                        _currentBitmap.forEach {
                            if (pub != null) {
                                APIService.agregaFoto(
                                    SaveSharedPreference.bitmapToBase64(it),
                                    pub.id
                                ) { b, _ ->
                                    if (!b!!) {
                                        Toast.makeText(
                                            applicationContext,
                                            "Hubo un problema al subir la imagen",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        problem = false
                                    }
                                }
                            } else {
                                Toast.makeText(
                                    applicationContext,
                                    "No se pudo localizar la publicación",
                                    Toast.LENGTH_LONG
                                ).show()
                                problem = false
                            }
                        }
                    }
                    else{
                        APIService.editaPost(publID, titulo, genero, contenido, "Publicado"){ b, _ ->
                            if(b!!){
                                _currentBitmap.forEach {
                                    APIService.agregaFoto(
                                        SaveSharedPreference.bitmapToBase64(it),
                                        publID
                                    ) { b, _ ->
                                        if (!b!!) {
                                            Toast.makeText(
                                                applicationContext,
                                                "Hubo un problema al subir la imagen",
                                                Toast.LENGTH_LONG
                                            ).show()
                                            problem = false
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
                added = problem

                if(added){
                    Toast.makeText(
                        applicationContext,
                        "Publicacion creada exitosamente",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                }
            }
            else
                Toast.makeText(
                    applicationContext,
                    "No se puede publicar hasta que todos los campos tengan un valor",
                    Toast.LENGTH_LONG
                ).show()


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
                val uri: Uri? = data.data
                // your codefor single image selection
                uri?.let { _currentBitmap.add(loadBitmapFromUri(it)) }?.let { _currentImages.add(createImageViewFromBitmap(_currentBitmap.last())) };
            }
        }
    }

    override fun onBackPressed() {
        if(!added) {
            val titulo = newSongTitleEdit.text.toString()
            var genero: Int = 0
            arrayList.forEach {
                if (it.nombre == newSongGenreSpinner.selectedItem)
                    genero = it.id
            }
            val contenido = newSongContentEdit.text.toString()

            if(publID == 0)
            LoginRepository.instance()?.user?.userId?.let { it1 ->
                APIService.agregaPost(
                    titulo, genero, contenido, "Borrador",
                    it1
                ) { pub, _ ->
                    _currentBitmap.forEach {
                        if (pub != null) {
                            APIService.agregaFoto(
                                SaveSharedPreference.bitmapToBase64(it),
                                pub.id
                            ) { b, _ ->
                                if (!b!!)
                                    Toast.makeText(
                                        applicationContext,
                                        "Hubo un problema al subir la imagen",
                                        Toast.LENGTH_LONG
                                    ).show()
                            }
                        }
                    }
                }
            }
            else{
                APIService.editaPost(publID, titulo, genero, contenido, "Borrador"){ b, _ ->
                    if(b!!){
                        _currentBitmap.forEach {
                            APIService.agregaFoto(
                                SaveSharedPreference.bitmapToBase64(it),
                                publID
                            ) { b, _ ->
                                if (!b!!) {
                                    Toast.makeText(
                                        applicationContext,
                                        "Hubo un problema al subir la imagen",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }

                        }
                    }
                }
            }

            Toast.makeText(
                applicationContext,
                "Publicacion guardada como borrador",
                Toast.LENGTH_LONG
            ).show()
        }

        super.onBackPressed()
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
