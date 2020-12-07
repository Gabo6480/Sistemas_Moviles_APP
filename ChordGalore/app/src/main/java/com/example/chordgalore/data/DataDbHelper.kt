package com.example.chordgalore.data



import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.ColorSpace
import android.util.Log
import android.widget.Toast
import com.example.chordgalore.*
import com.example.chordgalore.data.model.*
import java.lang.Exception
import kotlinx.android.synthetic.main.activity_main.*

class DataDbHelper (var context: Context): SQLiteOpenHelper(context,bdMovilesMusica.DB_NAME,null,bdMovilesMusica.DB_VERSION){

    //-----------Usuarios---------------------------------------------------------------------------------------------------------//
    override fun onCreate(db: SQLiteDatabase?) {
        //CREAR BASE DE DATOS
        try {
            db?.execSQL("CREATE TABLE " + bdMovilesMusica.tbUsuario.TABLE_NAME + " (" +
                    bdMovilesMusica.tbUsuario.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    bdMovilesMusica.tbUsuario.COL_NOMBRE + " VARCHAR(50)," +
                    bdMovilesMusica.tbUsuario.COL_PASSWORD + " VARCHAR(30)," +
                    bdMovilesMusica.tbUsuario.COL_APELLIDOS + " VARCHAR(100), " +
                    bdMovilesMusica.tbUsuario.COL_EMAIL + " VARCHAR(50)," +
                    bdMovilesMusica.tbUsuario.COL_ACTIVO + " TINYINT DEFAULT 1," +
                    bdMovilesMusica.tbUsuario.COL_IMG + " LONGBLOB)")
            Log.d("Tabla", "Usuarios Done")

            db?.execSQL("CREATE TABLE " + bdMovilesMusica.tbCategorias.TABLE_NAME + " (" +
                    bdMovilesMusica.tbCategorias.COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    bdMovilesMusica.tbCategorias.COL_NOMBRE+" VARCHAR(50))")
            Log.d("Tabla", "Categorias Done");

            db?.execSQL("CREATE TABLE " + bdMovilesMusica.tbPublicaciones.TABLE_NAME + " (" +
                    bdMovilesMusica.tbPublicaciones.COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    bdMovilesMusica.tbPublicaciones.COL_TITULO+" VARCHAR(200),"+
                    bdMovilesMusica.tbPublicaciones.COL_ESTADO+" VARCHAR(50),"+
                    bdMovilesMusica.tbPublicaciones.COL_GENERO + " INT,"+
                    bdMovilesMusica.tbPublicaciones.COL_ACTIVO + " TINYINT DEFAULT 1 ,"+
                    bdMovilesMusica.tbPublicaciones.COL_IDUSUARIO + " INTEGER, " +
                    bdMovilesMusica.tbPublicaciones.COL_TEXTO + " TEXT, " +
                    " FOREIGN KEY ("+ bdMovilesMusica.tbPublicaciones.COL_GENERO +") REFERENCES " + bdMovilesMusica.tbCategorias.TABLE_NAME+"("+bdMovilesMusica.tbCategorias.COL_ID +"))")
            Log.d("Tabla", "Publicaciones Done");


            db?.execSQL("CREATE TABLE " + bdMovilesMusica.tbFavoritos.TABLE_NAME + " (" +
                    bdMovilesMusica.tbFavoritos.COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    bdMovilesMusica.tbFavoritos.COL_IDPUBLICACION+" INTEGER,"+
                    bdMovilesMusica.tbFavoritos.COL_IDUSUARIO + " INTEGER,"+
                    bdMovilesMusica.tbFavoritos.COL_ACTIVO + " TINYINT DEFAULT 1 ,"+
                    " FOREIGN KEY ("+ bdMovilesMusica.tbFavoritos.COL_IDUSUARIO +") REFERENCES " + bdMovilesMusica.tbUsuario.TABLE_NAME+" ( "+bdMovilesMusica.tbUsuario.COL_ID +" )," +
                    " FOREIGN KEY ("+ bdMovilesMusica.tbFavoritos.COL_IDPUBLICACION+") REFERENCES "+bdMovilesMusica.tbPublicaciones.TABLE_NAME+" ( "+bdMovilesMusica.tbPublicaciones.COL_ID +" ) )")
            Log.d("Tabla", "Favoritos Done")


            db?.execSQL("CREATE TABLE " + bdMovilesMusica.tbFotos.TABLE_NAME + " (" +
                    bdMovilesMusica.tbFotos.COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    bdMovilesMusica.tbFotos.COL_IDPUBLICACION+" INTEGER,"+
                    bdMovilesMusica.tbFotos.COL_IMAGEN + " LONGBLOB,"+
                    bdMovilesMusica.tbFotos.COL_ACTIVO + " TINYINT DEFAULT 1 ,"+
                    " FOREIGN KEY ("+ bdMovilesMusica.tbFotos.COL_IDPUBLICACION +") REFERENCES " + bdMovilesMusica.tbPublicaciones.TABLE_NAME+" ( "+bdMovilesMusica.tbPublicaciones.COL_ID +" ) )")
            Log.d("Tabla", "Fotos Done ");



        } catch (e: Exception) {
            Log.e("Execption", e.toString())
        }

    }


    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun insertUsuarios(Users:UsuariosLocal):Boolean{

        val dataBase:SQLiteDatabase = this.writableDatabase
        val values:ContentValues = ContentValues()
        var boolResult:Boolean =  true

        values.put(bdMovilesMusica.tbUsuario.COL_NOMBRE,Users.Nombre)
        values.put(bdMovilesMusica.tbUsuario.COL_APELLIDOS,Users.Apellidos)
        values.put(bdMovilesMusica.tbUsuario.COL_EMAIL,Users.Email)
        values.put(bdMovilesMusica.tbUsuario.COL_PASSWORD,Users.Password)
        values.put(bdMovilesMusica.tbUsuario.COL_IMG,Users.imgArray)



        try {
            val result =  dataBase.insert(bdMovilesMusica.tbUsuario.TABLE_NAME, null, values)

            if (result == (0).toLong()) {
                Toast.makeText(this.context, "Failed", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this.context, "Success", Toast.LENGTH_SHORT).show()
            }

        }catch (e: Exception){
            //Log.e("Execption", e.toString())
            boolResult =  false
        }

        dataBase.close()

        return boolResult
    } // insertar un usuario recibe de parametro la clase Usuario

