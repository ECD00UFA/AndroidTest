package com.example.androidtest.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidtest.databinding.LayoutFragmentDetailsBinding

class FragmentDetails: Fragment() {

    private lateinit var binding: LayoutFragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutFragmentDetailsBinding.inflate(layoutInflater)
        init()
        return binding.root
    }

    private fun init(){

    }



}