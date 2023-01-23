package com.example.tp_unsplash.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tp_unsplash.DetailPhotoView
import com.example.tp_unsplash.R
import com.example.tp_unsplash.schemas.UnSplashPhoto
import com.example.tp_unsplash.viewmodels.LikedPhotosViewModel

class UnSplashLikedPhotoAdapter(private var photos: List<UnSplashPhoto>,
                                private var viewModel: LikedPhotosViewModel
                                ) : RecyclerView.Adapter<UnSplashLikedPhotoAdapter.UnSplashLikedPhotoViewHolder>() {
    inner class UnSplashLikedPhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photo: ImageView = itemView.findViewById(R.id.photo_small)
        val btn: ImageButton = itemView.findViewById(R.id.btn_unlike)


        fun bind(unsplash_photo: UnSplashPhoto) {

            val uri : Uri = Uri.parse(unsplash_photo.urls.full)
            photo.load(uri)
            println("test " + unsplash_photo.id)
            btn.setOnClickListener {
                // remove photo from list
                val newList = photos.filter { it.id != unsplash_photo.id }
                photos = listOf()
                notifyDataSetChanged()
                photos = newList
                notifyDataSetChanged()

                // unlike photo
                viewModel.unlikePhoto(unsplash_photo.id)
            }
            photo.setOnClickListener {
                val intent = Intent(itemView.context, DetailPhotoView::class.java)
                //intent.putExtra("photo", unsplash_photo)
                intent.putExtra("photo_id", unsplash_photo.id)
                intent.putExtra("photo_url", unsplash_photo.urls.full)
                intent.putExtra("author_name", unsplash_photo.user.name)
                intent.putExtra("description", unsplash_photo.alt_description)
                intent.putExtra("is_liked", unsplash_photo.liked_by_user)

                itemView.context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnSplashLikedPhotoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.photo_liked_view, parent, false)

        return UnSplashLikedPhotoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UnSplashLikedPhotoViewHolder, position: Int) {
        val item = photos.get(position)
        holder.bind(item)
    }

    override fun getItemCount() = photos.size

    fun setData(photos: List<UnSplashPhoto>?) {
        if (photos != null) {
            this.photos = photos
            notifyDataSetChanged()
        }
    }
}