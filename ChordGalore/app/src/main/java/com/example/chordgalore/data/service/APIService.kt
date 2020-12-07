package com.example.chordgalore.data.service

import android.widget.Toast
import com.example.chordgalore.data.model.Usuarios
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import android.graphics.Bitmap
import com.example.chordgalore.data.DataDbHelper
import com.example.chordgalore.data.model.Categoria
import com.example.chordgalore.data.model.Fotos
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

        fun updateUsuario(elId:Int,nombre:String,apellidos:String,correo:String, passw: String,imagen: String,onResultCallback:(status:Boolean?,t: Throwable?)->Unit){
            val file =  Usuarios(elId,nombre,apellidos,correo,passw, "data:image/;base64,$imagen")
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

        fun deleteUsuario(elId:Int,onResultCallback:(respuesta:Boolean?,t: Throwable?)->Unit){
            val file =  Usuarios(elId)
            val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
            val result: Call<Boolean> = service.eliminate(file)

            result.enqueue(object: Callback<Boolean>{
                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    onResultCallback(null, t)
                }
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    onResultCallback(response.body(), null);
                }
            })
        }

        fun traer1Usuario(elId:Int,onResultCallback:(user:Usuarios?,t: Throwable?)->Unit){
            val file =  Usuarios(elId)
            val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
            val result: Call<List<Usuarios>> = service.trae1User(file)

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

        fun traerUsuarios(onResultCallback:(usuarios: List<Usuarios>?,t: Throwable?)->Unit){
            val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
            val result: Call<List<Usuarios>> = service.listUsers()

            result.enqueue(object: Callback<List<Usuarios>>{
                override fun onFailure(call: Call<List<Usuarios>>, t: Throwable) {
                    onResultCallback(null, t)
                }
                override fun onResponse(call: Call<List<Usuarios>>, response: Response<List<Usuarios>>) {
                    onResultCallback(response.body(), null);
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

        fun traerPublicacionesCat(elId:Int,onResultCallback:(publis: List<Publicacion>?, t: Throwable?)->Unit){
            val file =  Publicacion(elId)
            val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
            val result: Call<List<Publicacion>> = service.listPublicacionesCat(file)

            result.enqueue(object: Callback<List<Publicacion>>{
                override fun onFailure(call: Call<List<Publicacion>>, t: Throwable) {
                    onResultCallback(null, t)
                }
                override fun onResponse(call: Call<List<Publicacion>>, response: Response<List<Publicacion>>) {
                    onResultCallback(  response.body() , null);
                }
            })
        }

        fun traerBorradorUser(elId:Int,onResultCallback:(borris:List<Publicacion>?,t: Throwable?)->Unit){
            val file =  Publicacion(elId)
            val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
            val result: Call<List<Publicacion>> = service.listBorradorUser(file)

            result.enqueue(object: Callback<List<Publicacion>>{
                override fun onFailure(call: Call<List<Publicacion>>, t: Throwable) {
                    onResultCallback(null, t)
                }
                override fun onResponse(call: Call<List<Publicacion>>, response: Response<List<Publicacion>>) {
                    onResultCallback(response.body(), null);
                }
            })
        }

        fun agregaPost(titulo:String,genero:Int,contenido:String, estado: String,userID: Int,onResultCallback:(SuID: Publicacion?,t: Throwable?)->Unit){
            val file =  Publicacion(titulo,genero,contenido,estado,userID)
            val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
            val result:Call<List<Publicacion>> = service.insertaPost(file)

            result.enqueue(object: Callback<List<Publicacion>>{
                override fun onFailure(call: Call<List<Publicacion>>, t: Throwable) {
                    onResultCallback(null, t)
                }
                override fun onResponse(call: Call<List<Publicacion>>, response: Response<List<Publicacion>>) {
                    val arrayItems =  response.body()
                    val SuID =
                        arrayItems?.get(0)?.let { Publicacion(it.id) };
                    onResultCallback(SuID, null);

                }
            })
        }

        fun editaPost(postID: Int,titulo:String,genero:Int,contenido:String, estado: String,onResultCallback:(respuesta:Boolean?,t: Throwable?)->Unit){
            val file =  Publicacion(postID,titulo,genero,contenido,estado)
            val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
            val result:Call<Boolean> = service.cambiaPost(file)

            result.enqueue(object: Callback<Boolean>{
                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    onResultCallback(null, t)
                }
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    onResultCallback(response.body(), null);

                }
            })
        }

        fun deletePost(elId:Int,onResultCallback:(respuesta:Boolean?,t: Throwable?)->Unit){
            val file =  Publicacion(elId)
            val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
            val result: Call<Boolean> = service.eliminaPost(file)

            result.enqueue(object: Callback<Boolean>{
                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    onResultCallback(null, t)
                }
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    onResultCallback(response.body(), null);
                }
            })
        }

        //Funciones Fotos
        fun traerFotos(idPubli: Int,onResultCallback:(publis: List<Fotos>?, t: Throwable?)->Unit){
            val file =  Fotos(idPubli)
            val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
            val result: Call<List<Fotos>> = service.listFoto(file)
            result.enqueue(object: Callback<List<Fotos>>{
                override fun onFailure(call: Call<List<Fotos>>, t: Throwable) {
                    onResultCallback(null, t)
                }
                override fun onResponse(call: Call<List<Fotos>>, response: Response<List<Fotos>>) {
                    onResultCallback(  response.body() , null);
                }
            })
        }

        fun agregaFoto(imagen:String,idPubli:Int,onResultCallback:(status:Boolean?,t: Throwable?)->Unit){
            val file =  Fotos(idPubli, "data:image/;base64,$imagen")
            val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
            val result:Call<Boolean> = service.insertaFoto(file)

            result.enqueue(object: Callback<Boolean>{
                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    onResultCallback(null, t)
                }
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    onResultCallback(response.body(), null);
                }
            })
        }

        fun deleteFoto(elId:Int,onResultCallback:(respuesta:Boolean?,t: Throwable?)->Unit){
            val file =  Publicacion(elId)
            val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
            val result: Call<Boolean> = service.eliminaPost(file)

            result.enqueue(object: Callback<Boolean>{
                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    onResultCallback(null, t)
                }
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    onResultCallback(response.body(), null);
                }
            })
        }

        //Funciones Categorias

        fun traerCategoria(onResultCallback:(cates: List<Categoria>?,t: Throwable?)->Unit){
            val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
            val result:Call<List<Categoria>> = service.listCategoria()

            result.enqueue(object: Callback<List<Categoria>>{
                override fun onFailure(call: Call<List<Categoria>>, t: Throwable) {
                    onResultCallback(null, t)
                }
                override fun onResponse(call: Call<List<Categoria>>, response: Response<List<Categoria>>) {
                    onResultCallback(response.body(), null);
                }
            })
        }
    }

}