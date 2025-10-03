package com.example.v205_ringtonemakermp3cutter_sandbox.base

import java.util.LinkedList
import java.util.Queue
import java.util.Stack


fun abc(){
    val list = listOf(1,2,3,2)
    val l = list
    print(list)
}

fun set(){
    val set = setOf(1,2,3,3)
    print(set)
}

fun map(){
    val map = mapOf(1 to 2, 3 to 4, 5 to 6)
    print(map[5])
}

fun stack(){
    val stack = Stack<Int>()
    stack.push(2)
    stack.push(5)
    stack.push(6)
    print(stack.pop())
}

fun queue(){
    val queue : Queue<Int> = LinkedList()
    queue.add(2)
    queue.add(3)
    queue.add(4)
    println(queue.remove())
}

fun hashmap(){
    val hashMap = HashMap<String, Int>()
    hashMap["ss"] = 3
    hashMap["ssa"] = 9
    println(hashMap["ss"])
}

fun mutable(){
    //tham trị luôn là biến nguyên thủy (Int, Double, Boolean, Char, …) cả immutable,mutable vì nó chiếm ít ô nhớ
    val a = 5
    var b = a
    b += 4
    print(a)
    print(b)

    // tham chiếu luôn là object (class, array, list, map …) cả immutable,mutable vì nó chiếm nhiều vùng nhớ
    val mutable = mutableListOf(2,2,2)
    val l = mutable
    l.add(3)
    println(l)
    println(mutable)

}

//================================================================GENERIC==============================================================================
// nhiều tham số tương tự
//Ngoài khai báo gì (T, R, E...) → con mặc định dùng lại được.
//Nếu con tự khai báo <T> → đó là T mới, độc lập.
//Muốn class con ăn theo generic của class cha → dùng inner class (thay vì nested class).
fun <T> identity(value: T): T {  // bắt bộc phải có <T> vì nó là top level
    return value
}

class box<T>(var content: T){  // các hàm hay class trong thì không cần thêm <T> vì nó ăn theo cha
    fun show(){
        print("$content")
    }
}

//Có thể viết fun <T> Int.xxx(): T?, nhưng nó không mang ý nghĩa thực tế, vì Int không có generic.
//Chỉ List<T>, Set<T>, Map<K,V>, hoặc class/interface generic mới thật sự cần generic extension.

fun <T> List<T>.secondOrNull(): T? {
    return if (this.size >= 2) this[1] else null
}


interface repo<T>{
    fun save(item : T)
    fun getAll() : List<T>
}

class userrepo : repo<String>{
    var mutable = mutableListOf<String>()
    override fun save(item: String) {
        mutable.add(item)

    }

    override fun getAll(): List<String> {
        return mutable
    }

}

sealed class Result<out T> {
    data class Success<T>(val data: T): Result<T>()
    data class Error(val message: String): Result<Nothing>()
    object Loading: Result<Nothing>()
}



//=======================================================================OOP============================================================================
//Trong Kotlin, mặc định class là final → không kế thừa được, muốn kế thừa đánh dấu open, abstract hoặc sealed
// class con sẽ có 100% thuộc tính thg cha
//,Function,Property (val/var) là final , phải để open mới override được
//Abstract = phải override (no body)
//Open = muốn thì override (open ở trước)
//Final = chịu, không override (final ở trước)
//Interface = không body thì abstract, có body thì open = non abstract
//Property val → có thể nâng lên var, var → chỉ giữ var
//Object = không override


// interface không có constructor, absstruct thì có
//Khi có logic + state chung → dùng abstract class
//Khi cần chia sẻ hành vi, đa kế thừa → dùng interface



abstract class A {
   abstract fun a()
}

class B : A() {
    override fun a() {

    }
}


class cut(){
    var e = 5
        get() = field
        set(value) {
             if (value >= 0) field = value
             else 0
        }
}

sealed class ss{

}

fun undercut(a : Int, b : (Int) -> (Int)): Int{
    return b(a)
}


fun pixelcut(){
    var an = { n: Int -> n*n }
    var c = undercut(5,an)
    println(c)
}

fun vut(xen: Int) : (Int) -> (Int){
    return { len -> len*xen}
}

