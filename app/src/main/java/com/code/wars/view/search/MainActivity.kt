package com.code.wars.view.search

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.code.wars.R
import com.code.wars.databinding.ActivityMainBinding
import com.code.wars.models.UserResponse
import com.code.wars.utils.Constants
import com.code.wars.utils.Utils
import com.code.wars.view.DebouncingQueryTextListener
import com.code.wars.view.profile.ProfileActivity
import com.code.wars.viewModels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var resultList = ArrayList<UserResponse>()
    private lateinit var adapter : SearchAdapter

    @Inject
    lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupObservers()
        setupResultList()
        setupSearch()
        binding.lifecycleOwner = this
    }

    private fun setupResultList() {
        adapter = SearchAdapter(resultList, object : UserOnClickListener {
            override fun onClick(userResponse: UserResponse) {
                val intent = Intent(this@MainActivity, ProfileActivity::class.java)
                intent.putExtra(Constants.argsUserResponse, userResponse)
                startActivity(intent)
            }
        })
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupSearch() {
        binding.searchView.queryHint = resources.getString(R.string.hint_search_user)
        binding.searchView.setOnQueryTextListener(DebouncingQueryTextListener(this.lifecycle) { query ->
            query?.let {
                if (it.length > 3 && Utils.hasNetwork(this@MainActivity)) {
                    viewModel.getUser(it)
                } else {
                    resultList.clear()
                    adapter.notifyDataSetChanged()
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupObservers() {
        viewModel.userLiveData.observe(this, {
            it?.let {
                validateTextIsVisible(false)
                resultList.add(it)
                adapter.notifyDataSetChanged()
            }
        })

        viewModel.errorLiveData.observe(this, {
            it?.let {
                validateTextIsVisible(true)
            }
        })
    }

    private fun validateTextIsVisible(show: Boolean) {
        if (show && binding.txtEmptyData.visibility == View.INVISIBLE)
            binding.txtEmptyData.visibility = View.VISIBLE
        else
            binding.txtEmptyData.visibility = View.INVISIBLE
    }
}