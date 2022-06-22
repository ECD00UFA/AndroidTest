package com.example.androidtest.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidtest.databinding.LayoutFragmentDashboardBinding

class FragmentDashboard: Fragment() {
    private lateinit var binding: LayoutFragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutFragmentDashboardBinding.inflate(layoutInflater)
        init()
        return binding.root
    }

    private fun init(){

    }
}