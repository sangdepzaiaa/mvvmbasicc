package com.example.myapplication.ui.editVideo

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R

class VideoAdapter(private var videos: List<Uri>) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnail: ImageView = itemView.findViewById(R.id.videoThumbnail)
        val playIcon: ImageView = itemView.findViewById(R.id.playIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val uri = videos[position]

        // Load thumbnail
        Glide.with(holder.itemView)
            .load(uri)
            .centerCrop()
            .into(holder.thumbnail)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, EditVideoActivity::class.java)
            intent.putExtra("video_uri", uri)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = videos.size

    fun updateList(newList: List<Uri>) {
        videos = newList
        notifyDataSetChanged()
    }
}