    fun UpdatePerfil(IDuser: Int,Users:UsuariosLocal):Boolean{


        val dataBase:SQLiteDatabase = this.writableDatabase
        val values:ContentValues = ContentValues()
        var boolResult:Boolean =  true

        values.put(bdMovilesMusica.tbUsuario.COL_NOMBRE,Users.Nombre)
        values.put(bdMovilesMusica.tbUsuario.COL_APELLIDOS,Users.Apellidos)
        values.put(bdMovilesMusica.tbUsuario.COL_EMAIL,Users.Email)
        values.put(bdMovilesMusica.tbUsuario.COL_PASSWORD,Users.Password)
        values.put(bdMovilesMusica.tbUsuario.COL_IMG,Users.imgArray)

        val selection:String =  bdMovilesMusica.tbUsuario.COL_ID + " LIKE ?"
        val selectionArgs = arrayOf(IDuser.toString())
        try {
            val result =  dataBase.update(bdMovilesMusica.tbUsuario.TABLE_NAME,
                 values,
                selection,
                selectionArgs
                )
            Log.d("myTag", "existe");

        }catch (e: Exception){
            Log.d("myTag", "JAJA mame");
            //Log.e("Execption", e.toString())
            boolResult =  false
        }

        dataBase.close()

        return boolResult
    } // Actualizacion de Perfil Recibe de Parametros la ID del Usuario y la Clase

