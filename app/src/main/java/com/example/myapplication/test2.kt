package com.example.myapplication

import android.widget.ImageView
import org.koin.dsl.module

fun abc(){
    var a = listOf(1,2,3)
    var l = a
    println(a == l)
    println(a === l )

    var d = mutableListOf(1,2,3)
    var e = d
    e.add(2)
    println(d == e)
    println(d === e )

    var b = 5
    var c = b
    c += 3
    println(c == b)
    println(c === b)
    // với object truyền tham tri = tham chiếu
}

abstract class Animal(val name: String){
    abstract fun eat()
    fun sound() = println("$name is ddddddddd")
}

interface Fly{
    fun fly() = println("fly")
    fun move()
}

interface Swim{
    fun swim() = println("swim")
    fun display()
}

class Dog(name: String) : Animal(name), Fly{
    override fun eat() = println("$name is dog")
    override fun move() = println("$name is move")
}

class Cat(name: String): Animal(name), Swim{
    override fun eat() = println("$name is cat")
    override fun display() = println("$name is display")
}

class Dusk(name: String): Animal(name), Fly, Swim{
    override fun eat() {
        TODO("Not yet implemented")
    }

    override fun move() {
        TODO("Not yet implemented")
    }

    override fun display() {
        TODO("Not yet implemented")
    }

}

//   các class con kế thừa nên có hết các biến của cha, và có thể mở rộng biến

class Dollar(val a:Int, val b: Int){
    constructor():this(a = 2, b =3)
}

class Box<T>(var content:T){
    fun lay() {
       println("$content is done")
    }
}

fun <T> goo( title:T):T {
    return title
}
class Foo(){
    var id :Int =9
        get() = field
        set(value) { field = value }
}

enum class Too{
    A,B,C
}

fun poo(too: Too){
    when(too){
        Too.A -> println("A")
        Too.B -> println("B")
        Too.C -> println("C")
    }
}

sealed class Doo{
    data class Collection(
        val id:Int
    ): Doo()
}

sealed class Result {
    data class Success(val data: String): Result()
    data class Error(val message: String): Result()
    object Loading: Result()
}

fun handle(result: Result) {
    when (result) {
        is Result.Success -> println("Thành công: ${result.data}")
        is Result.Error -> println("Lỗi: ${result.message}")
        Result.Loading -> println("Đang tải...")
    }
}

class A{
    var h :Int = 9
    inner class B{
        fun hoo(){
            println("$h dsdd")
        }
    }
}

fun String.toStringg():String{
    return "$this is me"
}

fun loo(a:Int, b: (Int) -> Int):Int{
    return b(a)
}

data class User(
    val id:Int,
    val title: String
)

data class Person(var name: String, var age: Int)

fun mai() {
    // Tạo một đối tượng Person
    val person = Person("Alice", 25)

    // 1. let: Truy cập đối tượng bằng 'it', thường dùng để xử lý nullable hoặc trả về kết quả
    val letResult = person.let {
        println("let: Name is ${it.name}")
        it.age + 5 // Trả về kết quả cuối cùng
    }
    println("letResult: $letResult") // Output: 30

    // 2. run: Truy cập đối tượng bằng 'this', thường dùng để thực hiện một khối lệnh và trả về kết quả
    val runResult = person.run {
        name = "Bob" // Sửa đổi thuộc tính
        age + 10 // Trả về kết quả cuối cùng
    }
    println("runResult: $runResult") // Output: 35
    println("Person after run: $person") // Output: Person(name=Bob, age=25)

    // 3. with: Truy cập đối tượng bằng 'this', không cần trả về giá trị, thường dùng để cấu hình
    with(person) {
        name = "Charlie"
        age = 30
        println("with: Updated person to $name, $age")
    }
    println("Person after with: $person") // Output: Person(name=Charlie, age=30)

    // 4. apply: Truy cập đối tượng bằng 'this', trả về chính đối tượng, dùng để khởi tạo hoặc cấu hình
    val applyResult = person.apply {
        name = "David"
        age = 35
    }
    println("applyResult: $applyResult") // Output: Person(name=David, age=35)
    println("Person after apply: $person") // Output: Person(name=David, age=35)

    // 5. also: Truy cập đối tượng bằng 'it', trả về chính đối tượng, dùng để thực hiện các tác vụ phụ
    val alsoResult = person.also {
        println("also: Logging person with name ${it.name} and age ${it.age}")
    }
    println("alsoResult: $alsoResult") // Output: Person(name=David, age=35)
}

fun main(){
   // abc()
    val user = User(1,"ss").run {

    }
    var result = { x: Int -> x*10 }
    println(loo(5,result))

    val name = "toooo"
    println(name.toStringg())

   val dog = Dog("Dog")
   val cat = Cat("Cat")
   val dusk = Dusk("Dusk")
    val foo = Foo()

   val dollar = Dollar()
   val dollar2 = Dollar(1,2)

   val box = Box("string")
    box.lay()

    goo("string is fun")
    foo.id  = 99
    println(foo.id)

    poo(too = Too.A)

    handle(Result.Success("Dữ liệu nè"))
    handle(Result.Error("Không kết nối được"))

}