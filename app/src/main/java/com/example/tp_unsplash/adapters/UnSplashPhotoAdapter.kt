package com.example.tp_unsplash.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tp_unsplash.DetailPhotoView
import com.example.tp_unsplash.R
import com.example.tp_unsplash.schemas.UnSplashPhoto

class UnSplashPhotoAdapter(private var photos: List<UnSplashPhoto>) : RecyclerView.Adapter<UnSplashPhotoAdapter.UnSplashPhotoViewHolder>() {

    class UnSplashPhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photo: ImageView = itemView.findViewById(R.id.photo_small)
        fun bind(unsplash_photo: UnSplashPhoto) {
            val uri : Uri = Uri.parse(unsplash_photo.urls.full)
            photo.load(uri)

            photo.setOnClickListener {
                val intent = Intent(itemView.context, DetailPhotoView::class.java)
                intent.putExtra("photo", unsplash_photo)
                itemView.context.startActivity(intent)
            }
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