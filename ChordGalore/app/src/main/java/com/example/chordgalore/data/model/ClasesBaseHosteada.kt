package com.example.chordgalore.data.model

//esto objectos fueron generados con el plugin Json to Kotlin Class
//Estos objecto se usa para pasar la respuesta recibida
data class Publicacion(
    val titulo: String,
    val imagen: String,
    val id: Int,
    val genero: Int,
    val generoN: String,
    val texto: String,
    val estado: String,
    val idUser: Int,
    val nombre: String,
    val activo: Int
)
{
    //constructor para traer los borradores de un User
    constructor(id: Int):this("","",id,-1,"","","",-1,"",-1)
    //Constructor para agregar posts
    constructor(titulo: String,genero: Int,texto: String,estado: String,idUser: Int):this(titulo,"",-1,genero,"",texto,estado,idUser,"",-1)
    constructor(idUser: Int,imagen: String,activo: Int):this("",imagen,-1,-1,"","","",idUser,"",activo)
}
data class Usuarios(
    val id: Int,
    val nombre: String,
    val contra: String,
    val apellidos: String,
    val email: String,
    val imagen: String,
    val activo: Int
)
{
    //Constructor de logInUser
    constructor(email: String,contra: String):this(-1,"",contra,"",email,"",-1)
    //Constructor de registroUser
    constructor(nombre: String,apellidos: String,email: String,contra: String,imagen: String):this(-1,nombre,contra,apellidos,email,imagen,-1)
    //Constructor
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

