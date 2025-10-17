package com.example.myapplication

//Thread {
//    downloadImage()
//}.start()  .
//
//Thread {
//    playMusic()
//}.start() chạy trong backgroud thread vì .start() không chặn Ui thread

//Hai thread này cùng chạy — vừa tải ảnh, vừa nghe nhạc, không chờ nhau.

//println("Bắt đầu tải dữ liệu...")
//Thread {
//    Thread.sleep(3000)
//    println("Tải xong dữ liệu!")
//}.start()
//println("UI vẫn hoạt động!")
//
//Bắt đầu tải dữ liệu...
//UI vẫn hoạt động!
//Tải xong dữ liệu!

//Thread {
//    println("Background")
//}.run() // ❌ chạy trong main thread vì .run()

//Thread.sleep(3000) : chạy trên main thred

//Trong app Android hoặc app GUI nói chung, UI chỉ chạy trên 1 thread chính (Main/UI thread).
//Nếu bạn làm việc nặng trong đó (vd: đọc file lớn, gọi API), giao diện sẽ bị “đơ”.
//Giải pháp: chạy việc nặng trong thread phụ (background thread) để UI mượt:

//Thread {
//    val data = getDataFromNetwork()
//    runOnUiThread {
//        textView.text = data
//    }
//}.start()

//coroutine :1 cái giả vờ như luồng (luồng logic nhẹ ) (lightweight thread),không phải là thread,chạy bên trong thread,
//gọi là lightweight thread (luồng nhẹ) vì nó hoạt động giống thread nhưng không tạo thread thật.
// Có thể chạy trong 1 thread thật, tạm dừng / resume linh hoạt
//Thread { ... }.start() Tạo 1 thread thật của hệ điều hành, chạy ở Thread vật lý riêng
//Vấn đề của Thread { }.start()
//Nó đơn giản, nhưng có nhiều hạn chế lớn:
//❌ a. Tốn tài nguyên
//Mỗi Thread thật chiếm vài MB RAM.
//Nếu bạn tạo hàng trăm thread → app lag hoặc crash.
//❌ b. Không dừng / resume được
//Thread chạy một lèo đến hết.
//Không thể “tạm dừng rồi chạy lại” như coroutine.
//❌ c. Khó quản lý vòng đời
//Nếu user thoát Activity, thread vẫn chạy → rò rỉ bộ nhớ (memory leak) hoặc crash khi thread cố cập nhật UI đã bị hủy.

//Thread {
//    val data = loadFromNetwork()
//    runOnUiThread { textView.text = data }
//}.start()
//
//Nếu user thoát Activity giữa chừng,
//thread vẫn chạy → sau đó runOnUiThread gọi lên textView đã null → crash (NullPointerException).
//➡️ Coroutine (với lifecycleScope) tự hủy khi Activity bị hủy.

//Không thể tạm dừng, resume
//Thread chạy một lèo đến hết.
//Coroutine thì có thể:
//delay(3000)
//→ dừng logic 3s, mà không block thread thật.
//Thread vẫn rảnh để làm việc khác.

//Coroutine có thể dừng nhẹ nhàng bất kỳ lúc nào, ví dụ:
//
//val job = lifecycleScope.launch {
//    repeat(10) {
//        delay(1000)
//        println("Coroutine chạy: $it")
//    }
//}
//// Khi cần dừng
//job.cancel()
//→ Coroutine ngừng ngay, an toàn, không crash, không cần flag, không cần interrupt.

//Thread { }.start() không dừng được giữa chừng trừ khi bạn tự cài cơ chế dừng (flag hoặc interrupt).
//Coroutine thì có dừng được tự nhiên — đó là lý do người ta chuộng coroutine hơn.

//❌ d. Không có cơ chế switch thread dễ dàng
//Thread {
//    val data = loadFromNetwork() // chạy ở background
//    runOnUiThread { textView.text = data } // phải tự quay về UI
//}.start()
//→ Viết rườm rà, dễ sai.

//Coroutine giải quyết tất cả
//a. Nhẹ & siêu nhanh
//1 process có thể chạy hàng nghìn coroutine mà không lag.
//Tài nguyên nhỏ hơn thread hàng trăm lần.
//b. Tạm dừng / resume dễ dàng
//Coroutine có thể “chờ” (delay, await) mà không chặn thread.
//Thread.sleep() chặn thật, còn delay() chỉ tạm dừng coroutine logic.
//c. Tự quản lý vòng đời
//Dùng lifecycleScope (Activity/Fragment) hoặc viewModelScope (ViewModel) → tự hủy coroutine khi UI bị hủy → không leak, không crash.
//d. Dễ chuyển luồng
//Chỉ 1 dòng là xong:
//lifecycleScope.launch {
//    val data = withContext(Dispatchers.IO) {
//        loadFromNetwork() // chạy background
//    }
//    textView.text = data // tự quay về Main thread
//}→ Rõ ràng, gọn, an toàn, hiện đại hơn rất nhiều.