    fun getUsers(): ArrayList<UsuariosLocal>{
        val usuarios: UsuariosLocal = UsuariosLocal()
        val ListUsers = ArrayList<UsuariosLocal>()

        val dataBase:SQLiteDatabase = this.writableDatabase

        val columns =  arrayOf(bdMovilesMusica.tbUsuario.COL_ID,
            bdMovilesMusica.tbUsuario.COL_NOMBRE,
            bdMovilesMusica.tbUsuario.COL_APELLIDOS,
            bdMovilesMusica.tbUsuario.COL_EMAIL,
            bdMovilesMusica.tbUsuario.COL_PASSWORD,
            bdMovilesMusica.tbUsuario.COL_IMG
        )

        //val where:String =  bdMovilesMusica.tbUsuario.COL_EMAIL + " = '${UserEmail}'"+" AND "+bdMovilesMusica.tbUsuario.COL_PASSWORD+" = '${UserPass}'"

        val data =  dataBase.query(
            bdMovilesMusica.tbUsuario.TABLE_NAME,
            columns,
            null,
            null,
            null,
            null,
            null,
            null)

        if(data.moveToFirst()){
            do {
                var nombre = data.getString(data.getColumnIndex(bdMovilesMusica.tbUsuario.COL_NOMBRE)).toString()
                var Contra = data.getString(data.getColumnIndex(bdMovilesMusica.tbUsuario.COL_PASSWORD)).toString()
                var Apellidos = data.getString(data.getColumnIndex(bdMovilesMusica.tbUsuario.COL_APELLIDOS)).toString()
                var Email = data.getString(data.getColumnIndex(bdMovilesMusica.tbUsuario.COL_EMAIL)).toString()
                var imgArray = data.getBlob(data.getColumnIndex(bdMovilesMusica.tbUsuario.COL_IMG))
                var IDusuario = data.getInt(data.getColumnIndex(bdMovilesMusica.tbUsuario.COL_ID))

                ListUsers.add(UsuariosLocal(nombre,Apellidos,Email,Contra,IDusuario,imgArray,1))
            }while (data.moveToNext())


        }

        data.close()
       return ListUsers

    } // Devuelve todos los usuarios

    fun getUsuarioUnico(IDuser:Int): UsuariosLocal{
        val usuarios: UsuariosLocal = UsuariosLocal()

        val dataBase:SQLiteDatabase = this.writableDatabase

        val columns =  arrayOf(bdMovilesMusica.tbUsuario.COL_ID,
            bdMovilesMusica.tbUsuario.COL_NOMBRE,
            bdMovilesMusica.tbUsuario.COL_APELLIDOS,
            bdMovilesMusica.tbUsuario.COL_EMAIL,
            bdMovilesMusica.tbUsuario.COL_PASSWORD,
            bdMovilesMusica.tbUsuario.COL_IMG
        )

        val where:String =  bdMovilesMusica.tbUsuario.COL_ID + " = '${IDuser}'"

        val data =  dataBase.query(
            bdMovilesMusica.tbUsuario.TABLE_NAME,
            columns,
            where,
            null,
            null,
            null,
            null,
            null)

        if(data.moveToFirst()){

        }
        usuarios.Nombre = data.getString(data.getColumnIndex(bdMovilesMusica.tbUsuario.COL_NOMBRE)).toString()
        usuarios.Password = data.getString(data.getColumnIndex(bdMovilesMusica.tbUsuario.COL_PASSWORD)).toString()
        usuarios.Apellidos = data.getString(data.getColumnIndex(bdMovilesMusica.tbUsuario.COL_APELLIDOS)).toString()
        usuarios.Email = data.getString(data.getColumnIndex(bdMovilesMusica.tbUsuario.COL_EMAIL)).toString()
        usuarios.imgArray = data.getBlob(data.getColumnIndex(bdMovilesMusica.tbUsuario.COL_IMG))
        data.close()
        return usuarios

    } // Devuelve un usaurio unico mediante la ID y lo devuelve en formato de objeto clase Usuario

    fun Log_in(UserEmail:String,UserPass:String): Boolean{
        val usuarios: UsuariosLocal = UsuariosLocal()

        val dataBase:SQLiteDatabase = this.writableDatabase

        val columns =  arrayOf(bdMovilesMusica.tbUsuario.COL_ID,
            bdMovilesMusica.tbUsuario.COL_NOMBRE,
            bdMovilesMusica.tbUsuario.COL_APELLIDOS,
            bdMovilesMusica.tbUsuario.COL_EMAIL,
            bdMovilesMusica.tbUsuario.COL_PASSWORD,
            bdMovilesMusica.tbUsuario.COL_IMG
        )

        val where:String =  bdMovilesMusica.tbUsuario.COL_EMAIL + " = '${UserEmail}'"+" AND "+bdMovilesMusica.tbUsuario.COL_PASSWORD+" = '${UserPass}'"

        val data =  dataBase.query(
            bdMovilesMusica.tbUsuario.TABLE_NAME,
            columns,
            where,
            null,
            null,
            null,
            null,
            null)

        val contador:Int = data.count
        if(data.moveToFirst()){

        }

        if(contador>=1)
        {
            Log.d("myTag", "existe");
            data.close()
            return true
        }
        else
        {
            Log.d("myTag", "No existe");
            data.close()
            return false
        }
    } // Login regresa booleano

