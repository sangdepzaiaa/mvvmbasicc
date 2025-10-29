package com.example.myapplication

interface Demo{
    val a: Int




    fun getData2(a:String){
        println("$a + sss")
    }
}

abstract class Test{
    open lateinit var s:String
    abstract fun post()

    open fun getData(a:String = ""){
        println("$a + sss")
    }
}

open class Test2: Test(), Demo{
    override fun post() {

    }

    override fun getData(a: String) {
        super.getData(a)
    }

    fun getData(b:String, c:String): String{
        return "á"
    }




    override val a: Int
        get() = TODO("Not yet implemented")

    override fun getData2(a: String) {
        super.getData2(a)
    }


}

sealed class Statee(var a123: Boolean){
    data class Success(var a12:Int) : Statee(true)
    data class Error(var error: String):Statee(false)
}

enum class Enumclasss(var e:String){
    A("ss"),B("sss"),C("sdf"),D("sdf");

    fun setData(){
        println("sdsa")
    }
}

class Big{
    var a33 :Int = 5

    class Small{
        fun printNest(){
            println(a)
        }
    }

    inner class Normal{

        fun print(){
            println(a33)
        }
    }

}

fun objectBig(){
    var a123 = Big()
    var a1234 = a123.Normal()
    val a12345 = Big.Small()
}


class Big2{
    var am : Int = 9

    class Small2{
        fun prine(){
            println(am)
        }
    }
}

