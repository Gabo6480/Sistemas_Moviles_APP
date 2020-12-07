package com.example.chordgalore.data.service

import com.example.chordgalore.data.model.Publicacion
import com.example.chordgalore.data.model.Usuarios
import com.example.chordgalore.data.model.Fotos
import com.example.chordgalore.data.model.Favoritos
import retrofit2.Call
import retrofit2.http.*

interface Service {
    ////////Gets GEO
    /*@GET("Geo/Paises") //Anotaciones propias de Retrofit
    fun listCountries(): Call<List<Country>> //Nos va a regresar la lista de paises

    @GET("Geo/Paises/{id}")
    fun getCountry(@Path("id") id: Int): Call<List<Country>> //Vamos a obtener un pais
    //Esta es otra forma cuando  la url esta por parametros
    //fun getCountry(@Query("id") id: Int): Call<List<Country>> //Vamos a obtener un pais
    ////////POST GEO
    @Headers("Content-Type: application/json")
    @POST("Geo/Paises")
    fun addpais(@Body fileData: Country): Call<Int>


    @GET("Usuario/traeUsuarios") //Anotaciones propias de Retrofit
    fun listUsers(): Call<List<Usuarios>> //Nos va a regresar la lista de paises

    @GET("Usuario/traeUsuarios{id}")
    fun getUser(@Path("id") id: Int): Call<List<Usuarios>> //Vamos a obtener un pais
    //Esta es otra forma cuando  la url esta por parametros
    //fun getUser(@Query("id") id: Int): Call<List<Country>> //Vamos a obtener un pais

    //MEDIA
    @Headers("Content-Type: application/json")
    @POST("Media/archivos")
    fun addFile(@Body fileData: File): Call<Int>

    @GET("Media/archivos/{id}")
    fun getFile(@Path("id") id:Int): Call<List<File>>*/
    ////////POST GEO
    @Headers("Content-Type: application/json")
    @POST("SP_Usuario/Fg1e2GetLogInUser")
    fun logueate(@Body fileData: Usuarios): Call<List<Usuarios>>
}