package com.example.myapplication


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.myapplication.databinding.ActivityTestBinding
import com.example.myapplication.ui.main.MainActivity


class TestActivity : AppCompatActivity(){
    companion object{
        const val TAG = "TAG"
    }
    val binding by lazy {
        ActivityTestBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"onCreate")
        setContentView(binding.root)

        binding.btnButton.setOnClickListener {
            var dia = AlertDialog.Builder(this)
                .setMessage("Show Dialog")
                .setPositiveButton("YES"){dia,id ->
                    dia.dismiss()
                }
                .setNegativeButton("NO"){dia,id ->
                    dia.dismiss()
                }
        }

        binding.btnButton2.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.btnButton3.setOnClickListener {
            var count = 0
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<TestFragment>(
                    containerViewId = R.id.container,
                    tag = "test_${count++}"
                )
            }
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