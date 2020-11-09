package com.example.chordgalore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chordgalore.data.model.TileEntity
import com.example.chordgalore.ui.content_view.ContentRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_favorite.*


class FavoriteFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ContentRecyclerAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var listItems: ArrayList<TileEntity>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerView = recycler_View
        recyclerView.setHasFixedSize(true)

        layoutManager = LinearLayoutManager(context)

        listItems = GetArrayItems()
        adapter = ContentRecyclerAdapter(listItems)

        recyclerView.layoutManager = this.layoutManager
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : ContentRecyclerAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                var item = listItems[position]
                Toast.makeText(
                    context,
                    "${item._title} cliqueado",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun GetArrayItems() : ArrayList<TileEntity>{
        val listItems = ArrayList<TileEntity>()

        listItems.add(TileEntity(R.drawable.user_image, "Macarena", "Pepe"))
        listItems.add(TileEntity(R.drawable.user_image, "Algo", "Toño"))
        listItems.add(TileEntity(R.drawable.user_image, "E", "Marquiplier"))
        listItems.add(TileEntity(R.drawable.user_image, "Otro", "Asu"))
        listItems.add(TileEntity(R.drawable.user_image, "Lorem", "Ipsum"))
        listItems.add(TileEntity(R.drawable.user_image, "Titulototototote", "AAAAAAAAAAAAAAAAAAAAAAAAA"))
        listItems.add(TileEntity(R.drawable.user_image, "Macarena", "Pepe"))
        listItems.add(TileEntity(R.drawable.user_image, "Algo", "Toño"))
        listItems.add(TileEntity(R.drawable.user_image, "E", "Marquiplier"))
        listItems.add(TileEntity(R.drawable.user_image, "Otro", "Asu"))
        listItems.add(TileEntity(R.drawable.user_image, "Lorem", "Ipsum"))
        listItems.add(TileEntity(R.drawable.user_image, "Titulototototote", "AAAAAAAAAAAAAAAAAAAAAAAAA"))

        return listItems
    }
}