//So sánh nhanh
//Tiêu chí	        Thread { }.start()	                 Coroutine
//Tạo luồng	        Thread thật	                         Luồng logic (nhẹ)
//Chuyển luồng	    Thủ công (runOnUiThread)	         Dễ dàng (withContext)
//Tạm dừng	        Không có	                         Có (delay, await)
//Quản lý vòng đời	Khó, dễ leak	                     Tự động (lifecycleScope)
//Hiệu năng	        Nặng (1 thread = vài MB)	         Nhẹ (100k coroutine vẫn ok)
//Viết code	        Dài, rườm	Gọn,                     trong sáng

//Nhiều suspend function trong 1 coroutine có thể song song không?
//Ví dụ KHÔNG song song (tuần tự):
//lifecycleScope.launch {
//    getUser()     // suspend 1
//    getPosts()    // suspend 2
//}
//Cái này tuần tự, getPosts() chỉ chạy khi getUser() hoàn tất.
//
//
//Nếu bạn muốn nhiều suspend chạy song song trong cùng coroutine,
//bạn phải tạo coroutine con bằng async.
//lifecycleScope.launch {
//    val a = async { getUser() }
//    val b = async { getPosts() }
//    a.await()
//    b.await()
//}
//Ở đây getUser() và getPosts() đều là suspend fun,
//nhưng chúng được “bọc” trong 2 coroutine con khác nhau → chạy song song logic.

//Nhiều coroutine con có thể chạy song song trong 1 coroutine cha không?
////Khi bạn gọi nhiều async {} hoặc launch {} trong cùng coroutine cha,
////chúng đều có thể chạy song song (tuỳ dispatcher).
////
////lifecycleScope.launch {
////    launch { getUser() }   // coroutine con 1
////    launch { getPosts() }  // coroutine con 2
////}
////
////→ getUser() và getPosts() chạy song song logic,
////→ có thể thực sự song song vật lý nếu dispatcher dùng nhiều thread.
////
////Nhiều coroutine cha có thể chạy song song trong cùng một thread IO không?
////
////✅ CÓ! — và đây là điểm đặc biệt của coroutine 😎
////
////Dispatchers.IO là thread pool có thể tái sử dụng thread,
////nên nhiều coroutine (cha hoặc con) có thể luân phiên chạy trên cùng một thread thật, nhờ cơ chế suspend / resume.

//repeat(3) {
//    GlobalScope.launch(Dispatchers.IO) {
//        println("Start ${Thread.currentThread().name}")
//        delay(1000)
//        println("End ${Thread.currentThread().name}")
//    }
//}
//
//Start DefaultDispatcher-worker-1
//Start DefaultDispatcher-worker-1
//Start DefaultDispatcher-worker-1
//End DefaultDispatcher-worker-1
//End DefaultDispatcher-worker-1
//End DefaultDispatcher-worker-1
//
//a coroutine chia sẻ cùng một thread thật
//(vì khi delay() xảy ra, coroutine tạm dừng → thread rảnh cho coroutine khác).

//Tóm gọn các cấp song song
//Cấp độ	                              Có thể song song không?	                     Khi nào?
//Nhiều suspend trong 1 coroutine	      ❌ Không	                                     Chạy tuần tự trừ khi dùng async
//Nhiều coroutine con trong 1 cha	      ✅ Có	                                         Dùng nhiều launch hoặc async
//Nhiều coroutine cha (khác nhau)	      ✅ Có	                                         Dùng nhiều launch độc lập
//Nhiều coroutine trên 1 thread IO	      ✅ Có	                                         Coroutine luân phiên nhau nhờ suspend/resume

//Song song logic vs vật lý
//Loại song song	    Nghĩa	                                                      Ví dụ
//Logic concurrency	Nhiều coroutine chạy “cùng lúc” (nhưng chia sẻ thread)	      Coroutine 1 delay → Coroutine 2 chạy
//Vật lý (parallel)	Nhiều coroutine thực sự chạy cùng lúc trên nhiều CPU core	  Dispatchers.IO hoặc Dispatchers.Default có nhiều thread

