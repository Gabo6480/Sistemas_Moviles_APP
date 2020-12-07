package com.example.chordgalore.data.service

import android.widget.Toast
import com.example.chordgalore.data.model.Usuarios
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import android.graphics.Bitmap
import com.example.chordgalore.data.DataDbHelper
import com.example.chordgalore.data.model.Publicacion
import java.util.*

class APIService {
    companion object{

        //Funciones Usuarios
        fun logInUsuario(correo:String, passw: String,onResultCallback:(user:Usuarios?,t: Throwable?)->Unit){
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

        fun registroUsuario(nombre:String,apellidos:String,correo:String, passw: String,imagen: String,onResultCallback:(status:Boolean?,t: Throwable?)->Unit){
            val file =  Usuarios(nombre,apellidos,correo,passw, "data:image/;base64,$imagen")
            val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
            val result: Call<Boolean> = service.registrate(file)

            result.enqueue(object: Callback<Boolean>{
                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    onResultCallback(null, t)
                }
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {

                    onResultCallback( response.body(), null);
                }
            })
        }
        //Funciones Publicaciones
        fun traerPublicaciones(onResultCallback:(publis: List<Publicacion>?, t: Throwable?)->Unit){
            val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
            val result: Call<List<Publicacion>> = service.listPublicaciones()

            result.enqueue(object: Callback<List<Publicacion>>{
                override fun onFailure(call: Call<List<Publicacion>>, t: Throwable) {
                    onResultCallback(null, t)
                }
                override fun onResponse(call: Call<List<Publicacion>>, response: Response<List<Publicacion>>) {
                    onResultCallback(  response.body() , null);
                }
            })
        }
    }

}