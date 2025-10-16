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

