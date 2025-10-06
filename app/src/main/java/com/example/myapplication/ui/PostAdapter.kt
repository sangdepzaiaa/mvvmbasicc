package com.example.myapplication.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.model.Post
import com.example.myapplication.databinding.ItemPostBinding


class PostAdapter() : ListAdapter<Post, PostAdapter.VH>(callback()){

    lateinit var onItemClick: (Post) -> Unit

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostAdapter.VH {
        return VH(ItemPostBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    inner class VH(var binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post){
            binding.run {
                postTitle.text = post.title
                postBody.text = post.body
                root.setOnClickListener {
                    onItemClick(post)
                }
            }
        }
    }


    override fun onBindViewHolder(holder: PostAdapter.VH, position: Int) {
      holder.bind(getItem(position))
    }



    override fun getItemCount(): Int {
        return currentList.size
    }

}

class callback : DiffUtil.ItemCallback<Post>(){
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