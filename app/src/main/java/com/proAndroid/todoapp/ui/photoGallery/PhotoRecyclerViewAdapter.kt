package com.proAndroid.todoapp.ui.photoGallery

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import com.bumptech.glide.RequestManager
import com.proAndroid.todoapp.R

import kotlinx.android.synthetic.main.fragment_photo_gallery.view.*


class PhotoRecyclerViewAdapter(
    private val values: MutableList<Photo>,
    private val glide: RequestManager,
    private val listener: InteractionListener
) : RecyclerView.Adapter<PhotoRecyclerViewAdapter.ViewHolder>() {

    interface InteractionListener {
        fun onClick(photo: Photo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_photo_gallery, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = values[position]
        holder.render(photo)
    }

    override fun getItemCount(): Int = values.size

    fun update(mValues: List<Photo>) {
        values.clear()
        values.addAll(mValues)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun render(photo: Photo) {
            glide
                .load(photo.uri.toUri())
                .into(itemView.image)

            itemView.image.setOnClickListener {
                listener.onClick(photo)
            }
        }
    }
}