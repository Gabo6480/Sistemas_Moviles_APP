package com.example.chordgalore.data.service

import com.example.chordgalore.data.model.Publicacion
import com.example.chordgalore.data.model.Usuarios
import com.example.chordgalore.data.model.Fotos
import com.example.chordgalore.data.model.Favoritos
import retrofit2.Call
import retrofit2.http.*

interface Service {
    //////////////////Usuario
    ////////POST
    @Headers("Content-Type: application/json")
    @POST("SP_Usuario/Fg1e2GetLogInUser")
    fun logueate(@Body fileData: Usuarios): Call<List<Usuarios>>
    @Headers("Content-Type: application/json")
    @POST("SP_Usuario/Fg1e2GetLogInUser")
    fun registrate(@Body fileData: Usuarios): Call<Int>
    //////////////////Publicacion
    @GET("SP_Publicacion/Fg1e2GetAllPost")
    fun listPublicaciones(): Call<List<Publicacion>> //Nos va a regresar la lista de publi
}