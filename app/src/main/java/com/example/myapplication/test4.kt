package com.example.myapplication

import kotlin.inc
import kotlin.text.uppercase

fun abck(){
    val l = listOf(1,2,3)
    var m = mutableListOf(1,2,3)
    val l1 = setOf(1,2,3)
    var m1 = mutableSetOf(1,2,3)
    var l2 = mapOf(1 to 2, 2 to 3, 3 to 4)
    var m2 = mutableMapOf(1 to 2, 2 to 3, 3 to 4)

    var n = l
    var k = m
    m.add(4)
    m2[4] = 4
    println(m2)
    println(k == m)
    println(k === m)
    println(l[1])
    println(m[1])
  //  println(l1[2])
    println(l2[0]) // == null
    println(l2[2])

}

abstract class Animallo(private val name : String){

     val age:Int?=null

    protected var an:Int =6
   abstract fun move()
    fun eat(namee: String){
    println("$namee is $name")
    }
}

interface Flyable2{
    fun fly()
}

interface Swimable2{
    fun swim()
}

class Dogg(name:String): Animallo(name), Swimable2, Flyable2{

    override fun move() {
        println("$age")
    }

    override fun swim() {
        println("dog has swimmed ")
    }

    override fun fly() {
        TODO("Not yet implemented")
    }

}

class Boxl<T>(var content:T){
    fun getContentt():T{
        return content
    }
}

fun <T> mother(content:T):T{
    return content
    // hàm không trả về gì mặc định println ra có kotlin.unit
}

data class Address3(val address3: String?)
data class Info3(val address3: Address3?)
data class Person3(val info3: Info3?, val address2: Address3?)

 var  person3 = Person3(Info3((Address3("sss"))),Address3("ss"))

var person4 = person3
    ?.info3
    ?.address3
    ?.address3
    ?.trim()
    ?.uppercase()
    ?.substringBefore("s")
    ?.takeIf { it.length > 2 }
    ?.let { str ->  // Tạo biến str
        println("LET: $str")
        str.substringBefore("a")
    }
    ?.run {
        val len = this.length
        println("RUN: length = $len")
        len
    }
    ?.apply {

        println("APPLY: vẫn giữ nguyên giá trị $this")
    }
    ?.also { value ->
        println("ALSO: debug, giá trị cuối = $value")
    }
    ?.also { with(person3){
        println("ALSO with person3: ${this?.info3?.address3?.address3}")
    } }
    ?: "default value"

data class Pro(var name: String, var age: Int)

var pro = Pro("ss",20)

var pro2  = pro.let {
    println("ssss $it")
}

var pro1 = pro.apply { age++ }
    .also { println("result: $it") }
    .run { "$name and $age" }
    .let { it.uppercase() }
    .also { println("result end: $it") }

data class User4(val info:String,val property:Int){
    constructor(info2: String): this("ss",2)
    constructor():this("",2)
}

lateinit var a : String
val b by lazy {
    null
}
fun ll(){
    a = "f"
    a= "ss"
    println(a)
    println(b)

}

class sod{
    var at:Int = 8
        get() = field*99
        set(value) {
            field = value*field
        }
}

enum class EnumClass{
    A,B,C

}

fun EnumClass2(x : EnumClass){
    when(x){
        EnumClass.A -> println("This is A")
        EnumClass.B -> println("This is B")
        EnumClass.C -> println("This is C")
    }

}

sealed class SealedClass{
    data class getData(var content: String): SealedClass()
    data class setData(var title: Int): SealedClass()
    object Loading: SealedClass()

}

fun SealedClass2(x: SealedClass){
    when(x){
        is SealedClass.getData -> println("${x.content}")
        is SealedClass.setData -> println("${x.title}")
        is SealedClass.Loading -> println("Loading...")
    }
}

//class AA{
//    var amk = 5
//    inner class BB{
//        init {
//            println("$amk")
//        }
//    }
//}

fun hofun(a:Int, b: (Int) -> Int):Int{
    return b(a)
}

data class sssa(val a:Int,val b:Int)

class Tooo{
    fun transformString():String{
        return " is transformed jjjj"
    }
}
fun Tooo.transformString():String{
    return "$this is transformed"
}

object DialogHelper{
    var a = 0
    fun inscreaseA(){
        a++
        println("$a")
    }
}

class DialogHelper2(){
    var b = 0
    fun inscreaseB(){
        b++
        println("$b")

    }
}


fun main(){
    abck()
    println("-------------------")
    var dog = Dogg("dog")
    dog.eat("ss")
    dog.swim()
    println("------------------")
    var box2 = Boxl("hello")
    println(box2.getContentt())
    var box3 = Boxl(45)
    println(box3.getContentt())
    println("------------------")
    println(mother("sss"))
    println(mother(33))
    println("------------------")

    println("------------------")
    var user4 = User4("info",55)
    var user5 = User4("info2")
    var user6 = User4()
    println(user4)
    println(user5)
    println(user6)
    println("------------------")
    ll()
    println("------------------")
    var soddd = sod()
    println(soddd.at)
    soddd.at = 3
    println(soddd.at)
    println("------------------")
    EnumClass2(EnumClass.B)
    SealedClass2(SealedClass.getData("jj").copy("sss"))
    SealedClass2(SealedClass.setData(55).copy(99))
    println("------------------")
    var a = sssa(5,10)
    var b = a.copy()
    println(a.equals(b))
    var (m,n) = sssa(7,3)
    println("${a.a} and ${a.b}")
    println("$m and $n")

    println(a == b)
    println(a === b)

    val c = {x:Int -> x*x}
    println(hofun(4){it*it})
    println(hofun(3,c))
    println("------------------")
    var tooo = Tooo()
    println(tooo.transformString())

    println("------------------")
    val a1 = DialogHelper
    val b1 = DialogHelper
    val c1 = DialogHelper
    a1.inscreaseA()
    b1.inscreaseA()
    c1.inscreaseA()
    println(a1 === b1 && b1 === c1)

    var a2 =  DialogHelper2()
    var b2 =  DialogHelper2()
    var c2 =  DialogHelper2()
    a2.inscreaseB()
    b2.inscreaseB()
    c2.inscreaseB()
    println(a2 === a2 && b2 === c2)

    println("------------------")
    println(person4)
    println("------------------")
    println(pro1)
    println(pro2)

}






