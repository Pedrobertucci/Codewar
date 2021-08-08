package com.code.wars.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.code.wars.R
import com.code.wars.databinding.ActivityMainBinding
import com.code.wars.models.UserResponse
import com.code.wars.utils.Utils
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
                Toast.makeText(this@MainActivity,
                    "${userResponse.username} clicked", Toast.LENGTH_SHORT).show()
            }
        })
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun setupSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if (query.length > 3 && Utils.hasNetwork(this@MainActivity)) {
                        viewModel.getUser(query)
                    } else {
                        resultList.clear()
                        adapter.notifyDataSetChanged()
                    }
                }
                return true
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (newText.length > 3 && Utils.hasNetwork(this@MainActivity)) {
                        viewModel.getUser(newText)
                    } else {
                        resultList.clear()
                        adapter.notifyDataSetChanged()
                    }
                }
                return true
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupObservers() {
        viewModel.userLiveData.observe(this, {
            it?.let {
                resultList.add(it)
                adapter.notifyDataSetChanged()
            }
        })

        viewModel.errorLiveData.observe(this, {
            it?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.emptyValuesLiveData.observe(this, {
            it?.let {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }
}