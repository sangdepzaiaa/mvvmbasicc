package com.example.myapplication.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.model.Post
import com.example.myapplication.databinding.ItemPostBinding
import com.example.myapplication.extension.ExtImg.loadImg

//"https://randomuser.me/api/portraits/men/${post.id % 100}.jpg"

 class PostAdapter : ListAdapter<Post, PostAdapter.PostViewHolder>(DiffutilCallback){

    lateinit var onClickItem: (Post) -> Unit
    class PostViewHolder(val binding: ItemPostBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post, onCLickItem:(Post) -> Unit){
            binding.run {
                postTitle.text = post.title
                postBody.text = post.body
                postImage.loadImg("https://randomuser.me/api/portraits/men/${post.id % 100}.jpg")

                root.setOnClickListener {
                    onCLickItem(post)
                }
            }


        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostViewHolder {
        return PostViewHolder(ItemPostBinding.
        inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(
        holder: PostViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position),onClickItem )
    }
    override fun getItemCount(): Int {
        return currentList.size
    }


}

object DiffutilCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(
        oldItem: Post,
        newItem: Post
    ): Boolean {
       return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Post,
        newItem: Post
    ): Boolean {
        return oldItem == newItem
    }

}
