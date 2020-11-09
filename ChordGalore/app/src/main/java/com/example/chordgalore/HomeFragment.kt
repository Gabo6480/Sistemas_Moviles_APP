package com.example.chordgalore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.chordgalore.data.model.TileEntity


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
