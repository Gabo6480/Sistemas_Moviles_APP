package com.example.chordgalore.data.model

class UsuariosLocal(){

    var Nombre:String =""
    var Apellidos: String =""
    var Email: String =""
    var Password: String = ""
    var UserID: Int = 0
    var imgArray: ByteArray? = null
    var Activo: Int = 0

    constructor(Nombre:String,Apellidos:String,Email:String,Password:String,UserID:Int,imgArray:ByteArray,Activo:Int):this(){
        this.Nombre=Nombre
        this.Apellidos =Apellidos
        this.Email = Email
        this.Password = Password
        this.UserID =UserID
        this.imgArray =imgArray
        this.Activo = Activo

    }

}

class CategoriasLocal(){
    var Nombre:String=""
    var ID : Int = 0


    constructor(Nombre:String,ID:Int):this(){
        this.ID=ID
        this.Nombre = Nombre
    }
}

class FavoritosLocal(){
    var ID : Int =0
    var IDusuario : Int =0
    var IDpublicacion : Int =0
    var activo: Int = 0
    var NombreUser: String =""
    var NombrePubl:String =""
    constructor(ID:Int,IDusuario:Int,IDpublicacion:Int,activo:Int,NombreUser: String,NombrePubl:String) :this(){
        this.ID=ID
        this.IDpublicacion=IDpublicacion
        this.IDusuario=IDusuario
        this.activo=activo
        this.NombrePubl=NombrePubl
        this.NombreUser=NombreUser
    }
}

class FotosLocal(){
    var ID :Int=0
    var IDpublicacion: Int =0
    var imagen : ByteArray ? = null
    var activo : Int =0
    constructor(ID:Int,IDpublicacion:Int,imagenLByteArray: ByteArray,activo: Int):this(){
        this.ID=ID
        this.IDpublicacion=IDpublicacion
        this.imagen=imagen
        this.activo=activo

    }
}

class LocalPost(){
    var ID:Int=0
    var titulo:String =""
    var genero :Int =0
    var texto :String = ""
    var estado :String = ""
    var activo :Int = 0
    var IDusuario :Int = 0


    constructor(ID: Int,titulo:String,genero:Int,estado:String,activo:Int,IDusuario:Int):this(){
        this.ID=ID
        this.genero=genero
        this.IDusuario=IDusuario
        this.activo=activo
        this.estado=estado
        this.titulo=titulo
        this.texto=texto
    }

}

class PublicacionRow(){

    var Titulo:String=""
    var Imagen:ByteArray ? = null
    var PostID:Int = 0
    var CatName:String =""
    var Nameuser:String =""

    constructor(Titulo:String,imagen:ByteArray,PostID:Int,CatName:String,Nameuser:String):this(){
        this.Titulo =Titulo
        this.Imagen = imagen
        this. PostID = PostID
        this. CatName = CatName
        this. Nameuser = Nameuser
    }
}

class PublicacionView(){
    var Titulo:String=""
    var TextPost:String=""
    var UsuarioName:String=""
    var PostID:Int = 0
    var CatName:String =""


    constructor(Titulo:String,PostID:Int,CatName:String,UsuarioName:String,TextPost:String):this(){
        this.Titulo =Titulo
        this.TextPost = TextPost
        this.UsuarioName = UsuarioName
        this. PostID = PostID
        this. CatName = CatName

    }

}