fun poo(){
    val o = listOf(1,2,3)
    val d = o.filter { it % 2 == 0 }
    println(d)
}

// all

data class Song(
    val title: String,
    val artist: String,
    val duration: Int,
    val type: MediaType
)


enum class MediaType {
    AUDIO, VIDEO, IMAGE
}

sealed class MediaResult {
    data class Success(val songs: List<Song>) : MediaResult()
    data class Error(val message: String) : MediaResult()
    object Loading : MediaResult()
}

fun Song.format(): String = "🎵 $title - $artist (${duration}s)"

fun all(){
    val songs = listOf(
        Song("Hello", "Adele", 300, MediaType.AUDIO),
        Song("Rolling", "Adele", 250, MediaType.AUDIO),
        Song("Shape", "Ed Sheeran", 200, MediaType.VIDEO),
        Song("Sunflower", "Post Malone", 180, MediaType.AUDIO)
    )

    // 🔹 HOF: filter, sortedBy, map
    val adeleSongs = songs
        .filter { it.artist == "Adele" }       // lọc theo nghệ sĩ
        .sortedBy { it.duration }              // sắp xếp theo thời lượng
        .map { it.format() }                   // dùng extension function
    println("Adele songs: $adeleSongs\n")

    // 🔹 groupBy (HOF)
    val grouped = songs.groupBy { it.type }
    println("Group by type: $grouped\n")

    // 🔹 let (dùng với null-check)
    val cover: String? = null
    cover?.let { println("Load cover: $it") } // không crash nếu null

    // 🔹 apply (khởi tạo + cấu hình object)
    val songBuilder = StringBuilder().apply {
        append("Playlist:\n")
        songs.forEach { append("- ${it.title}\n") }
    }
    println(songBuilder.toString())

    // 🔹 run (thao tác + trả kết quả)
    val longest = songs.run { maxByOrNull { it.duration } }
    println("Longest song: ${longest?.format()}\n")

    // 🔹 with (nhiều thao tác trên object)
    val firstSong = songs.first()
    val description = with(firstSong) {
        "First song is $title by $artist"
    }
    println(description)

    // 🔹 also (side-effect: debug log)
    val newSongs = songs.toMutableList().also {
        println("Before add: $it")
    }.apply {
        add(Song("New Hit", "Unknown", 210, MediaType.AUDIO))
    }.also {
        println("After add: $it")
    }

    // 🔹 sealed class usage
    val result: MediaResult = if (newSongs.isNotEmpty()) {
        MediaResult.Success(newSongs)
    } else {
        MediaResult.Error("No songs found")
    }

    when (result) {
        is MediaResult.Success -> println("Loaded ${result.songs.size} songs")
        is MediaResult.Error -> println("Error: ${result.message}")
        MediaResult.Loading -> println("Loading...")
    }
}


class bov<T>(var content:T){
    fun getA(): String{
      return  "$content"
    }
}

fun <T> getx(comment :T): T{
    return comment
}

//Có thể viết fun <T> Int.xxx(): T?, nhưng nó không mang ý nghĩa thực tế, vì Int không có generic.
//Chỉ List<T>, Set<T>, Map<K,V>, hoặc class/interface generic mới thật sự cần generic extension.






fun main(){
    abc()
    set()
    map()
    stack()
    queue()
    hashmap()
    mutable()

    val a = box("ssss")
    a.show()

    println(identity(123))       // Int
    println(identity("Hello"))   // String
    println(identity(3.14))      // Double

    val list = listOf(1,2,3)
    println(list.secondOrNull()) // 2

    val repo = userrepo()
    repo.save("Alice")
    repo.save("Bob")
    println(repo.getAll()) // [Alice, Bob]

    val success: Result<String> = Result.Success("Loaded data")
    val error: Result<Int> = Result.Error("Network error")
    val loading: Result<Any> = Result.Loading
    println(success) // Success(data=Loaded data)
    println(error)   // Error(message=Network error)
    println(loading) // Loading


    val b = cut()
    b.e = -99
    println(b.e)
    pixelcut()
    val h = vut(5)
    println(h(2))
    poo()
    val name = "Kotlin"
    println(name::class)
    all()
    val n = listOf(1,2,3)
    val m = n
    print(m === n)



}