package com.example.tp_unsplash.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_unsplash.R
import com.example.tp_unsplash.schemas.UnSplashPhoto

class UnSplashPhotoAdapter(private var photos: List<UnSplashPhoto>) : RecyclerView.Adapter<UnSplashPhotoAdapter.UnSplashPhotoViewHolder>() {
    class UnSplashPhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val firstName: TextView = itemView.findViewById(R.id.photo)


        fun bind(photo: UnSplashPhoto) {
            firstName.text = photo.description

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnSplashPhotoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.photo_view, parent, false)
        return UnSplashPhotoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UnSplashPhotoViewHolder, position: Int) {
        val item = photos.get(position)
        holder.bind(item)
    }

    override fun getItemCount() = photos.size

    fun setData(photos: List<UnSplashPhoto>?) {
        if (photos != null) {
            this.photos = photos
        }
        notifyDataSetChanged()
    }
}