//Kết luận siêu ngắn gọn
//Câu hỏi	                                                  Trả lời
//Nhiều suspend chạy song song trong 1 coroutine con?	      ❌ Không, trừ khi bạn async mỗi cái
//Nhiều coroutine con song song trong 1 cha?	              ✅ Có, bằng launch/async
//Nhiều coroutine cha song song trong 1 thread IO?	          ✅ Có, coroutine luân phiên suspend/resume
//Tất cả dùng cùng thread?	                                  Có thể, nếu dispatcher phân phối vậy
//Muốn “thật sự song song vật lý”?	                          Dùng Dispatchers.IO hoặc Dispatchers.Default có nhiều thread

//Tóm tắt “chân lý coroutine”:
//🧵 Một coroutine = 1 luồng logic, chỉ chạy 1 suspend tại 1 thời điểm.
//⚡ Muốn song song, phải tách suspend ra nhiều coroutine (bằng async hoặc launch).
//🧠 Nhiều coroutine có thể cùng chạy trên 1 thread thật, nhờ suspend/resume,
//hoặc chạy song song vật lý nếu có nhiều thread (IO, Default).

//🧵 Thread = đường ray thật
//⚙️ Coroutine = tàu chạy trên đường ray đó, biết dừng ở ga, chuyển sang đường ray khác, tiếp tục đi
//⛓️ Code bình thường = tàu chạy 1 lèo, không dừng, chiếm luôn đường ray cho đến khi xong.
// corroutine cũng là khối code binhh thương nhưng có dừng, chạy song song, nhường nhau chạy

//Thread không phải là “tập hợp khối code”,
//mà là một luồng thực thi (execution flow) ,chạy các khối code đó
//hread là 1 khu vực chạy tưng khối code 1
//thread cũng giôống corotine , muốn chạy xong xong thì phải tạo nhiều thread, mỗi thread chạy 1 cái
//Thread { taskA() }.start()
//Thread { taskB() }.start()

//còn
//Thread {
//    doTaskA()
//    doTaskB()
//}.start()
//        là chạy tuần tự

//So sánh	                                        Thread	                                      Coroutine
//Là gì	                                        Khu vực (luồng) CPU thực thi code	          Khối code logic có thể tạm dừng/tiếp tục
//Số code chạy cùng lúc trong 1 thread	        1 khối duy nhất	                              Nhiều coroutine có thể luân phiên nhau trên 1 thread
//Cách hoạt động	                                OS cấp phát và quản lý	                      Kotlin runtime quản lý
//Có thể tạm dừng mà không block	                ❌ Không	                                  ✅ Có

//🧠 Thread là khu vực CPU thực thi code — tại 1 thời điểm chỉ chạy 1 khối code duy nhất.
//
//Muốn chạy nhiều khối code đồng thời → cần nhiều thread (hoặc coroutine chia sẻ thread).


//1️⃣ DEMO 1 – Suspend tuần tự (chạy từng cái một)
//suspend fun task(name: String, time: Long): String {
//    delay(time)
//    println("✅ $name done on ${Thread.currentThread().name}")
//    return name
//}
//
//fun main() = runBlocking {
//    val time = measureTimeMillis {
//        val a = task("Task A", 1000)
//        val b = task("Task B", 1000)
//        println("Result: $a + $b")
//    }
//    println("⏱️ Total time: $time ms")
//}
//
//🧠 Giải thích:
//delay() là suspend — coroutine tạm dừng nhưng không chặn thread.
//Gọi lần lượt → tuần tự.
//Thời gian ≈ 1000 + 1000 = 2000ms.
//
//🧾 Kết quả ví dụ:
//✅ Task A done on main
//✅ Task B done on main
//Result: Task A + Task B
//⏱️ Total time: 2010 ms

//2️⃣ DEMO 2 – Song song (2 coroutine con chạy cùng lúc)
//fun main() = runBlocking {
//    val time = measureTimeMillis {
//        val a = async { task("Task A", 1000) }
//        val b = async { task("Task B", 1000) }
//        println("Result: ${a.await()} + ${b.await()}")
//    }
//    println("⏱️ Total time: $time ms")
//}
//
//🧠 Giải thích:
//
//async {} tạo 2 coroutine con chạy độc lập.
//
//Hai cái chạy song song logic (và vật lý nếu có nhiều thread).
//
//Thời gian ≈ max(1000, 1000) = 1000ms.
//
//🧾 Kết quả ví dụ:
//✅ Task A done on main
//✅ Task B done on main
//Result: Task A + Task B
//⏱️ Total time: 1010 ms


