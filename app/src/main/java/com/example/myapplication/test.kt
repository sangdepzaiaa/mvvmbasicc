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
    val mutable = mutableListOf(2,2,2)
    val l = mutable
    l.add(3)
    println(l)
}

class box<T>(var content: T){
    fun show(){
        print("$content")
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
}