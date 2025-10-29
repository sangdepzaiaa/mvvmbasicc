package com.example.myapplication

class Animal23(var co:String){
    var a11 = Animal23("A")
    var b11 = Animal23("B")
    var c11 = Animal23("C")
}   //đệ quy voo hạn khi tạo instance ở chỗ khaác => đặt chúng trong compani object
// dùng when thì không phải khai báo hết case

//enum class Animal1223(var co:String){
//  var a =  Animal1223("A")
//  var b =  Animal1223("B")
//    var c = Animal1223("C")
//}  sai vì enum được thiết kế là các giá trị con instance cố định , viết như này là tạo thêm instanse mới, và enum
// không cho tạo constructor trong enum
// mục đích sinh ra: Tập hợp giá trị cố định, phẳng
// dùng when thì phải khai báo hết case

//sealed class Animal23(var co: String) {
//    var a11 = Animal23("A")
//    var b11 = Animal23("B")
//    var c11 = Animal23("C")
//} //sealed class không thể tự tạo instance trực tiếp, chỉ tạo instance thông qua lớp con kế thừa
// mục đích sinh ra: Tập hợp kiểu cố định có cấu trúc
// dùng when thì phải khai báo hết case

//data class User44(val name:String,val age:Int){
//    var a233 = User44("3",3)
//}





class AA(var doi:Int){
    fun printdoi(){
        println("$doi")
    }


}

enum class FF(var content2:String){
    AAAA("a"),
    BBBB("b"),
    CCCC("c");

}
fun printanimal(co: Animal23){
    when(co){

    }
}

enum class Animal223(var co:String){
    A("A"),
    B("B"),
    C("C");
}
// con của enum ăn theo cha, cha có thuộc tính => con cũng có
sealed class Animal235 {
    data class Dog(val age: Int) : Animal235()
    data class Cat(val color: String) : Animal235()
    data object Cow : Animal235()
}

//sealed thì con muốn có cái gì cũng được

fun abcdef(dir: Animal235){
    when( dir){
        is Animal235.Dog -> println(2)
        is Animal235.Cat -> println("Cat")
        Animal235.Cow -> println("Cow")
    }
}

data class User44(val name:String,val age:Int)

fun hhoo(a12: Int, b12: (Int) -> Int):Int{
    return b12(a12)
}

fun hhoo1(a12:Int):Int{
    return a12*a12
}

fun hhoo2(a12:Int):Int{
    return a12*a12*a12*a12
}

fun String.converttostring(str : String): String{
    return "$this + sdas"
}

object  Heee{
    var a = 9

}

class Hooo{
    var b = 9

}

fun main(){
    var a33 = User44("ss",99)
    println(a33)
    println("----------------------")

    var a1 = User44("ss",7)
    var a2  = User44("ks",78)
    var a3 = a2.copy()
    var (name, age) = a3
    println(a1 == a2)
    println(a2 == a3)
    println(a1)
    println(name)
    a3.equals(a2)
    println(a3.equals(a2))
    println("----------------------")
    var a4 = {x:Int -> x*x}
    var a5 = {x:Int -> x*x*x*x}
    println(hhoo(3,a4))
    println("sss".converttostring("ssa"))

    var aa12 = Heee
    var aa13 = Heee

    aa12.a += 1
    aa13.a += 2
    println(aa12.a)
    println(aa13.a)

    var ho1 = Hooo()
    var ho2 = Hooo()

    ho1.b += 1
    ho2.b += 2
    println(ho1.b)
    println(ho2.b)


}