package com.example.myapplication.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.model.Post
import com.example.myapplication.databinding.LayoutBottomSheetPostBinding
import com.example.myapplication.extension.extension.loadCircleImage
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlin.random.Random

object dialog_help {
    fun showPostPopup(context: Context, post: Post?, onSave: (title: String, body: String) -> Unit, onDelete: (() -> Unit)? = null) {
        val binding = LayoutBottomSheetPostBinding.inflate(LayoutInflater.from(context))
        val dialog = BottomSheetDialog(context)
        dialog.setContentView(binding.root)

        Glide.with(context)
            .load("https://i.pravatar.cc/150?img=${post?.id ?: 1}")
            .circleCrop()
            .into(binding.imgAvatar)

        post?.let {
            binding.run {
                etTitle.setText(it.title)
                etBody.setText(it.body)
                btnDelete.visibility = View.VISIBLE
            }
        }

        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val body = binding.etBody.text.toString().trim()
            if (title.isNotEmpty() && body.isNotEmpty()) {
                onSave(title, body)
                dialog.dismiss()
            } else {
                Toast.makeText(context, R.string.title_body_no_empty, Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnDelete.setOnClickListener {
            onDelete?.invoke()
            dialog.dismiss()
        }

        binding.root.alpha = 0f
        binding.root.animate().alpha(1f).setDuration(250).start()


        dialog.show()
    }

}