package com.example.chordgalore.data



class bdMovilesMusica {
    companion object {
        val DB_NAME = "bdMovilesMusica"
        val DB_VERSION = 1
    }

    abstract class tbUsuario{
        companion object{
            val TABLE_NAME ="Usuarios"
            val COL_ID = "UserID"
            val COL_NOMBRE= "Nombre"
            val COL_APELLIDOS = "Apellidos"
            val COL_EMAIL = "Email"
            val COL_PASSWORD = "Password"
            val COL_IMG = "byteImage"
            val COL_ACTIVO = "Activo"
        }
    }

    abstract class tbPublicaciones{
        companion object{
            val TABLE_NAME ="Publicacion"
            val COL_ID ="ID"
            val COL_TITULO ="Titulo"
            val COL_GENERO = "Genero"
            val COL_ESTADO ="Estado"
            val COL_ACTIVO = "Activo"
            val COL_IDUSUARIO ="IDusuario"
            val COL_TEXTO = "TextPublic"

        }
    }

    abstract class tbFotos(){
        companion object{
            val TABLE_NAME = "Fotos"
            val COL_ID = "ID"
            val COL_IDPUBLICACION ="IDpublicacion"
            val COL_IMAGEN ="Imagen"
            val COL_ACTIVO = "Activo"
        }
    }

    abstract class tbFavoritos(){
        companion object{
            val TABLE_NAME ="Favoritos"
            val COL_ID ="ID"
            val COL_IDUSUARIO ="IDusuario"
            val COL_IDPUBLICACION ="IDpublicacion"
            val COL_ACTIVO ="Activo"
        }
    }

    abstract class tbCategorias(){
        companion object{
            val TABLE_NAME ="Categorias"
            val COL_NOMBRE ="Nombre"
            val COL_ID ="ID"
        }

    }
}