//3️⃣ DEMO 3 – Kết hợp tuần tự + song song
//fun main() = runBlocking {
//    val time = measureTimeMillis {
//        // Nhóm 1 (song song)
//        val group1A = async { task("Group1-A", 1000) }
//        val group1B = async { task("Group1-B", 1000) }
//
//        // Chờ nhóm 1 xong
//        group1A.await()
//        group1B.await()
//
//        // Nhóm 2 (tuần tự)
//        val c = task("Group2-C", 500)
//        val d = task("Group2-D", 500)
//        println("Result: $c + $d")
//    }
//    println("⏱️ Total time: $time ms")
//}
//
//🧠 Giải thích:
//Nhóm 1 (A + B) chạy song song.
//Khi xong, mới tới nhóm 2 (C + D) chạy tuần tự.
//Tổng thời gian ≈ max(1000,1000) + (500+500) = 2000ms.
//
//🧾 Kết quả:
//✅ Group1-A done on main
//✅ Group1-B done on main
//✅ Group2-C done on main
//✅ Group2-D done on main
//⏱️ Total time: 2005 ms

//DEMO 4 – So sánh với Thread thật
//fun threadTask(name: String, time: Long): Thread {
//    return Thread {
//        Thread.sleep(time)
//        println("✅ $name done on ${Thread.currentThread().name}")
//    }
//}
//
//fun main() {
//    val start = System.currentTimeMillis()
//
//    val t1 = threadTask("Thread 1", 1000)
//    val t2 = threadTask("Thread 2", 1000)
//
//    t1.start()
//    t2.start()
//    t1.join()
//    t2.join()
//
//    val end = System.currentTimeMillis()
//    println("⏱️ Total time: ${end - start} ms")
//}
//
//🧠 Kết quả:
//✅ Thread 1 done on Thread-0
//✅ Thread 2 done on Thread-1
//⏱️ Total time: 1005 ms
//
//
//➡️ Kết luận:
//Giống coroutine chạy song song, nhưng mỗi Thread{} tốn tài nguyên thật.
//Coroutine thì nhẹ, có thể hàng ngàn cái trên vài thread.


////full
//import kotlinx.coroutines.*
//import kotlin.system.measureTimeMillis
//
//// Giả lập suspend function (mất 1s)
//suspend fun fakeTask(name: String): String {
//    println("[$name] Start on thread: ${Thread.currentThread().name}")
//    delay(1000) // tạm dừng coroutine (không chặn thread)
//    println("[$name] End   on thread: ${Thread.currentThread().name}")
//    return name
//}
//
//fun main() = runBlocking {
//    println("▶ START MAIN (Thread: ${Thread.currentThread().name})\n")
//
//    // ---- 1️⃣ Tuần tự ----
//    val sequentialTime = measureTimeMillis {
//        val a = fakeTask("A")
//        val b = fakeTask("B")
//        println("Result sequential: $a + $b")
//    }
//    println("⏱ Sequential took: ${sequentialTime}ms\n")
//
//    // ---- 2️⃣ Song song bằng async ----
//    val parallelTime = measureTimeMillis {
//        val a = async { fakeTask("A") }
//        val b = async { fakeTask("B") }
//        println("Result parallel: ${a.await()} + ${b.await()}")
//    }
//    println("⏱ Parallel took: ${parallelTime}ms\n")
//
//    // ---- 3️⃣ Coroutine trong thread riêng ----
//    val threadTime = measureTimeMillis {
//        val thread = Thread {
//            runBlocking {
//                println("▶ Thread block start on: ${Thread.currentThread().name}")
//                val c = async(Dispatchers.IO) { fakeTask("C") }
//                val d = async(Dispatchers.IO) { fakeTask("D") }
//                println("Result thread+coroutine: ${c.await()} + ${d.await()}")
//            }
//        }
//        thread.start()
//        thread.join()
//    }
//    println("⏱ Thread + Coroutine took: ${threadTime}ms\n")
//
//    println("🏁 END MAIN (Thread: ${Thread.currentThread().name})")
//}


