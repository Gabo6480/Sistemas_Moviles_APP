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
    val activo: Int,
    val favorito: Int   //1 Si, 0 No
)
{
    //constructor para traer los borradores de un User y eliminar un post
    constructor(id: Int):this("","",id,-1,"","","",-1,"",-1,-1)
    //constructor para traer 1 post
    constructor(id: Int,idUser: Int):this("","",id,-1,"","","",idUser,"",-1,-1)
    constructor(id: Int,nombre: String,imagen: String,titulo: String,idUser: Int,activo: Int,favorito: Int, estado: String,genero: Int,generoN: String,texto: String):this
        (titulo,imagen,id,genero,generoN,texto,estado,idUser,nombre,activo,favorito)
    //Constructor para agregar posts
    constructor(titulo: String,genero: Int,texto: String,estado: String,idUser: Int):this(titulo,"",-1,genero,"",texto,estado,idUser,"",-1,-1)
    //Constructor para editar post
    constructor(id: Int,titulo: String,genero: Int,texto: String,estado: String):this(titulo,"",id,genero,"",texto,estado,-1,"",-1,-1)
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
    //Constructor de UpdateUser
    constructor(id: Int,nombre: String,apellidos: String,email: String,contra: String,imagen: String):this(id,nombre,contra,apellidos,email,imagen,-1)
    //Constructor de logInUser2
    constructor(id: Int,nombre: String,imagen: String):this(id,nombre,"","","",imagen,-1)
    //Constructor de Delete y obtener 1User
    constructor(id: Int):this(id,"","","","","",-1)
}
data class Fotos(
    val id: Int,
    val idPublicacion: Int,
    val imagen: String,
    val activo: Boolean
)
{
    //Constructor para agregar fotos
    constructor(idPubli: Int,contenido: String):this(-1,idPubli,contenido,false)
    //Constructor para traer fotos
    constructor(idPubli: Int):this(-1,idPubli,"",false)
}
data class Favoritos(
    val id: Int,
    val idUser: Int,
    val idPubli: Int,
    val accion: Int
)
{
    //constructor para traer los favoritos de un usuario
    constructor(idPubli: Int):this(-1,-1,idPubli,0)
    //constructor para insertar un favorito
    constructor(idUser: Int,idPubli: Int,accion: Int):this(-1,idUser,idPubli,accion)
}
data class Categoria(
    val id: Int,
    val nombre: String
){
    override fun toString(): String {
        return nombre
    }
}

