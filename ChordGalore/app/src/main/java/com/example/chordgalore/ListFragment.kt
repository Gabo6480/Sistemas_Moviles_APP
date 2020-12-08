package com.example.chordgalore

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chordgalore.data.DataDbHelper
import com.example.chordgalore.data.LoginRepository
import com.example.chordgalore.data.SaveSharedPreference
import com.example.chordgalore.data.model.TileEntity
import com.example.chordgalore.data.service.APIService
import com.example.chordgalore.ui.content_view.ContentRecyclerAdapter
import com.example.chordgalore.data.model.ConectionUtility
import com.example.chordgalore.data.model.ImageUtilities
import kotlinx.android.synthetic.main.fragment_list.*



//Query va a ser utilizado para seleccionar que lista entre Home, Favorite y Downloads le vamos a mostrar
//Se vale cambiar el tipo de dato si se considera más adecuado
class ListFragment(val query : Int) : Fragment() {
    var DBsqlite= LoginRepository.instance()?.context?.let { DataDbHelper(it) }
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ContentRecyclerAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private var listItems: ArrayList<TileEntity> = ArrayList()
    private var listItemsFull: ArrayList<TileEntity> = ArrayList()

    //Solo para prueba de cargar más datos
    private var changeCounter = 0

    /* Esta variable es usada para saber desde qué punto actualizar la lista para
     * ahorrarnos el tener que redibujar toda la lista además de añadirle una animación
     * predefinida, así que cada vez que se cambie el tamaño de la lista se debe de actualizar
     */
    private var lastItemIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState == null)
            getArrayItems()

        buildRecyclerView()

        adapter.updateList()
    }

    private fun buildRecyclerView(){
        recyclerView = recycler_View
        recyclerView.setHasFixedSize(true)

        layoutManager = LinearLayoutManager(context)

        adapter = ContentRecyclerAdapter(listItems, listItemsFull)

        recyclerView.layoutManager = this.layoutManager
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : ContentRecyclerAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val item = listItems[position]
                val intent = Intent(context, if(query != 2) SongActivity::class.java else NewSongActivity::class.java)
                intent.putExtra("SongID", item.id)
                startActivity(intent)
            }
        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = LinearLayoutManager::class.java.cast(recyclerView.layoutManager)
                if (layoutManager != null) {
                    val totalItemCount = layoutManager.itemCount
                    val lastVisible = layoutManager.findLastVisibleItemPosition()

                    //val endHasBeenReached = lastVisible + 5 >= totalItemCount
                    //if (totalItemCount > 0 && endHasBeenReached) {
                    //    progressBar.visibility = View.VISIBLE
                    //    loadMoreItems()
                    //}
                }
            }
        })
    }

    fun setFilter(filter : String?){
        adapter.filter.filter(filter)
    }

    private fun updateMoreItems(){
        progressBar.visibility = View.INVISIBLE
        //adapter.notifyItemRangeInserted(lastItemIndex, listItemsFull.lastIndex - lastItemIndex)
        lastItemIndex = listItemsFull.lastIndex

        adapter.updateList()
    }

    private fun getArrayItems(){
        when(query){
            -1 -> if(context?.let { ConectionUtility.isOnline(it) } == true){
                APIService.traerPublicaciones { publis, t ->
                    publis?.forEach {
                        listItemsFull.add(TileEntity(it.id, SaveSharedPreference.base64ToBitmap(it.imagen, context), it.titulo, it.nombre, it.generoN))
                    }
                    updateMoreItems()
                }
            }else{
                DBsqlite?.GetPublicaciones()?.forEach {
                    it.Imagen?.let { it1 ->listItemsFull.add(TileEntity(it.PostID,
                        ImageUtilities.getBitMapFromByteArray(it1),it.Titulo,it.Nameuser,it.CatName))}
                }
                updateMoreItems()
            }



            -2 -> LoginRepository.instance()?.user?.userId?.let {APIService.traerFavoritos(it){ publis, t ->
                publis?.forEach {
                    listItemsFull.add(TileEntity(it.id, SaveSharedPreference.base64ToBitmap(it.imagen, context), it.titulo, it.nombre, it.generoN))
                }
                updateMoreItems()
            } }

            -3 -> if (context?.let { ConectionUtility.isOnline(it) } == true){
                LoginRepository.instance()?.user?.userId?.let { APIService.traerBorradorUser(it){ publis, t ->
                    publis?.forEach {
                        listItemsFull.add(TileEntity(it.id, SaveSharedPreference.base64ToBitmap(it.imagen, context), it.titulo, it.nombre, it.generoN))
                    }
                    updateMoreItems()
                }
                }

            } else {
                listItemsFull.add(TileEntity(-1, BitmapFactory.decodeResource(resources,R.drawable.default_image), "es necesaria una coneccion de internet para visualizar este contenido", "revise su coneccion de internet", "sin Internet"))
                updateMoreItems()
            }

            else -> APIService.traerPublicacionesUser(query){ publis, t ->
                publis?.forEach {
                    listItemsFull.add(TileEntity(it.id, SaveSharedPreference.base64ToBitmap(it.imagen, context), it.titulo, it.nombre, it.generoN))
                }
                updateMoreItems()
            }

        }

    }
}
