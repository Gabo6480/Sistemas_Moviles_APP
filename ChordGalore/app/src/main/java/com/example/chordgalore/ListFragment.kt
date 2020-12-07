package com.example.chordgalore

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chordgalore.data.model.TileEntity
import com.example.chordgalore.ui.content_view.ContentRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_list.*


//Query va a ser utilizado para seleccionar que lista entre Home, Favorite y Downloads le vamos a mostrar
//Se vale cambiar el tipo de dato si se considera más adecuado
class ListFragment(query : Int) : Fragment() {

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
                val intent = Intent(context, SongActivity::class.java)
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

                    val endHasBeenReached = lastVisible + 5 >= totalItemCount
                    if (totalItemCount > 0 && endHasBeenReached) {
                        progressBar.visibility = View.VISIBLE
                        loadMoreItems()
                    }
                }
            }
        })
    }

    private fun loadMoreItems(){
        listItemsFull.add(TileEntity(1 + changeCounter, R.drawable.user_image, "Macarena $changeCounter", "Pepe", "Rock"))
        listItemsFull.add(TileEntity(2 + changeCounter,R.drawable.user_image, "Algo $changeCounter", "Toño", "Mañoño"))
        listItemsFull.add(TileEntity(3 + changeCounter,R.drawable.user_image, "E $changeCounter", "Marquiplier", "888"))
        listItemsFull.add(TileEntity(4 + changeCounter,R.drawable.user_image, "Otro $changeCounter", "Asu","Madre"))
        listItemsFull.add(TileEntity(5 + changeCounter,R.drawable.user_image, "Lorem $changeCounter", "Ipsum", "Opers"))
        listItemsFull.add(
            TileEntity(6 + changeCounter,
                R.drawable.user_image,
                "Titulototototote $changeCounter",
                "AAAAAAAAAAAAAAAAAAAAAAAAA",
                "Death Metal"
            )
        )
        changeCounter++

        //Esto pospone el llamado del update por 1 frame para evitar errores
        Handler(Looper.getMainLooper()).postDelayed({
            updateMoreItems()
        }, 0)

    }

    private fun updateMoreItems(){
        progressBar.visibility = View.INVISIBLE
        //adapter.notifyItemRangeInserted(lastItemIndex, listItemsFull.lastIndex - lastItemIndex)
        lastItemIndex = listItemsFull.lastIndex

        adapter.updateList()
    }

    fun setFilter(filter : String?){
        adapter.filter.filter(filter)
    }

    private fun getArrayItems(){
        loadMoreItems()
        loadMoreItems()

        lastItemIndex = listItemsFull.lastIndex
    }
}