    fun insertCategoria(Cat:CategoriasLocal):Boolean{

        val dataBase:SQLiteDatabase = this.writableDatabase
        val values:ContentValues = ContentValues()
        var boolResult:Boolean =  true

        values.put(bdMovilesMusica.tbCategorias.COL_NOMBRE,Cat.Nombre)

        try {
            val result =  dataBase.insert(bdMovilesMusica.tbCategorias.TABLE_NAME, null, values)

            if (result == (0).toLong()) {
                Toast.makeText(this.context, "Failed", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this.context, "Success", Toast.LENGTH_SHORT).show()
            }
            Log.d("Insert", "pues se hizo ");
        }catch (e: Exception){
            //Log.e("Execption", e.toString())
            boolResult =  false
        }

        dataBase.close()

        return boolResult
    } // insertar un usuario recibe de parametro la clase Usuario

    fun getCatUnico(IDcat:Int): CategoriasLocal{
        val CatOut: CategoriasLocal = CategoriasLocal()

        val dataBase:SQLiteDatabase = this.writableDatabase

        val columns =  arrayOf(bdMovilesMusica.tbCategorias.COL_ID,
            bdMovilesMusica.tbCategorias.COL_NOMBRE
        )

        val where:String =  bdMovilesMusica.tbCategorias.COL_ID + " = '${IDcat}'"

        val data =  dataBase.query(
            bdMovilesMusica.tbCategorias.TABLE_NAME,
            columns,
            where,
            null,
            null,
            null,
            null,
            null)

        if(data.moveToFirst()){

        }

        CatOut.Nombre=data.getString(data.getColumnIndex(bdMovilesMusica.tbCategorias.COL_NOMBRE)).toString()
        CatOut.ID=data.getString(data.getColumnIndex(bdMovilesMusica.tbCategorias.COL_ID)).toInt()
        data.close()
        return CatOut

    } // no es necesario

    fun insertPublicacion(Public: LocalPost):Boolean{

        val dataBase:SQLiteDatabase = this.writableDatabase
        val values:ContentValues = ContentValues()
        var boolResult:Boolean =  true

        values.put(bdMovilesMusica.tbPublicaciones.COL_TITULO,Public.titulo)
        values.put(bdMovilesMusica.tbPublicaciones.COL_ESTADO,Public.estado)
        values.put(bdMovilesMusica.tbPublicaciones.COL_GENERO,Public.genero)
        values.put(bdMovilesMusica.tbPublicaciones.COL_IDUSUARIO,Public.IDusuario)
        values.put(bdMovilesMusica.tbPublicaciones.COL_TEXTO,Public.texto)


        try {
            val result =  dataBase.insert(bdMovilesMusica.tbPublicaciones.TABLE_NAME, null, values)

            if (result == (0).toLong()) {
                Toast.makeText(this.context, "Failed", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this.context, "Success", Toast.LENGTH_SHORT).show()
            }
            Log.d("Insert", "pues se hizo ");
        }catch (e: Exception){
            //Log.e("Execption", e.toString())
            boolResult =  false
        }

        dataBase.close()

        return boolResult
    } // insertar un usuario recibe de parametro la clase Usuario si no se publica agregar guarda como y el estado hacerlo borrador

    fun insertFav(FavP: FavoritosLocal):Boolean{
        val dataBase:SQLiteDatabase = this.writableDatabase
        val values:ContentValues = ContentValues()
        var boolResult:Boolean =  true

        values.put(bdMovilesMusica.tbFavoritos.COL_IDPUBLICACION,FavP.IDpublicacion)
        values.put(bdMovilesMusica.tbFavoritos.COL_IDUSUARIO,FavP.IDusuario)

        try {
            val result =  dataBase.insert(bdMovilesMusica.tbFavoritos.TABLE_NAME, null, values)

            if (result == (0).toLong()) {
                Toast.makeText(this.context, "Failed", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this.context, "Success", Toast.LENGTH_SHORT).show()
            }
            Log.d("Insert", "pues se hizo ");
        }catch (e: Exception){
            //Log.e("Execption", e.toString())
            boolResult =  false
        }

        dataBase.close()

        return boolResult
    }

