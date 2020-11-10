package com.example.chordgalore

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
                Toast.makeText(
                    context,
                    "${item._title} cliqueado",
                    Toast.LENGTH_LONG
                ).show()
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
        changeCounter++
        listItemsFull.add(TileEntity(R.drawable.user_image, "Macarena $changeCounter", "Pepe"))
        listItemsFull.add(TileEntity(R.drawable.user_image, "Algo $changeCounter", "Toño"))
        listItemsFull.add(TileEntity(R.drawable.user_image, "E $changeCounter", "Marquiplier"))
        listItemsFull.add(TileEntity(R.drawable.user_image, "Otro $changeCounter", "Asu"))
        listItemsFull.add(TileEntity(R.drawable.user_image, "Lorem $changeCounter", "Ipsum"))
        listItemsFull.add(
            TileEntity(
                R.drawable.user_image,
                "Titulototototote $changeCounter",
                "AAAAAAAAAAAAAAAAAAAAAAAAA"
            )
        )

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
        listItemsFull.add(TileEntity(R.drawable.user_image, "Macarena $changeCounter", "Pepe"))
        listItemsFull.add(TileEntity(R.drawable.user_image, "Algo $changeCounter", "Toño"))
        listItemsFull.add(TileEntity(R.drawable.user_image, "E $changeCounter", "Marquiplier"))
        listItemsFull.add(TileEntity(R.drawable.user_image, "Otro $changeCounter", "Asu"))
        listItemsFull.add(TileEntity(R.drawable.user_image, "Lorem $changeCounter", "Ipsum"))
        listItemsFull.add(
            TileEntity(
                R.drawable.user_image,
                "Titulototototote $changeCounter",
                "AAAAAAAAAAAAAAAAAAAAAAAAA"
            )
        )
        changeCounter++
        listItemsFull.add(TileEntity(R.drawable.user_image, "Macarena $changeCounter", "Pepe"))
        listItemsFull.add(TileEntity(R.drawable.user_image, "Algo $changeCounter", "Toño"))
        listItemsFull.add(TileEntity(R.drawable.user_image, "E $changeCounter", "Marquiplier"))
        listItemsFull.add(TileEntity(R.drawable.user_image, "Otro $changeCounter", "Asu"))
        listItemsFull.add(TileEntity(R.drawable.user_image, "Lorem $changeCounter", "Ipsum"))
        listItemsFull.add(
            TileEntity(
                R.drawable.user_image,
                "Titulototototote $changeCounter",
                "AAAAAAAAAAAAAAAAAAAAAAAAA"
            )
        )

        lastItemIndex = listItemsFull.lastIndex
    }
}
