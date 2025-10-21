package com.example.myapplication.ui.main


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.Post
import com.example.myapplication.data.repository.PostRepository
import kotlinx.coroutines.launch


class MainViewModel(val repository: PostRepository): ViewModel(){
    val posts:LiveData<List<Post>> = repository.allPosts.asLiveData()

    val _error = MutableLiveData<String>()
    val error:LiveData<String> get() = _error

    init {
        // Đảm bảo LiveData được observe trước khi fetch API
        viewModelScope.launch {
            val postsFromApi = repository.fetchPostsFromApi()
            if (postsFromApi.isEmpty()) {
                _error.postValue("Cannot fetch posts or API empty")
            }
        }
    }


    fun insertPost(post: Post) = viewModelScope.launch { repository.insertPost(post) }
    fun insertPosts(posts:List<Post>) = viewModelScope.launch { repository.inserposts(posts) }
    fun deletePost(post: Post) = viewModelScope.launch { repository.deletePost(post) }
    fun updatepost(post:Post) = viewModelScope.launch { repository.updatePost(post) }




}

//    fun savePostsToExternalPrivate(context: Context) {
//        viewModelScope.launch {
//            try {
//                // ⚡ Lấy danh sách bài post từ LiveData hiện có
//                val postsToSave = posts.value
//                if (postsToSave.isNullOrEmpty()) {
//                    _error.postValue("Không có bài đăng nào để lưu.")
//                    return@launch
//                }
//
//                // ⚡ Chuyển list Post thành chuỗi JSON
//                val gson = Gson()
//                val jsonString = gson.toJson(postsToSave)
//
//                // ⚡ Ghi file ra external private storage
//                val file = File(context.getExternalFilesDir(null), "posts_private.json")
//                file.writeText(jsonString)
//
//                // ⚡ In log đường dẫn
//                Log.d("FilePath", "✅ File saved at: ${file.absolutePath}")
//
//                _error.postValue("Lưu file private thành công!")
//
//            } catch (e: Exception) {
//                Log.e("FilePath", "❌ Error saving file: ${e.message}")
//                _error.postValue("Lỗi khi lưu file private: ${e.message}")
//            }
//        }
//    }
//
//    fun loadPostsFromExternalPrivate(context: Context): List<Post>? {
//        return try {
//            val file = File(context.getExternalFilesDir(null), "posts_private.json")
//            if (!file.exists()) return null
//
//            val json = file.readText()
//            val gson = Gson()
//            val type = object : TypeToken<List<Post>>() {}.type
//            gson.fromJson<List<Post>>(json, type)
//        } catch (e: Exception) {
//            Log.e("FilePath", "❌ Error reading file: ${e.message}")
//            null
//        }
//    }
//
//    /**
//     * Lưu danh sách bài đăng vào thư mục Downloads công cộng.
//     * File này sẽ không bị xóa khi gỡ app.
//     */
//    fun savePostsToPublicDownloads(context: Context) {
//        viewModelScope.launch {
//            try {
//                val postsToSave = posts.value
//                if (postsToSave.isNullOrEmpty()) {
//                    _error.postValue("Không có bài đăng nào để lưu.")
//                    return@launch
//                }
//
//                val gson = Gson()
//                val jsonString = gson.toJson(postsToSave)
//
//                val fileName = "MyApplicationPosts_${System.currentTimeMillis()}.json"
//                var outputStream: OutputStream? = null
//
//                try {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                        // Dành cho Android 10 (API 29) trở lên
//                        val contentResolver = context.contentResolver
//                        val contentValues = ContentValues().apply {
//                            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
//                            put(MediaStore.MediaColumns.MIME_TYPE, "application/json")
//                            put(MediaStore.MediaColumns.RELATIVE_PATH, "Download/MyApplication")
//                        }
//                        val uri = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
//                        outputStream = uri?.let { contentResolver.openOutputStream(it) }
//
//                    } else {
//                        // Dành cho Android 9 (API 28) trở xuống
//                        @Suppress("DEPRECATION")
//                        val downloadsDir = android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_DOWNLOADS)
//                        val appDir = File(downloadsDir, "MyApplication")
//                        if (!appDir.exists()) {
//                            appDir.mkdir()
//                        }
//                        val file = File(appDir, fileName)
//                        outputStream = file.outputStream()
//                    }
//
//                    outputStream?.use { it.write(jsonString.toByteArray()) }
//                    _error.postValue("Lưu file public thành công vào thư mục Downloads/MyApplication")
//
//                } finally {
//                    outputStream?.close()
//                }
//
//            } catch (e: Exception) {
//                _error.postValue("Lỗi khi lưu file public: ${e.message}")
//            }
//        }
//    }