    fun GetFav(UserID:Int):ArrayList<FavoritosLocal>{
        val FavPubli : FavoritosLocal = FavoritosLocal()
        val ListaFav = ArrayList<FavoritosLocal>()
        val dataBase:SQLiteDatabase = this.writableDatabase

        val columns =  arrayOf(bdMovilesMusica.tbFavoritos.COL_IDPUBLICACION,
           bdMovilesMusica.tbFavoritos.COL_IDUSUARIO
        )



        val data2 = dataBase.rawQuery("SELECT Post.Titulo, Fav.IDPublicacion, Fav.ID as IDFAV from Publicacion Post\n" +
                "inner join Favoritos Fav on Fav.IDPublicacion = Post.ID\n" +
                "where Post.Activo=1 AND Post.Estado = 'Redaccion' AND Fav.IDusuario = '${UserID}';",null,null)


        if(data2.moveToFirst()){
            do {
                var titulo = data2.getString(data2.getColumnIndex("Titulo")).toString()
                var IDFAv = data2.getInt(data2.getColumnIndex("IDFAV")).toInt()
                var IDpost = data2.getInt(data2.getColumnIndex("IDPublicacion")).toInt()


                ListaFav.add(FavoritosLocal(IDFAv,UserID,IDpost,1,"pepito",titulo))
            }while (data2.moveToNext())
        }


        data2.close()
        return ListaFav
    }

    fun GetPublicaciones():ArrayList<PublicacionRow>{
        val PublicacionOut: LocalPost = LocalPost()

        val ListaPublic = ArrayList<PublicacionRow>()



        val dataBase:SQLiteDatabase = this.writableDatabase

        val columns =  arrayOf(bdMovilesMusica.tbPublicaciones.COL_ID,
            bdMovilesMusica.tbPublicaciones.COL_TEXTO,
            bdMovilesMusica.tbPublicaciones.COL_ESTADO,
            bdMovilesMusica.tbPublicaciones.COL_GENERO,
            bdMovilesMusica.tbPublicaciones.COL_TITULO,
            bdMovilesMusica.tbPublicaciones.COL_IDUSUARIO
        )

        //val where:String =  bdMovilesMusica.tbUsuario.COL_ID + " = '${IDuser}'"

        var cursor: Cursor? = null



        cursor =  dataBase.rawQuery(" Select Post.Titulo , Us.byteImage, Us.Nombre as NombreUs , Post.ID, Cat.Nombre from Publicacion Post\n" +
                "inner join Usuarios Us on Post.IDusuario = Us.UserID\n" +
                "inner join Categorias Cat on Cat.ID = Post.Genero\n" +
                "where Post.Activo = 1 AND Post.Estado = 'Redaccion' ;)",null,null)




        if(cursor.moveToFirst()){
            var i:Int = 1
            while (cursor.isAfterLast == false) {
                //Pruebalista[i].titulo=data.getString(data.getColumnIndex(bdMovilesMusica.tbPublicaciones.COL_TITULO)).toString()
                var Titulo = cursor.getString(cursor.getColumnIndex("Titulo")).toString()
                var Imagen = cursor.getBlob(cursor.getColumnIndex("byteImage"))
                var CatNombre = cursor.getString(cursor.getColumnIndex("Nombre"))
                var NombreUS = cursor.getString(cursor.getColumnIndex("NombreUS"))
                var IDpost = cursor.getInt(cursor.getColumnIndex("ID"))
                ListaPublic.add(PublicacionRow(Titulo,Imagen,IDpost,CatNombre,NombreUS))
                cursor.moveToNext()
            }

        }
        Log.d("pruebafuera",  ListaPublic[0].Titulo)
        Log.d("pruebafuera",  ListaPublic[0].CatName)
        Log.d("pruebafuera",  ListaPublic[0].PostID.toString())



        //PublicacionOut.titulo=data.getString(data.getColumnIndex(bdMovilesMusica.tbPublicaciones.COL_TITULO)).toString()
        //Log.d("WHAT",  PublicacionOut.titulo);
        //PublicacionOut.texto=data.getString(data.getColumnIndex(bdMovilesMusica.tbPublicaciones.COL_TEXTO)).toString()
        //Log.d("WHAT",  PublicacionOut.texto);
        //PublicacionOut.genero=data.getString(data.getColumnIndex(bdMovilesMusica.tbPublicaciones.COL_GENERO)).toInt()
        //PublicacionOut.IDusuario=data.getString(data.getColumnIndex(bdMovilesMusica.tbPublicaciones.COL_IDUSUARIO)).toInt()
        //PublicacionOut.estado=data.getString(data.getColumnIndex(bdMovilesMusica.tbPublicaciones.COL_ESTADO)).toString()
        cursor.close()
        return ListaPublic
    }

