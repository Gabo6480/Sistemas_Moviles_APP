package com.example.chordgalore.ui.content_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chordgalore.R
import com.example.chordgalore.data.model.TileEntity

class ContentRecyclerAdapter(arrayList : ArrayList<TileEntity>) : RecyclerView.Adapter<ContentRecyclerAdapter.ItemViewHolder>() {
    private val _arrayList = arrayList
    private lateinit var _listener : OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener : OnItemClickListener){
        _listener = listener
    }

    class ItemViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView){
        val imgView = itemView.findViewById<ImageView>(R.id.tile_image)
        val titleView = itemView.findViewById<TextView>(R.id.tile_title)
        val subtitleView = itemView.findViewById<TextView>(R.id.tile_subtitle)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION){
                    listener.onItemClick(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentRecyclerAdapter.ItemViewHolder {
        val v : View = LayoutInflater.from(parent.context).inflate(R.layout.content_tile, parent, false)
        return ItemViewHolder(v, _listener)

    }

    override fun getItemCount(): Int {
        return _arrayList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val tileEntity =  _arrayList[position]

        holder.imgView.setImageResource(tileEntity._image)
        holder.titleView.text = tileEntity._title
        holder.subtitleView.text = tileEntity._subtitle
    }
}