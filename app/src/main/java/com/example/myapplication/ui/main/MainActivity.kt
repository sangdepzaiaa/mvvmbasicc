package com.example.myapplication.ui.main


import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.model.Post
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.ui.dialog.dialog_help.showPostPopup
import nl.dionsegijn.konfetti.core.PartyFactory
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.core.models.Shape
import nl.dionsegijn.konfetti.core.models.Size
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity(){
    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    val postAdapter by lazy{
        PostAdapter()
    }
    private val viewmodel: MainViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupView()
        bindVM()
    }

    private fun setupView() {
        binding.recyclerView.run {
            adapter = postAdapter
        }
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) binding.fabAdd.hide()
                else if (dy < 0) binding.fabAdd.show()
            }
        })

        binding.fabAdd.setOnClickListener { fab ->

            val anim = AnimationUtils.loadAnimation(fab.context, R.anim.fab_click_blast)
            fab.startAnimation(anim)

            val location = IntArray(2)
            fab.getLocationInWindow(location)

            val x = ((location[0] + fab.width / 2f) / binding.root.width).toDouble()
            val y = ((location[1] + fab.height / 2f) / binding.root.height).toDouble()

            val emitterConfig = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100)

            val party = PartyFactory(emitter = emitterConfig)
                .spread(360)
                .position(Position.Relative(x, y))
                .timeToLive(1200)
                .sizes(Size(6, 10f))
                .shapes(listOf(Shape.Circle))
                .colors(
                    listOf(
                        0xFF03A9F4.toInt(),
                        0xFF00BCD4.toInt(),
                        0xFF80DEEA.toInt(),
                        0xFFFFFFFF.toInt()
                    )
                )
                .build()

            binding.konfettiView.start(party)

            showPostPopup(this, null, onSave = { title, body ->
                viewmodel.addPost(title, body)
            })
        }
        postAdapter.onItemClick = { post: Post ->
            showPostPopup(this, post,
                onSave = { title, body ->
                    viewmodel.updatePost(post, title, body)
                },
                onDelete = {
                    viewmodel.deletePost(post)
                }
            )
        }

    }

    private fun bindVM() {
        viewmodel.post.observe(this,::render)
        viewmodel.error.observe(this){error ->
            error.let{
                Toast.makeText(this,"error", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun render(posts: List<Post>) {
        postAdapter.submitList(posts)
    }
}