    fun GetinfoPublicacion(STP_IDPost:Int):PublicacionView{
        var Postout: PublicacionView = PublicacionView()


        val dataBase:SQLiteDatabase = this.writableDatabase

        val columns =  arrayOf(bdMovilesMusica.tbPublicaciones.COL_ID,
                bdMovilesMusica.tbPublicaciones.COL_TEXTO,
                bdMovilesMusica.tbPublicaciones.COL_ESTADO,
                bdMovilesMusica.tbPublicaciones.COL_GENERO,
                bdMovilesMusica.tbPublicaciones.COL_TITULO,
                bdMovilesMusica.tbPublicaciones.COL_IDUSUARIO
        )

        val where:String =  bdMovilesMusica.tbPublicaciones.COL_ID + " = '${STP_IDPost}'"

        var cursor: Cursor? = null

        cursor =  dataBase.rawQuery("SELECT Pub.Titulo,Pub.ID,Pub.TextPublic,Us.Nombre, Cat.Nombre as CatName FROM Publicacion Pub\n" +
                "inner join Categorias Cat on Cat.ID = Pub.Genero\n" +
                "inner join Usuarios Us on Us.UserID = Pub.IDusuario\n" +
                "WHERE Pub.ID = '${STP_IDPost}';",null,null)
        if(cursor.moveToFirst())
        {
            Postout.Titulo = cursor.getString(cursor.getColumnIndex(bdMovilesMusica.tbPublicaciones.COL_TITULO))
            Postout.PostID = cursor.getInt(cursor.getColumnIndex(bdMovilesMusica.tbPublicaciones.COL_ID))
            Postout.TextPost =cursor.getString(cursor.getColumnIndex(bdMovilesMusica.tbPublicaciones.COL_TEXTO))
            Postout.UsuarioName =cursor.getString(cursor.getColumnIndex("Nombre"))
            Postout.CatName = cursor.getString(cursor.getColumnIndex("CatName"))
        }


        Log.d("pruebafuera",  Postout.Titulo)
        Log.d("pruebafuera",  Postout.PostID.toString())
        Log.d("pruebafuera",  Postout.TextPost)
        Log.d("pruebafuera",  Postout.UsuarioName)
        Log.d("pruebafuera",  Postout.CatName)

        return Postout
    }

    fun GetImgNoticias(STP_IDPost:Int): ArrayList<FotosLocal>{
        val ListaFotos = ArrayList<FotosLocal>()

        val dataBase:SQLiteDatabase = this.writableDatabase

        val columns =  arrayOf(bdMovilesMusica.tbFotos.COL_ID,
                bdMovilesMusica.tbFotos.COL_IMAGEN,
                bdMovilesMusica.tbFotos.COL_IDPUBLICACION
        )

        val where:String =  bdMovilesMusica.tbFotos.COL_IDPUBLICACION + " = '${STP_IDPost}'"

        val data =  dataBase.query(
                bdMovilesMusica.tbFotos.TABLE_NAME,
                columns,
                where,
                null,
                null,
                null,
                null,
                null)

        if(data.moveToFirst())
        {
           var IDimage = data.getInt(data.getColumnIndex(bdMovilesMusica.tbFotos.COL_ID))
            var image = data.getBlob(data.getColumnIndex(bdMovilesMusica.tbFotos.COL_IMAGEN))
            var IDpost = data.getInt(data.getColumnIndex(bdMovilesMusica.tbFotos.COL_IDPUBLICACION))
            ListaFotos.add(FotosLocal(IDimage,IDpost,image,1))
        }
        return ListaFotos
    }

