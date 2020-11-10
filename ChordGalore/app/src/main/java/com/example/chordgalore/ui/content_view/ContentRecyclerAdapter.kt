package com.example.chordgalore.ui.content_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chordgalore.R
import com.example.chordgalore.data.model.TileEntity
import java.util.*
import kotlin.collections.ArrayList

class ContentRecyclerAdapter(arrayList : ArrayList<TileEntity>, arrayListFull : ArrayList<TileEntity>) : RecyclerView.Adapter<ContentRecyclerAdapter.ItemViewHolder>(), Filterable{
    private var _arrayList = arrayList
    private var _arrayListFull = arrayListFull
    private lateinit var _listener : OnItemClickListener

    private var _filter : String? = ""

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener : OnItemClickListener){
        _listener = listener
    }

    class ItemViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView){
        val imgView : ImageView = itemView.findViewById(R.id.tile_image)
        val titleView : TextView = itemView.findViewById(R.id.tile_title)
        val subtitleView : TextView = itemView.findViewById(R.id.tile_subtitle)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION){
                    listener.onItemClick(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
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

    fun updateList(){
        filter.filter(_filter)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = ArrayList<TileEntity>()
                val unfilteredList = ArrayList<TileEntity>(_arrayListFull)

                if(constraint == null || constraint.isEmpty()){
                    filteredList.addAll(unfilteredList)
                    _filter = ""
                }
                else{
                    val filterPattern = constraint.toString().toLowerCase(Locale.ROOT).trim()
                    _filter = filterPattern

                    filteredList.addAll(unfilteredList.filter { item -> item._title.toLowerCase(Locale.ROOT).contains(filterPattern) })
                }

                val results = FilterResults()
                results.values = filteredList

                return results
            }

            override fun publishResults(p0: CharSequence?, results: FilterResults?) {
                if (results?.values != null) {
                    _arrayList.clear()
                    _arrayList.addAll(results.values as ArrayList<TileEntity>)
                }

                notifyDataSetChanged()
            }
        }
    }
}