package com.example.chordgalore.data.model

//esto objectos fueron generados con el plugin Json to Kotly Class
//Estos objecto se usa para pasar la respuesta recibida
data class Publicacion(
    val id: String,
    val titulo: String,
    val genero: Int,
    val texto: String,
    val estado: String,
    val activo: Int
)

data class Usuarios(
    val ID: Int,
    val nombre: String,
    val contra: String,
    val apellidos: String,
    val email: String,
    val imagen: String,
    val activo: Boolean
)
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