    fun FavExists(STP_IDuser:Int , STP_IDPost: Int):Boolean{

        val dataBase:SQLiteDatabase = this.writableDatabase

        val columns =  arrayOf(bdMovilesMusica.tbFavoritos.COL_ID,
                bdMovilesMusica.tbFavoritos.COL_IDUSUARIO,
                bdMovilesMusica.tbFavoritos.COL_IDPUBLICACION
        )

        val where:String =  bdMovilesMusica.tbFavoritos.COL_IDPUBLICACION + " = '${STP_IDPost}' AND " + bdMovilesMusica.tbFavoritos.COL_IDUSUARIO + "='${STP_IDuser}';"

        val data =  dataBase.query(
                bdMovilesMusica.tbFavoritos.TABLE_NAME,
                columns,
                where,
                null,
                null,
                null,
                null,
                null)

        val contador = data.count

        if(contador >=1)
        {
            return true
        }
        if(contador<=0)
        {
            return false
        }
        return false
    }







    //-----------Usuarios---------------------------------------------------------------------------------------------------------//

    //-----------Publicaciones---------------------------------------------------------------------------------------------------------//




    //-----------Publicaciones---------------------------------------------------------------------------------------------------------//
    /*

       fun getAlbum(id_Album:Int):Album{

           val dataBase:SQLiteDatabase = this.writableDatabase

           val columns =  arrayOf(bdCustomListView.tbAlbum.COL_ID,
               bdCustomListView.tbAlbum.COL_TITLE,
               bdCustomListView.tbAlbum.COL_DESCRIPTION,
               bdCustomListView.tbAlbum.COL_IDPHOTO,
               bdCustomListView.tbAlbum.COL_IMG)

           val where:String =  bdCustomListView.tbAlbum.COL_ID + "= ${id_Album.toString()}"

           val data =  dataBase.query(bdCustomListView.tbAlbum.TABLE_NAME,columns,where,null,null,null,bdCustomListView.tbAlbum.COL_ID + " ASC")

           data.moveToFirst()

           val album:Album =  Album()
           album.id = data.getString(data.getColumnIndex(bdCustomListView.tbAlbum.COL_ID)).toInt()
           album.title = data.getString(data.getColumnIndex(bdCustomListView.tbAlbum.COL_TITLE)).toString()
           album.desc = data.getString(data.getColumnIndex(bdCustomListView.tbAlbum.COL_DESCRIPTION)).toString()
           album.photo = data.getString(data.getColumnIndex(bdCustomListView.tbAlbum.COL_IDPHOTO)).toInt()
           album.imgArray =  data.getBlob(data.getColumnIndex(bdCustomListView.tbAlbum.COL_IMG))

           data.close()
           return album
       }

       fun updateAlbum(album:Album):Boolean{

           val dataBase:SQLiteDatabase = this.writableDatabase
           val values:ContentValues = ContentValues()
           var boolResult:Boolean =  true

           values.put(bdCustomListView.tbAlbum.COL_TITLE,album.title)
           values.put(bdCustomListView.tbAlbum.COL_DESCRIPTION,album.desc)
           values.put(bdCustomListView.tbAlbum.COL_IDPHOTO, album.photo)
           values.put(bdCustomListView.tbAlbum.COL_IMG,album.imgArray)

           val where:String =  bdCustomListView.tbAlbum.COL_ID + "=?"
           try {
               val result =  dataBase.update(bdCustomListView.tbAlbum.TABLE_NAME,values,where, arrayOf(album.id.toString()))

               if (result != -1 ) {
                   Toast.makeText(this.context, "Success", Toast.LENGTH_SHORT).show()
               }
               else {
                   Toast.makeText(this.context, "Failed", Toast.LENGTH_SHORT).show()

               }

           }catch (e: Exception){
               boolResult = false
               //Log.e("Execption", e.toString())
           }

           dataBase.close()

           return  boolResult
       }

       fun deleteAlbum(_id: Int): Boolean {
           val db = this.writableDatabase
           val where:String =  bdCustomListView.tbAlbum.COL_ID + "=?"
           val _success = db.delete(bdCustomListView.tbAlbum.TABLE_NAME, where, arrayOf(_id.toString()))
           db.close()
           return Integer.parseInt("$_success") != -1
       }
       */


}