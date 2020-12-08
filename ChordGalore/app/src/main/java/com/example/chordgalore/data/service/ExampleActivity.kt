package com.example.chordgalore.data.service

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import com.example.chordgalore.data.model.Publicacion
import com.example.chordgalore.data.model.Usuarios
import com.example.chordgalore.data.model.Fotos
import com.example.chordgalore.data.model.Favoritos
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.util.*
abstract class ExampleActivity : AppCompatActivity(), View.OnClickListener  {
    var txtMessage:MultiAutoCompleteTextView? = null
    var txtId:TextView? = null
    var imageUI:ImageView? =  null
    var imgArray:ByteArray? =  null
    /*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnConsultarPaises =  findViewById<Button>(R.id.btnConsultarPaises)
        btnConsultarPaises.setOnClickListener(this)
        val btnConcultaPais =  findViewById<Button>(R.id.btnConsultarPais)
        btnConcultaPais.setOnClickListener(this)
        val btnCamera = findViewById<ImageButton>(R.id.btnCamera)
        btnCamera.setOnClickListener(this)
        val btnEnviarImage =  findViewById<Button>(R.id.btnEnviarImagen)
        btnEnviarImage.setOnClickListener(this)
        val btnObtenerImage =  findViewById<Button>(R.id.btnObtenerImg)
        btnObtenerImage.setOnClickListener(this)
        val btnMandarPrueba =  findViewById<Button>(R.id.botonfuncionapls)
        btnMandarPrueba.setOnClickListener(this)
        txtMessage =  findViewById(R.id.txtResponse)
        txtId =  findViewById(R.id.txtId)
        imageUI =  findViewById(R.id.imgUI)

    }

    companion object {
        //Estos número tu los eliges como mejor funcione para ti, no necesariamente tienen que ser 1000, puede
        // ser 1,2,3
        //Lo importante es ser congruente en su uso
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
        //camera code
        private val CAMERA_CODE = 1002;
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnConsultarPaises-> getCountries()
            R.id.btnConsultarPais-> getCountry()
            R.id.btnCamera-> openCamera()
            R.id.btnEnviarImagen -> sendImage()
            R.id.btnObtenerImg->getImage()
            R.id.botonfuncionapls->mandaprueba()
        }
    }
    private fun mandaprueba(){
        txtMessage!!.setText("")

        val file =  Country("0","Marruecos","1")
        val service: Service =  RestEngine.getRestEngine().create(Service::class.java)

        val result: Call<Int> = service.addpais(file)

        result.enqueue(object: Callback<Int>{
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Toast.makeText(this@MainActivity,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                Toast.makeText(this@MainActivity,"OK",Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getCountries(){
        txtMessage!!.setText("")
        val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
        val result: Call<List<Usuarios>> = service.listUsers()

        result.enqueue(object: Callback<List<Usuarios>>{


            override fun onFailure(call: Call<List<Usuarios>>, t: Throwable) {
                Toast.makeText(this@MainActivity,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<Usuarios>>, response: Response<List<Usuarios>>) {
                val arrayItems =  response.body()
                var strMessage:String =  ""
                if (arrayItems != null){
                    for (item in arrayItems!!){
                        strMessage =  strMessage + item.ID.toString() +  "-" + item.nombre + "\n"
                    }
                }

                txtMessage!!.setText(strMessage)
                Toast.makeText(this@MainActivity,"OK",Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getCountry(){
        txtMessage!!.setText("")
        val intId:Int =  txtId!!.text.toString().toInt()
        val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
        val result: Call<List<Usuarios>> = service.listUsers()

        result.enqueue(object: Callback<List<Usuarios>>{
            override fun onFailure(call: Call<List<Usuarios>>, t: Throwable) {
                Toast.makeText(this@MainActivity,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<Usuarios>>, response: Response<List<Usuarios>>) {
                var strMessage:String =  ""
                val item =  response.body()
                if (item != null){
                    strMessage =   item[0].ID.toString() +  "-" + item[0].nombre + "-"+ item[0].apellidos+"\n"
                }
                txtMessage!!.setText(strMessage)
                Toast.makeText(this@MainActivity,"OK",Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun openCamera(){
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_CODE)
    }

    override fun onActivityResult(requestcode: Int, resultcode: Int, data: Intent?) {
        super.onActivityResult(requestcode, resultcode, intent)

        if (resultcode == Activity.RESULT_OK) {
            //RESPUESTA DE LA CÁMARA CON TIENE LA IMAGEN
            if (requestcode == CAMERA_CODE) {

                val photo =  data?.extras?.get("data") as Bitmap
                val stream = ByteArrayOutputStream()
                //Bitmap.CompressFormat agregar el formato desado, estoy usando aqui jpeg
                photo.compress(Bitmap.CompressFormat.JPEG, 80, stream)
                //Agregamos al objecto album el arreglo de bytes
                imgArray =  stream.toByteArray()
                //Mostramos la imagen en la vista
                this.imageUI!!.setImageBitmap(photo)

            }

        }
    }

    private fun sendImage(){

        val encodedString:String = "" + Base64.getEncoder().encodeToString(this.imgArray)

        val file =  File(encodedString,0,"test")

        val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
        val result: Call<Int> = service.addFile(file)

        result.enqueue(object: Callback<Int>{
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Toast.makeText(this@MainActivity,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                Toast.makeText(this@MainActivity,"OK",Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getImage(){
        val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
        val result: Call<List<File>> = service.getFile(5)

        result.enqueue(object:Callback<List<File>>{
            override fun onFailure(call: Call<List<File>>, t: Throwable) {
                Toast.makeText(this@MainActivity,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<File>>, response: Response<List<File>>) {
                val tempObjec =  response.body()
                val strBase64:String =  tempObjec!![0].contenido.replace(,"")
                val imgByteArray =  Base64.getDecoder().decode(strBase64)

                imageUI!!.setImageBitmap(ImageUtilities.getBitMapFromByteArray(imgByteArray))

                Toast.makeText(this@MainActivity,"OK",Toast.LENGTH_LONG).show()
            }
        })
    }*/
}