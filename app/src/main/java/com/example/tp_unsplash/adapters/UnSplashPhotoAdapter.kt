package com.example.tp_unsplash.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tp_unsplash.DetailPhotoView
import com.example.tp_unsplash.R
import com.example.tp_unsplash.schemas.UnSplashPhoto

class UnSplashPhotoAdapter(private var photos: List<UnSplashPhoto>, private var app_ctx: Context) : RecyclerView.Adapter<UnSplashPhotoAdapter.UnSplashPhotoViewHolder>() {

    class UnSplashPhotoViewHolder(itemView: View, _ctx: Context) : RecyclerView.ViewHolder(itemView) {
        val photo: ImageView = itemView.findViewById(R.id.photo_small)
        private var ctx: Context = _ctx
        fun bind(unsplash_photo: UnSplashPhoto) {
            val uri : Uri = Uri.parse(unsplash_photo.urls.full)
            photo.load(uri)
            photo.setOnClickListener {
                val intent = Intent(ctx, DetailPhotoView::class.java)
                //intent.putExtra("photo", unsplash_photo)
                intent.putExtra("photo_id", unsplash_photo.id)
                intent.putExtra("photo_url", unsplash_photo.urls.full)
                intent.putExtra("author_name", unsplash_photo.user.name)
                intent.putExtra("description", unsplash_photo.description)
                intent.putExtra("is_liked", unsplash_photo.liked_by_user)

                ctx.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnSplashPhotoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.photo_view, parent, false)
        return UnSplashPhotoViewHolder(itemView, app_ctx)
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