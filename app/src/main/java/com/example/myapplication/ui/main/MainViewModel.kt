package com.example.myapplication.ui.main


import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.Post
import com.example.myapplication.data.repository.PostRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import java.io.File
import java.io.OutputStream


class MainViewModel(val repository: PostRepository): ViewModel(){
    val posts:LiveData<List<Post>> = repository.allPost.asLiveData()

    val _error = MutableLiveData<String>()
    val error:LiveData<String> get() = _error

    init {
        // Đảm bảo LiveData được observe trước khi fetch API
        viewModelScope.launch {
            val postsFromApi = repository.fetchAllPost()
            if (postsFromApi.isEmpty()) {
                _error.postValue("Cannot fetch posts or API empty")
            }
        }
    }


    fun insertPost(post: Post) = viewModelScope.launch { repository.insertPost(post) }
    fun insertPosts(posts:List<Post>) = viewModelScope.launch { repository.insertPosts(posts) }
    fun deletePost(post: Post) = viewModelScope.launch { repository.deletePost(post) }
    fun updatepost(post:Post) = viewModelScope.launch { repository.updatePost(post) }

    fun savePostsToPublicDownloads(context: Context) {
        viewModelScope.launch {
            try {
                val postsToSave = posts.value
                if (postsToSave.isNullOrEmpty()) {
                    _error.postValue("Không có bài đăng nào để lưu.")
                    return@launch
                }

                val gson = Gson()
                val jsonString = gson.toJson(postsToSave)

                val fileName = "MyApplicationPosts_${System.currentTimeMillis()}.json"
                var outputStream: OutputStream? = null

                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        // Dành cho Android 10 (API 29) trở lên
                        val contentResolver = context.contentResolver
                        val contentValues =
                            ContentValues().apply {
                                put(MediaStore.MediaColumns.DISPLAY_NAME,fileName)
                                put(MediaStore.MediaColumns.MIME_TYPE, "application/json")
                                put(MediaStore.MediaColumns.RELATIVE_PATH, "Download/MyApplication")
//                              put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis() / 1000)
//                              put(MediaStore.MediaColumns.SIZE, fileSize)
                            }
                        val uri = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
                        outputStream = uri?.let { contentResolver.openOutputStream(it) }
                    } else {
                        // Dành cho Android 9 (API 28) trở xuống
                        @Suppress("DEPRECATION")
                        val downloadsDir =
                            android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_DOWNLOADS)
                        val appDir = File(downloadsDir, "MyApplication")
                        if (!appDir.exists()) {
                            appDir.mkdir()
                        }
                        val file = File(appDir, fileName)
                        outputStream = file.outputStream()
                    }

                    outputStream?.use { it.write(jsonString.toByteArray()) }
                    _error.postValue("Lưu file public thành công vào thư mục Downloads/MyApplication")

                } finally {
                    outputStream?.close()
                }

            } catch (e: Exception) {
                _error.postValue("Lỗi khi lưu file public: ${e.message}")
            }
        }
    }

    fun savePostsToExternalPrivate(context: Context) {
        viewModelScope.launch {
            try {
                // ⚡ Lấy danh sách bài post từ LiveData hiện có
                val postsToSave = posts.value
                if (postsToSave.isNullOrEmpty()) {
                    _error.postValue("Không có bài đăng nào để lưu.")
                    return@launch
                }

                // ⚡ Chuyển list Post thành chuỗi JSON
                val gson = Gson()
                val jsonString = gson.toJson(postsToSave)

                // ⚡ Ghi file ra external private storage
                val file = File(context.getExternalFilesDir(null), "posts_private.json") // tạo đối tương file,chưa tạo file thật.
                file.writeText(jsonString) //tạo file + ghi dữ liệu

                // ⚡ In log đường dẫn
                Log.d("FilePath", "✅ File saved at: ${file.absolutePath}")

                _error.postValue("Lưu file private thành công!")

            } catch (e: Exception) {
                Log.e("FilePath", "❌ Error saving file: ${e.message}")
                _error.postValue("Lỗi khi lưu file private: ${e.message}")
            }
        }
    }

    fun loadPostsFromExternalPrivate(context: Context): List<Post>? {
        return try {
            val file = File(context.getExternalFilesDir(null), "posts_private.json")
            if (!file.exists()) return null

            val json = file.readText() //đọc dữ liệu
            val gson = Gson()
            val type = object : TypeToken<List<Post>>() {}.type
            gson.fromJson<List<Post>>(json, type)

        } catch (e: Exception) {
            Log.e("FilePath", "❌ Error reading file: ${e.message}")
            null
        }
    }


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
//Trong Kotlin, mọi khối lệnh (block) như
//if, when, try, let, run, apply, v.v.
//đều có thể được coi là biểu thức (expression) — tức là nó trả về một giá trị.
//
//👉 Và giá trị trả về của block đó chính là giá trị của dòng cuối cùng bên trong nó
//(miễn dòng đó không có return, throw, hay kết thúc sớm).
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
//                var outputStream: OutputStream? = null // luồng dẫn dữ liệu ra, file:chai, nước: dữ liệu, OutputStream: cái phễu
//
//                try {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                        // Dành cho Android 10 (API 29) trở lên
//                        val contentResolver = context.contentResolver // người mang thông tin đó đi đăng ký với hệ thống
// android 10+ , google dùng contentResolver để Gửi yêu cầu đọc hoặc ghi dữ liệu qua URI (địa chỉ nội dung) , bảo mật hơn do có contentResolver kiểm duyệt
//Hệ thống kiểm tra quyền, rồi cho phép bạn truy cập hoặc ghi dữ liệu, do android < 10 ghi trực tiếp đã bị chặn
//
//                        val contentValues = ContentValues().apply { //ContentValues():  Tờ giấy khai thông tin file”
//                            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)  // mediastore :lưu trữ toàn bo media
//                            put(MediaStore.MediaColumns.MIME_TYPE, "application/json")
//                            put(MediaStore.MediaColumns.DOWNLOAD, "Download/MyApplication")
//                            put(MediaStore.MediaColumns.DATE_ADDED, "Download/MyApplication")
//                            put(MediaStore.MediaColumns.SIZE, "Download/MyApplication")
//
//                        }
//                        val uri = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
//Đây là lệnh gửi “tờ khai” đó cho hệ thống Android.
// gửi file đến aandroid,nếu hợp lệ thì  tạo một file rỗng chỉ là placeholder, chưa có nội dung. trong download
// ,Một số launcher hoặc trình quản lý file có thể chưa hiển thị file này ngay nếu chưa có dữ liệu.
//Chuẩn bị URI và vị trí lưu
// chưa ghi dữ liệu

