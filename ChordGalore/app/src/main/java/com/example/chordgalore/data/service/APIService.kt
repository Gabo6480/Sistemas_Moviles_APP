package com.example.chordgalore.data.service

import android.widget.Toast
import com.example.chordgalore.data.model.Usuarios
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import android.graphics.Bitmap
import java.util.*

class APIService {
    companion object{
        fun mandaprueba(correo:String, passw: String,onResultCallback:(user:Usuarios?,t: Throwable?)->Unit){
            val file =  Usuarios(correo,passw)
            val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
            val result: Call<List<Usuarios>> = service.logueate(file)

            result.enqueue(object: Callback<List<Usuarios>>{


                override fun onFailure(call: Call<List<Usuarios>>, t: Throwable) {
                    onResultCallback(null, t)
                }

                override fun onResponse(call: Call<List<Usuarios>>, response: Response<List<Usuarios>>) {
                    val arrayItems =  response.body()
                    val user =
                        arrayItems?.get(0)?.let { Usuarios(it.id,it.nombre + " " + it.apellidos,it.imagen) };
                    onResultCallback(user, null);
                }
            })
        }

    }

}