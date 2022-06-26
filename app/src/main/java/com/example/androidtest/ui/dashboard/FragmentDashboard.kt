package com.example.androidtest.ui.dashboard

import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidtest.R
import com.example.androidtest.app.Navigator
import com.example.androidtest.databinding.LayoutFragmentDashboardBinding
import com.example.androidtest.repository.retrofit.model.Item
import com.example.androidtest.repository.room.entities.User
import com.example.androidtest.utils.Utils
import java.util.*


class FragmentDashboard : Fragment(), AdapterForDashboard.OnClick {
    private lateinit var binding: LayoutFragmentDashboardBinding
    private val viewModel by viewModels<VMFragmentDashboard>()
    lateinit var adapterForDashboard: AdapterForDashboard

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutFragmentDashboardBinding.inflate(layoutInflater)
        init()
        return binding.root
    }

    private fun init() {
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvDataList.layoutManager = layoutManager
        observeData()
        hideShowLoader(true)
        viewModel.getData()
        setEvents()
        greetUser()
    }

    private fun greetUser() {
        viewModel.getUserDetails()
        viewModel.user.observe(this, object : Observer<User> {
            override fun onChanged(t: User?) {
                viewModel.user.removeObserver(this)
                t?.let {
                    if (it.isLoggedIn == 1) {
                        val c: Calendar = Calendar.getInstance()
                        when (c.get(Calendar.HOUR_OF_DAY)) {
                            in 0..11 -> {
                                binding.tvTitle.text = "Good Morning ${it.userName}"
                            }
                            in 12..15 -> {
                                binding.tvTitle.text = "Good Afternoon ${it.userName}"
                            }
                            in 16..20 -> {
                                binding.tvTitle.text = "Good Evening ${it.userName}"
                            }
                            in 21..23 -> {
                                binding.tvTitle.text = "Good Night ${it.userName}"
                            }
                        }
                    }
                }
            }
        })
    }

    private fun setEvents() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            hideShowLoader(true)
            viewModel.getData()
        }

        binding.btnLogout.setOnClickListener {
            viewModel.logoutUser()
            Utils.showSnackBar(binding.root, "User Logged out...")
            Navigator.navigate(R.id.fragmentLogin, R.id.action_login, Bundle())
        }
    }

    private fun observeData() {
        viewModel.data.observe(this, object : Observer<ArrayList<Item>> {
            override fun onChanged(t: ArrayList<Item>?) {
                hideShowLoader(false)
                t?.let {
                    if (!this@FragmentDashboard::adapterForDashboard.isInitialized) {
                        adapterForDashboard = AdapterForDashboard(t, this@FragmentDashboard)
                        binding.rvDataList.adapter = adapterForDashboard
                    } else {
                        adapterForDashboard.updateDataList(t)
                        adapterForDashboard.notifyDataSetChanged()
                    }
                    return
                }
                Utils.showSnackBar(binding.root, "Unable to Fetch Data..... Retry!")
            }
        })
    }

    override fun onItemClicked(item: Item) {
        showDetailsDialog(item)
    }

    private fun hideShowLoader(shouldShow: Boolean) {
        if (shouldShow) {
            binding.rvDataList.visibility = View.GONE
            binding.loader.visibility = View.VISIBLE
        } else {
            binding.loader.visibility = View.GONE
            binding.rvDataList.visibility = View.VISIBLE
        }
    }

    private fun showDetailsDialog(item: Item) {
        val dialog = Dialog(requireContext())

        dialog?.setContentView(R.layout.layout_details_dialog)
        dialog?.setTitle("Item Details")
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.findViewById<TextView>(R.id.tv_name)?.text = item.name
        dialog?.findViewById<TextView>(R.id.tv_dose)?.text = item.dose
        dialog?.findViewById<TextView>(R.id.tv_strength)?.text = item.strength
        dialog?.findViewById<Button>(R.id.btn_ok)?.setOnClickListener {
            dialog?.dismiss()
        }

        dialog?.show()
    }
}