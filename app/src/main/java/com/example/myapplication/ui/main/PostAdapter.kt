package com.example.myapplication.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.model.Post
import com.example.myapplication.databinding.ItemPostBinding
import com.example.myapplication.extension.ExtImg.loadCircleImage


class PostAdapter : ListAdapter<Post, PostAdapter.postViewHoler>(callback){

    lateinit var onItemClick: (Post) -> Unit

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): postViewHoler {
        return postViewHoler(ItemPostBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
     class postViewHoler(var binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post,onItemClick: (Post) -> Unit){
            binding.run {
                postTitle.text = post.title
                postBody.text = post.body
                root.setOnClickListener {
                    onItemClick(post)
                }

                postImage.loadCircleImage(
                    "https://randomuser.me/api/portraits/men/${post.id % 100}.jpg"
                )

            }
        }
    }


    override fun onBindViewHolder(holder: postViewHoler, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }



    override fun getItemCount(): Int {
        return currentList.size
    }

}

object callback : DiffUtil.ItemCallback<Post>(){
    override fun areItemsTheSame(
        oldItem: Post,
        newItem: Post
    ): Boolean {
        return  oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Post,
        newItem: Post
    ): Boolean {
        return oldItem == newItem
    }

}