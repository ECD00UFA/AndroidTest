package com.example.androidtest.ui.login

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.androidtest.R
import com.example.androidtest.app.Navigator
import com.example.androidtest.databinding.LayoutFragmentLoginBinding
import com.example.androidtest.utils.Utils

class FragmentLogin : Fragment() {

    private lateinit var binding: LayoutFragmentLoginBinding
    private val viewModel by viewModels<VMFragmentLogin>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutFragmentLoginBinding.inflate(layoutInflater)
        init()
        setEvents()
        return binding.root
    }

    private fun init() {
        viewModel.isUserLoggedIn()
        viewModel.isUserLoggedIn.observe(this, object : Observer<Int> {
            override fun onChanged(t: Int?) {
                if (t == 1) {
                    viewModel.isUserLoggedIn.removeObserver(this)
                    Navigator.navigate(R.id.fragmentDashboard, R.id.action_dashboard, Bundle())
                }
            }
        })
    }

    private fun setEvents() {
        binding.btnLogin.setOnClickListener {
            val userName: String = binding.etUserName.text.toString()
            val password: String = binding.etPassword.text.toString()

            when (viewModel.validate(userName, password)) {
                1 -> {
                    binding.etUserName.error = "*"
                    binding.etUserName.requestFocus()
                }
                2 -> {
                    binding.etPassword.error = "*"
                    binding.etPassword.requestFocus()
                }
                else -> {
                    viewModel.setUserLoggedIn(userName)
                    Utils.showSnackBar(binding.root, "Login Success!")
                    Navigator.navigate(R.id.fragmentDashboard, R.id.action_dashboard, Bundle())
                }
            }
        }

        binding.tvForgetPassword.setOnClickListener {
            Utils.showSnackBar(
                binding.root,
                "Work in progress!!"
            )
        }

        binding.imgPassIcon.setOnClickListener {
            if (it.tag == getString(R.string.show_password)) {
                binding.imgPassIcon.tag = getString(R.string.hide_password)
                binding.imgPassIcon.setImageResource(R.drawable.icon_pass_hidden)
                binding.etPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            } else {
                binding.imgPassIcon.tag = getString(R.string.show_password)
                binding.imgPassIcon.setImageResource(R.drawable.icon_pass_show)
                binding.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            if (binding.etPassword.text.toString().isNotEmpty()) {
                binding.etPassword.setSelection(
                    binding.etPassword.text.toString().length
                )
            }
        }
    }
}