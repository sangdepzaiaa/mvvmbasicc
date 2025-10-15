package com.example.myapplication


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.myapplication.databinding.ActivityTestBinding
import com.example.myapplication.ui.main.MainActivity


class TestActivity : AppCompatActivity() {
    companion object{
        const val TAG = "TAG"
    }
    val binding by lazy {
        ActivityTestBinding.inflate(layoutInflater)
    }
    private var fragmentCount = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Log.d(TAG,"onCreate")
       binding.btnButton3.setOnClickListener {
           fragmentCount++
           supportFragmentManager.commit {
               setReorderingAllowed(true)
               add<TestFragment>(
                   containerViewId = R.id.container,
                   tag = TestFragment::class.java.name
               )

           }
       }

        binding.btnButton2.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.btnButton.setOnClickListener {
            var dia = AlertDialog.Builder(this)
                .setMessage("Get Started!")
                .setPositiveButton("YES"){dia,id ->
                    dia.dismiss()
                }
                .setNegativeButton("NO"){dia,id ->
                    dia.dismiss()

                }
            dia.create().show()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG,"onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG,"onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG,"onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG,"onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG,"onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy")
    }
}