//▶ START MAIN (Thread: main)
//
//[A] Start on thread: main
//[A] End   on thread: main
//[B] Start on thread: main
//[B] End   on thread: main
//Result sequential: A + B
//⏱ Sequential took: 2005ms
//
//[A] Start on thread: main
//[B] Start on thread: main
//[A] End   on thread: main
//[B] End   on thread: main
//Result parallel: A + B
//⏱ Parallel took: 1007ms
//
//▶ Thread block start on: Thread-0
//[C] Start on thread: DefaultDispatcher-worker-1
//[D] Start on thread: DefaultDispatcher-worker-2
//[C] End   on thread: DefaultDispatcher-worker-1
//[D] End   on thread: DefaultDispatcher-worker-2
//Result thread+coroutine: C + D
//⏱ Thread + Coroutine took: 1006ms
//
//🏁 END MAIN (Thread: main)
//
//
//Phân tích kết quả
//Loại	                Mô tả	                            Thread sử dụng	                        Tổng thời gian	                            Giải thích
//Sequential	            Gọi 2 suspend lần lượt	            main	                                ~2000ms                      fakeTask("A") xong mới tới "B"
//Parallel (async)	    2 coroutine con song song	        main (cùng thread nhưng luân phiên)	    ~1000ms                      coroutine chia sẻ thread, delay không chặn
//Thread + Coroutine	    Thread riêng + nhiều coroutine IO	Thread-0, worker-1, worker-2	        ~1000ms                      coroutine thật sự chạy song song
//
//
// 🧵 Cùng thread(UI thread) → coroutine chỉ giả song song, chạy luân phiên.
//⚙️ Dispatchers.IO → coroutine có thể chạy thật sự đồng thời trên nhiều thread.
// vật lý trên 2 thread

//Tóm gọn quy luật “vàng”

//Thread count	 Coroutine count	Suspend count	                  Cách hoạt động
//1	             1	                Nhiều	                          Tuần tự từng suspend
//1	             Nhiều	            Mỗi coroutine nhiều suspend	      Coroutine luân phiên nhau khi suspend
//Nhiều	         1	                Nhiều	                          Mỗi suspend tuần tự, nhưng thread có thể khác (nếu dispatcher)
//Nhiều	         Nhiều	            Mỗi coroutine nhiều suspend	      Mỗi coroutine có thể chạy ở thread khác nhau → song song thật, nhưng bên trong vẫn tuần tự suspend
//Nhiều            Quá nhiều          Mỗi coroutine nhiều suspend       Coroutine dư sẽ phải chờ thread rảnh, nhưng các coroutine khác vẫn chạy
//
//kết quả
//🕐 Tuần tự
//🔄 Luân phiên
//⚙️ Gần song song (nhưng coroutine đơn lẻ vẫn tuần tự)
//⚡ Song song ngoài, tuần tự trong
//⚖️ Song song giới hạn

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow

//// Fake suspend function
//suspend fun fakeTask(name: String, duration: Long = 1000) : String {
//    println("[$name] Start on ${Thread.currentThread().name}")
//    delay(duration)
//    println("[$name] End   on ${Thread.currentThread().name}")
//    return name
//}
//
//fun main() = runBlocking {
//    println("▶ START MAIN (Thread: ${Thread.currentThread().name})\n")
//
//    // ---- 1️⃣ 1 coroutine, nhiều suspend ----
//    val t1 = measureTimeMillis {
//        launch {
//            fakeTask("1A")
//            fakeTask("1B")
//            fakeTask("1C")
//        }.join()
//    }
//    println("⏱ Case 1 (1 coroutine, nhiều suspend) took: $t1 ms\n")
//
//    // ---- 2️⃣ Nhiều coroutine, cùng 1 thread ----
//    val t2 = measureTimeMillis {
//        launch { fakeTask("2A") }
//        launch { fakeTask("2B") }
//    }
//    delay(2200) // chờ coroutine kết thúc
//    println("⏱ Case 2 (nhiều coroutine, 1 thread) approx: $t2 ms\n")
//
//    // ---- 3️⃣ 1 coroutine, nhiều thread (sim bằng Dispatcher.IO) ----
//    val t3 = measureTimeMillis {
//        launch(Dispatchers.IO) {
//            fakeTask("3A")
//            fakeTask("3B")
//            fakeTask("3C")
//        }.join()
//    }
//    println("⏱ Case 3 (1 coroutine, nhiều thread via suspend points) took: $t3 ms\n")
//
//    // ---- 4️⃣ Nhiều coroutine, nhiều thread ----
//    val t4 = measureTimeMillis {
//        val c1 = async(Dispatchers.IO) {
//            fakeTask("4A1")
//            fakeTask("4A2")
//        }
//        val c2 = async(Dispatchers.IO) {
//            fakeTask("4B1")
//            fakeTask("4B2")
//        }
//        c1.await()
//        c2.await()
//    }
//    println("⏱ Case 4 (nhiều coroutine, nhiều thread) took: $t4 ms\n")
//
//    // ---- 5️⃣ Nhiều coroutine, thread pool hạn chế ----
//    val limitedDispatcher = newFixedThreadPoolContext(2, "LimitedPool")
//    val t5 = measureTimeMillis {
//        val jobs = (1..4).map { i ->
//            async(limitedDispatcher) {
//                fakeTask("5-$i-1")
//                fakeTask("5-$i-2")
//            }
//        }
//        jobs.awaitAll()
//    }
//    println("⏱ Case 5 (nhiều coroutine, limited threads) took: $t5 ms\n")
//
//    println("🏁 END MAIN (Thread: ${Thread.currentThread().name})")
//}

