package com.example.myapplication.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop

object extension {

    fun ImageView.loadCircleImage(url: String) {
        Glide.with(this.context)
            .load(url)
            .transform(CircleCrop())
            .into(this)
    }
}