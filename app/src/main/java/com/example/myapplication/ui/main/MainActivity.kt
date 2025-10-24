package com.example.myapplication.ui.main




import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



import com.example.myapplication.data.model.Post
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.ui.dialog.DialogHelp.showPostPopup
import org.koin.androidx.viewmodel.ext.android.viewModel



class MainActivity : AppCompatActivity(){
    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    private  val postAdapter by lazy {
        PostAdapter()
    }

    private  val viewModel: MainViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupView()
        bindVM()
    }

    private fun setupView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = postAdapter
        binding.recyclerView.setOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(dy > 0) binding.fabAdd.hide()
                else binding.fabAdd.show()
            }
        })



        binding.fabAdd.setOnClickListener {

            showPostPopup(this,null,
                onSave = {title, body ->
                    val newpost = Post(
                        id = (System.currentTimeMillis() % Int.MAX_VALUE).toInt(),
                        title = title,
                        body = body,
                    )
                    viewModel.insertPost(newpost)

                })
        }

        postAdapter.onClickItem = { posts ->
            showPostPopup(this, posts,
                onSave = { title,body ->
                    val updatePost = posts.copy(title = title, body = body)
                    viewModel.updatepost(updatePost)
                },
                onDelete = {
                    viewModel.deletePost(posts)
                })

        }
    }

    private fun bindVM() {
      viewModel.posts.observe(this){posts ->
          postAdapter.submitList(posts)
      }

      viewModel.error.observe(this){error ->
          error?.let {
              Toast.makeText(this,it, Toast.LENGTH_SHORT).show()
          }

      }
    }
}

//        binding.btnSavePrivate.setOnClickListener {
//            viewModel.savePostsToExternalPrivate(this)
//        }
//
//        binding.btnLoad.setOnClickListener {
//            val loaded = viewModel.loadPostsFromExternalPrivate(this)
//            Log.d("FilePath", "📂 Loaded: $loaded")
//        }

//        binding.btnSavePublic.setOnClickListener {
//            viewModel.savePostsToPublicDownloads(this@MainActivity)
//        }

//private fun setupFab() {
//    binding.fabAdd.setOnClickListener { fab ->
//        // FAB animation
//        val anim = AnimationUtils.loadAnimation(fab.context, R.anim.fab_click_blast)
//        fab.startAnimation(anim)
//
//        // Konfetti effect
//        showKonfetti(fab)
//
//        // Popup tạo post mới
//        showPostPopup(this, null,
//
//            onSave = { title, body ->
//
//                val newPost = Post(
//                    id = (System.currentTimeMillis() % Int.MAX_VALUE).toInt(),
//                    title = title,
//                    body = body
//                )
//                viewModel.insertPost(newPost)
//            }
//        )
//    }
//}
//
//private fun showKonfetti(fab: View) {
//    val location = IntArray(2)
//    fab.getLocationInWindow(location)
//    val x = ((location[0] + fab.width / 2f) / binding.root.width).toDouble()
//    val y = ((location[1] + fab.height / 2f) / binding.root.height).toDouble()
//
//    val emitterConfig = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100)
//    val party = PartyFactory(emitter = emitterConfig)
//        .spread(360)
//        .position(Position.Relative(x, y))
//        .timeToLive(1200)
//        .sizes(Size(6, 10f))
//        .shapes(listOf(Shape.Circle))
//        .colors(listOf(
//            0xFF03A9F4.toInt(),
//            0xFF00BCD4.toInt(),
//            0xFF80DEEA.toInt(),
//            0xFFFFFFFF.toInt()
//        ))
//        .build()
//
//    binding.konfettiView.start(party)
//}
