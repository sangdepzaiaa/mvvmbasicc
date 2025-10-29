package com.example.myapplication.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.R
import com.bumptech.glide.load.resource.bitmap.CircleCrop

object ExtImg{
    fun ImageView.loadImg(url:String){
        Glide.with(this.context) // this là kiểu class nó extension fun, ở đây là AppCompatImageView
            .load(url)
            .transform(CircleCrop())
            .placeholder(com.example.myapplication.R.drawable.ic_avatar_placeholder)
            .into(this)
    }
}