//Sau đó Android trả về cho bạn 1 cái uri(Là mã định danh duy nhất (ID) của file bạn vừa tạo.) đại diện cho file đó.
// id này ghi trong database nội boo của android mediastore
//// Tôi muốn gửi file này vào khu Downloads trong MediaStore nhé
//// MediaStore.Downloads.EXTERNAL_CONTENT_URI :Cánh cửa dẫn tới kho Download,
//                         outputStream = uri?.let { contentResolver.openOutputStream(it) }
//// “Mở cho tôi một cái vòi nối từ app của tôi đến cái file thật đó, để tôi rót dữ liệu JSON vào file đó.”
//Không tạo file mới, chỉ sử dụng file rỗng/placeholder đã có.
//Dùng URI từ insert() để ghi dữ liệu vào file đã tạo
// có thể ghi dữ liệu
////outputStream chính là cái vòi dẫn dữ liệu.

//💡 Nhớ:
//Không openOutputStream() → file vẫn rỗng, không dùng được.
//Phải openOutputStream() + write() → file mới có nội dung thực tế.
//                    } else {
//                        // Dành cho Android 9 (API 28) trở xuống
//                        @Suppress("DEPRECATION")
//                        val downloadsDir = android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_DOWNLOADS) // lấy đường dẫn đến download trên thieets bị
//                        val appDir = File(downloadsDir, "MyApplication")
// tạo đối tượng File trong Kotlin, kiểu như “đặt tên, chuẩn bị đường dẫn”, chưa sinh ra file trên máy.
//                        if (!appDir.exists()) {
//                            appDir.mkdir() // mkdir() TẠO thư mục thật trong Download/
//                        }
//                        val file = File(appDir, fileName)
//                        outputStream = file.outputStream() // TẠO file thật nếu chưa có và mở để ghi dữ liệu
//                    }
//
//                    outputStream?.use { it.write(jsonString.toByteArray()) } // ghi vào file
// OutputStream.write() chỉ chấp nhận mảng byte (ByteArray) ,toByteArray(): CHuyển thành byte
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
 //   }


