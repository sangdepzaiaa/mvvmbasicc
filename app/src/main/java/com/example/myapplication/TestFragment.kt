package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentTestBinding

class TestFragment : Fragment(){
    companion object{
        const val TAGG = "TAGG"
    }
    val binding by lazy { FragmentTestBinding.inflate(layoutInflater)  }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAGG,"onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAGG,"onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAGG,"onCreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAGG,"onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAGG,"onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAGG,"onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAGG,"onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAGG,"onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAGG,"onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAGG,"onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAGG,"onDetach")
    }
}
