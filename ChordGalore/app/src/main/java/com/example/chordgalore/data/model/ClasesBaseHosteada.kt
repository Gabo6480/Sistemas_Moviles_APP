package com.example.chordgalore.data.model

//esto objectos fueron generados con el plugin Json to Kotlin Class
//Estos objecto se usa para pasar la respuesta recibida
data class Publicacion(
    val titulo: String,
    val imagen: String,
    val id: Int,
    val genero: Int,
    val texto: String,
    val estado: String,
    val idUser: Int,
    val activo: Int
)
{
    constructor(estado: String,idUser: Int,activo: Int):this("","",-1,-1,"",estado,idUser,activo)
    constructor(idUser: Int,imagen: String,activo: Int):this("",imagen,-1,-1,"","",idUser,activo)
}
data class Usuarios(
    val ID: Int,
    val nombre: String,
    val contra: String,
    val apellidos: String,
    val email: String,
    val imagen: String,
    val activo: Int
)
{
    constructor(email: String,contra: String):this(-1,"",contra,"",email,"",-1)
    constructor(ID: Int,nombre: String,imagen: String):this(ID,nombre,"","","",imagen,-1)
}
data class Fotos(
    val id: Int,
    val idPubli: Int,
    val contenido: String,
    val activo: Boolean
)
data class Favoritos(
    val id: Int,
    val idUser: Int,
    val idPubli: Int,
    val activo: Int
)

