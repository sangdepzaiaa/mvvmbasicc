package com.example.myapplication.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.data.model.Post
import com.example.myapplication.databinding.LayoutBottomSheetPostBinding
import com.example.myapplication.extension.ExtImg.loadImg
import com.google.android.material.bottomsheet.BottomSheetDialog


//"https://randomuser.me/api/portraits/women/${post?.id % 100}.jpg"

object DialogHelp{
    fun showPostPopup(context: Context, post: Post?,onSave: (title:String,body:String) -> Unit, onDelete: (()->Unit)?=null){
        val binding = LayoutBottomSheetPostBinding.inflate(LayoutInflater.from(context))
        val dialog = BottomSheetDialog(context)
        dialog.setContentView(binding.root)

        binding.imgAvatar.loadImg("https://randomuser.me/api/portraits/women/${post?.id ?: 100}.jpg")

        binding.run {
            etTitle.setText(post?.title)
            etBody.setText(post?.body)
        }

        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val body = binding.etBody.text.toString().trim()

            if(title.isNotEmpty() && body.isNotEmpty()){
                onSave.invoke(title,body)
                dialog.dismiss()
            }else{
                Toast.makeText(context, R.string.title_body_no_empty, Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnDelete.setOnClickListener {
            onDelete?.invoke()
            dialog.dismiss()
        }

        binding.root.apply {
            alpha = 0f     // độ mờ f:FLoat:
            scaleX = 0.5f  // chiều ngang
            scaleY = 0.5f  // chiều dọc
            animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(350)
                .start()
        }
        dialog.show()
    }
}