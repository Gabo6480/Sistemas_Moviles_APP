package com.example.chordgalore.data.service

import com.example.chordgalore.data.model.*
import retrofit2.Call
import retrofit2.http.*

interface Service {
    //////////////////Usuario
    @GET("SP_Publicacion/Fg1e2GetAllUsers")
    fun listUsers(): Call<List<Usuarios>> //Nos va a regresar la lista de publi
    ////////POST
    @Headers("Content-Type: application/json")
    @POST("SP_Usuario/Fg1e2GetLogInUser")
    fun logueate(@Body fileData: Usuarios): Call<List<Usuarios>>
    @Headers("Content-Type: application/json")
    @POST("SP_Usuario/SP_g1e2GetOneUser")
    fun trae1User(@Body fileData: Usuarios): Call<List<Usuarios>>

    @Headers("Content-Type: application/json")
    @POST("SP_Usuario/Fg1e2Add_Usuario")
    fun registrate(@Body fileData: Usuarios): Call<Boolean>
    @Headers("Content-Type: application/json")
    @POST("SP_Usuario/Fg1e2DeleteUser")
    fun eliminate(@Body fileData: Usuarios): Call<Boolean>

    //////////////////Publicacion
    @GET("SP_Publicacion/Fg1e2GetAllPosts")
    fun listPublicaciones(): Call<List<Publicacion>> //Nos va a regresar la lista de publi
    @Headers("Content-Type: application/json")
    @POST("SP_Publicacion/Fg1e2GetPostbyCat")
    fun listPublicacionesCat(@Body fileData: Publicacion): Call<List<Publicacion>>

    @Headers("Content-Type: application/json")
    @POST("SP_Publicacion/Fg1e2ViewNoticia")
    fun traePublicacion(@Body fileData: Publicacion): Call<List<Publicacion>>

    @Headers("Content-Type: application/json")
    @POST("SP_Publicacion/Fg1e2GetDraftPosts")
    fun listBorradorUser(@Body fileData: Publicacion): Call<List<Publicacion>>

    @Headers("Content-Type: application/json")
    @POST("SP_Publicacion/Fg1e2AddPost")
    fun insertaPost(@Body fileData: Publicacion): Call<List<Publicacion>>

    @Headers("Content-Type: application/json")
    @POST("SP_Publicacion/Fg1e2UpdatePost")
    fun cambiaPost(@Body fileData: Publicacion): Call<Boolean>

    @Headers("Content-Type: application/json")
    @POST("SP_Publicacion/Fg1e2DeletePost")
    fun eliminaPost(@Body fileData: Publicacion): Call<Boolean>

    //////////////////Fotos
    @Headers("Content-Type: application/json")
    @POST("SP_Fotos/Fg1e2GetPhotos")
    fun listFoto(@Body fileData: Fotos): Call<List<Fotos>>
    @Headers("Content-Type: application/json")
    @POST("SP_Fotos/Fg1e2AddPhoto")
    fun insertaFoto(@Body fileData: Fotos): Call<Boolean>
    @Headers("Content-Type: application/json")
    @POST("SP_Fotos/Fg1e2DeleteLasFotos")
    fun eliminaFoto(@Body fileData: Fotos): Call<Boolean>
    //////////////////Categoria

    @GET("SP_Categoria/Fg1e2GetCategorias")
    fun listCategoria(): Call<List<Categoria>> //Nos va a regresar la lista de publi
}