//🧪 Kết quả demo (ước tính)
//Case 1 — 1 coroutine, nhiều suspend
//[1A] Start on main
//[1A] End   on main
//[1B] Start on main
//[1B] End   on main
//[1C] Start on main
//[1C] End   on main
//⏱ Case 1 took: ~3000 ms
//
//
//Giải thích: Tuần tự, suspend lần lượt, 1 thread duy nhất (main).
//
//Case 2 — Nhiều coroutine, cùng 1 thread
//[2A] Start on main
//[2B] Start on main
//[2A] End   on main
//[2B] End   on main
//⏱ Case 2 approx: ~2000 ms
//
//
//Giải thích: Cả 2 coroutine cùng thread, luân phiên khi suspend.
//
//Nhìn như song song logic nhưng thực chất tuần tự theo luồng tạm dừng.
//
//Case 3 — 1 coroutine, nhiều suspend trên Dispatcher.IO
//[3A] Start on DefaultDispatcher-worker-1
//[3A] End   on DefaultDispatcher-worker-1
//[3B] Start on DefaultDispatcher-worker-1
//[3B] End   on DefaultDispatcher-worker-1
//[3C] Start on DefaultDispatcher-worker-1
//[3C] End   on DefaultDispatcher-worker-1
//⏱ Case 3 took: ~3000 ms
//
//
//Giải thích: Chỉ 1 coroutine nhưng Dispatcher.IO có thể gán thread khác khi suspend → nhưng do 1 coroutine vẫn tuần tự các suspend → thời gian tổng vẫn tuần tự.
//
//Case 4 — Nhiều coroutine, nhiều thread
//[4A1] Start on DefaultDispatcher-worker-1
//[4B1] Start on DefaultDispatcher-worker-2
//[4A1] End   on DefaultDispatcher-worker-1
//[4A2] Start on DefaultDispatcher-worker-1
//[4B1] End   on DefaultDispatcher-worker-2
//[4B2] Start on DefaultDispatcher-worker-2
//[4A2] End   on DefaultDispatcher-worker-1
//[4B2] End   on DefaultDispatcher-worker-2
//⏱ Case 4 took: ~2000 ms
//
//
//Giải thích: Mỗi coroutine được gán thread riêng → song song vật lý, nhưng suspend trong coroutine vẫn tuần tự → tổng thời gian ≈ max của 2 coroutine con.
//
//Case 5 — Nhiều coroutine, thread pool hạn chế
//[5-1-1] Start on LimitedPool-1
//[5-2-1] Start on LimitedPool-2
//[5-3-1] waiting for thread
//[5-4-1] waiting for thread
//[5-1-1] End   on LimitedPool-1
//[5-1-2] Start on LimitedPool-1
//[5-2-1] End   on LimitedPool-2
//[5-2-2] Start on LimitedPool-2
//[5-3-1] Start on LimitedPool-1
//[5-4-1] Start on LimitedPool-2
//[5-1-2] End   on LimitedPool-1
//[5-2-2] End   on LimitedPool-2
//[5-3-1] End   on LimitedPool-1
//[5-4-1] End   on LimitedPool-2
//⏱ Case 5 took: ~4000 ms
//
//
//Giải thích: Pool chỉ 2 thread → 4 coroutine phải chờ thread rảnh.
//
//Khi thread rảnh → coroutine tiếp tục chạy.
//
//Tổng thời gian > song song lý tưởng (~2000 ms) nhưng vẫn nhanh hơn tuần tự hoàn toàn (~4000 ms nếu chạy tuần tự trên 1 thread).

