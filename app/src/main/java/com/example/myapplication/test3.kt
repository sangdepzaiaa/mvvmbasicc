package com.example.myapplication

fun abc(){
    var number1 = listOf(1,2,3,3)
    var number2 = number1
    print(number1[1])

// collect/object thì tham trị là tham chiếu, so sánh địa chỉ ô nhớ, nguyên thủy là tham trị giá trị, so sánh giá trị
    println(number1 == number2)
    println(number1 === number2)

    var num3 = mutableListOf(1,2,3)
    var num2 = num3
    num2.add(2)
    println(num3 == num2)
    println(num2 === num3)

    var set1 = setOf(1,2,3)
  //  print(set1[1])

    var map1 = mapOf(1 to 2, 9 to 3, 3 to 4)
    var map2 = map1
    print(map2 == map1)
    print(map2 === map1)
    print(map1[9])
}

abstract class Animal(val a: String){
    abstract fun move()
    fun getInfo() = println("Day là $a")
}

interface Fly{
    fun moven()
    fun fly() = println("Fly")
}

interface Swim {
    fun swim() = println("Swin")
    fun run()
}


class Dusk( a: String, val b: String): Animal(a), Fly, Swim{
    override fun move() {
        print("kapt kapt")
    }

    override fun moven() {
        print("szxz $a + $b")
    }

    override fun run() {
        print("dpsxpkk")
    }

}

class Dog(a : String) : Animal(a), Swim{
    override fun move() {
        print("dsad")
    }

    override fun run() {
      print("sáda")
    }

}

class Box<T>(var content:T){
    fun getContent(){
        print("$content")
    }
}

fun <T> getTitle(title:T):T{
     return title
}

class Info(val email: String?)
class Profile(val info: Info?)
class User(val profile: Profile?)

fun nullsafe(){
    val user1 = User(Profile(Info("hello@gmail.com")))
    val user2 = User(Profile(Info("")))
    val user3: User? = null

    val result1 = user1
        ?.profile
        ?.info
        ?.email
        ?.trim()
        ?.lowercase()
        ?.substringBefore("m") // lấy chuỗi trước m
        ?.takeIf { it.isNotEmpty() && it.first().isLetter() } //thỏa điều kiện sẽ lưu vào biến,java không có
        ?.takeUnless {  it.isNotEmpty() && it.first().isLetter() } //không thỏa điều kiện sẽ lưu vào biến,java không có
        ?: "no_user"

    print(result1)
}

class Constructor(val a: String,val b :Int){
    constructor(name: String): this(a = "",b = 1)
}

class lazyinit(){
    lateinit var a: String
    fun A(){
        print(a)
    }

    val b:Int? by lazy {
        null
    }

    fun B(){
        print(b)

    }
}

class abcd{
    var name:String = ""
        get()  {
            print("asd")
            return if (field.isNotEmpty()) "sangdz" else field.uppercase()
        }
        set(value){
            field = value + "sssssss"
            print("$field")
        }
}

enum class H{
    A,B,C,D

}

class Too(){
    fun result(h:H){
        when(h){
            H.A -> println("A")
            H.B -> println("B")
            H.C -> println("C")
            H.D -> println("D")
        }
    }
}

sealed class Result{
    data class getData(val title: String): Result()
    data class setData(val content: String): Result()
    data object Loading : Result()

}

class Roo(){
    fun resultSealedClass(result: Result){
        when(result){
           is Result.getData -> print("${result.title}")
           is Result.setData -> print("${result.content}")
           is Result.Loading -> print("loading... ")
            else -> "sss"
        }
    }
}



class Going(){
    var o: String = "jll"
    inner class H(){
       init {
           print("$o")
       }
    }
}

data class Userr(val name: String, val age: Int)

fun mainn() {
    val u1 = Userr("Sangk", 20)
    val u2 = Userr("Sangk", 20)
    val u3 = u2.copy() // tạo instance mới có cùng giá trị nhưng khác object trong bộ nhớ

    println(u1)          // User(name=Sang, age=20) tự sinh toString()
    println(u1 == u2)    // true ,vì so sánh giá trị ,tự sinh equal()
    println(u1.hashCode() == u2.hashCode()) // true, vì cùng 1 hashcode, tự sinh hashCode()

    val (n, a) = u1  // destructor, tự sinh destructor()
    println("$n - $a")   // Sang - 20

    print(u2 == u3)
    print(u2 === u3 )
}

