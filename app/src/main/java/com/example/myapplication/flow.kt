package com.example.myapplication

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking

// 1️⃣ Bản chất Flow
//Flow là một khối code phát ra dữ liệu (stream of values).
//Dữ liệu được phát ra một cách tuần tự.
//Flow là cold (không chạy gì cho đến khi có collector).
//Khi collector gọi .collect { ... }, Flow mới bắt đầu emit giá trị.
//⚙️ 2️⃣ Cơ chế tuần tự (Sequential)
//emit(1) → collector nhận 1 → emit(2) → collector nhận 2 → ...
//Điều quan trọng:
//Emit “chờ” collect xử lý xong trước khi emit tiếp.
//Flow đảm bảo thứ tự: các giá trị được collect theo đúng thứ tự phát ra.
//Nếu collect chậm, emit sẽ tạm dừng cho đến khi collector xử lý xong.
fun main() = runBlocking {
//    var a = flow {
//        emit(1)
//        emit(2)
//        emit(3)
//        emit(4)
//        emit(5)
//        emit(6)
//    }.flowOn(Dispatchers.IO).filter { it % 2 == 0 }  // chỉ còn 2
//        .collect { println(it) }  // chỉ nhận 2

//    var b = flow {
//        for (i in 1..5) {
//            println("Emit $i")
//            emit(i)
//        }
//    }.flowOn(Dispatchers.IO).buffer() // tạo buffer emit hết vào buffer và không qua collect, emit xong vào buffer rồi collect mới nhận
//        .collect { value ->
//            delay(500) // collector chậm
//            println("Collect $value")
//        }

    var c = flow {
        for (i in 1..5) {
            println("Emit $i")
            emit(i)
        }
    }.flowOn(Dispatchers.IO).conflate() //fowOn: chuyển qua thread khác,conflate() :nếu collector quá chậm, chỉ giữ giá trị mới nhất
        //Thứ tự giá trị có thể thay đổi nếu dùng conflate().
        .collect { value ->
            delay(500)
            println("Collect $value")
        }

}