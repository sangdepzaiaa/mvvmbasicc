package com.example.myapplication

abstract class Animall(var namee: String){
    abstract fun abc()
    fun move( age:Int) = println("ABC $namee + $age")

}

interface FLyy{
    fun fly()
}

interface Swimm{
    fun swim()
}

data class Country(var namee: String)
data class Hight(var hight: String,var country: Country)

class Catt(private val name: String) : Animall(name), Swimm, FLyy {
    override fun abc() {

    }

    override fun swim() {

    }

    override fun fly() {

    }
}

fun doo(){
    val l = listOf(1,2,3)
    var m = mutableListOf(1,2,3)
    var n = l
    println(l == n)
    println(l === n)
}

class Boxx<T>(var content: T){
    fun  aov(title:Int){
        println("$content + $title")
    }
}

fun <T> parent( des:T) {
    println(des)
}

data class Person2(var name2: String, var age2:Int)

fun persion(){
    var person2 = Person2("ss",2)
    println(person2)
    person2.let {
        println("persion2 exit")
        it
    }.run {
        age2 = age2 + 6
        this
    }.also {
        println(it.age2)
    }.apply {
        age2 = age2 + 6
        this
    }.also {
        println(it.age2)
    }.also { p ->
        with(p){
            name2 = "dz"
            age2 = age2 + 9999
        }
        println(p)
        p
    }
}

class Address(var districk: String?)

class User3(var name3: String?, var age3 : Int?, var address: Address? )

class Profile2(var address: Address?,var user3: User3?, var id:Int?)

class nullable() {

    var profile = Profile2(Address("ss"), User3("ss", 3, Address("dd")), 7)
    var result2 = profile
        ?.user3
        ?.address
        ?.districk
        ?.substringBefore("s")
        ?.uppercase()
        ?.trim()
        ?.takeIf { it.isNotEmpty() }
        ?.takeIf { it.length > 0 }
        ?: "ss"

}

class Voidd(var content:String, val des: String){
    init {
        println("$content + $des")
    }
    constructor( conten: String):this("",""){
        println(conten)
    }
    constructor():this("",""){
        println("không có gì")
    }

    lateinit var b: String
    val c by lazy {

    }

        var low = "name"
        get() = field + "gg"
        set(value) {
            field = value + "ss"
            println(field)
        }

}

enum class L{
    A,B,C
}

fun lpo(l: L){
    when(l){
        L.A -> println("A")
        L.B -> println("B")
        L.C -> println("C")
    }
    println(lpo(L.A))
}

sealed class Result2{
    data class getData2(var content2: String): Result2()
    data class setData2(var title2:String): Result2()
    data object Loading: Result2()
}

class Hoo(){
    fun lip(result2: Result2){
        when(result2){
            is Result2.getData2 -> println("${result2.content2}")
            is Result2.setData2 -> println("${result2.title2}")
            is Result2.Loading -> println("loading...")
            else -> "ss"
        }
    }
}

fun huo(a:Int, b:(Int) -> Int):Int{
    return b(a)
}
//Lambda là biến nên truyền vô được, còn fun chỉ là định nghĩa, phải :: để biến nó thành một giá trị (biến hàm) mới truyền được.

class GHJ {
    fun isPlay(): String {
        return "ff"
    }
}

fun GHJ.isPlay() : String{
    return "pp"
}





fun main(){
    var catt = Catt("String")
    catt.move(4)

    doo()

    var boxx = Boxx("String")
    var boxx2 = Boxx(2)
    boxx.aov(3)
    boxx2.aov(3)

    parent(22)
    parent("dd")

    persion()

    var nullable = nullable()

    var void = Voidd("1","2")
    var  void2 = Voidd("3")
    var void3 = Voidd()

    println(void.low)
    void.low = "kkkkk"

    var hoo = Hoo()
    hoo.lip(Result2.getData2("ss").copy(content2 = "ss99"))

    var result = {x:Int -> x*x}
    println(huo(4,result))

    var ghj = GHJ()
    println(ghj.isPlay())


}