//Lambda là biến nên truyền vô được hof, còn fun chỉ là định nghĩa, phải :: để biến nó thành một giá trị (biến hàm) mới truyền được.
fun highFun( a:Int,  b : (Int) -> Int ): Int{
    return b(a)
}

fun double(x:Int):Int{
    return x*x*2
}

fun String.toStringger(): String{
    return "$this have done"
}

class Extvsfun(){
    fun toStringgered(){
        print("sss")
    }
}
fun Extvsfun.toStringgered(){
    print("aaa")
}

class so{
    companion object{

    }
    var an = arrayOf("s","d","d")



}

object Do{

}

interface A{
    fun face()
}

fun Hp(){
    var a = object : A{
        override fun face() {
            println("sss")
        }
    }
    a.face()
}

object Config{
    val api by lazy {
        30
    }
}

//lazy → singleton duy nhất cho 1 property (biến), khởi tạo khi dùng đến
//object → singleton cho toàn bộ mọi thứ trong object, khởi tạo ngay từ đầu
//object + lazy -> khởi tạo object từ đầu, biến khởi tạo khi cần

data class Person(var name: String,var age: Int)

fun scopeFun(){
    var person = Person("sang",18)
    person.let { // let trả về giá trị của biểu thức cuoi cùng,Truy cập object bằng it,Xử lý nullable
        println("person exit")
        it
    }.run {  // run trả về giá trị của biểu thức cuoi cùng,Truy cập property trực tiếp (this),Viết gọn, rõ ràng khi trong class
        name = "ff"
        age = age + 9
        this
    }.also {  //  also trả về object, dùng để log,..
        println("${it.name} + ${it.age}")
        it
    }.apply {  // apply trả về object, thay đổi giá trị thuộc tính object
        name = "ssss"
        age = age + 99
        this
    }.also {
        println("${it.name} + ${it.age}")
        it
    }.also { p ->
        with(p){ //with trả về giá trị của biểu thức cuoi cùng, phải truyền object vaoif
            age = age +8888
            this
        }
        println(p)
        p
    }
}
// các scope đều được nối nhau, kết quả hàm trước là đầu vào hàm sau, với đầu vào lucs đầu là person
//hàm with không phải extension function,không thể gọi nó bằng cú pháp person.with { ... }
//nó không được định nghĩa trên đối tượng person mà là một hàm độc lập nhận person làm tham số.
// nên cho vào also,let,apply,run
// let, also nhận it , run,apply,with nhận this
// apply,also tra về object ban đầu println: vẫn trả về object
// let,run,with trả về giá trị biểu thức cuối cùng, println có thể trả về unit
//

fun main(){
   // abc()

    var dog = Dog("Dog")
    var dusk = Dusk("Dusk", "power")
    dusk.getInfo()
    dusk.moven()

    var box = Box("ss")
    var box1 = Box(2)
    box.getContent()
    box1.getContent()

    print(getTitle("ff"))
    print(getTitle(22))

    nullsafe()

    val con1 = Constructor("",2)
    val con2 = Constructor("")

    var an = lazyinit()
    an.a = "dd"
    an.A()
    an.B()

    var abcd = abcd()
    print(abcd.name)
    abcd.name = "dd"

    var too = Too()
    too.result(H.A)

    var roo = Roo()
    roo.resultSealedClass(Result.getData("sss").copy("ssssss"))

    var g = Going()
    var o = g.H()

    mainn()



    var name = "sang"
    println(name.toStringger())

    var a = Extvsfun()
    a.toStringgered()

    Hp()

    scopeFun()

    //Được viết tắt khi lambda nằm cuối danh sách tham số
    //và số tham số trong lambda ≤ 1 (để dùng it).
    // nhiều tham số dùng nhiều biến,không tham số bỏ luoonh ngoặc tròn
    var result = {x:Int ->  x*x}
    println(highFun(2,result))
    println(highFun(2,::double))


}




