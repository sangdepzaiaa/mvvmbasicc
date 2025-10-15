package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentTestBinding


class TestFragment : Fragment() {
    companion object {
        const val TAGG = "TAGG"
    }
    private var _binding: FragmentTestBinding? = null
    private val binding get() = _binding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        Log.d(TAGG, "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAGG, "onCreate")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAGG, "onCreateView")
        _binding = FragmentTestBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAGG, "onViewCreated")
        binding?.tvtitlee?.text = "Hello from TestFragment!"
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAGG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAGG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAGG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAGG, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAGG, "onDestroyView")
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAGG, "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAGG, "onDetach")
    }
}