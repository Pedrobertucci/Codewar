package com.code.wars.view.search

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
import androidx.appcompat.app.AlertDialog

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
        setupNetwork()
        binding.lifecycleOwner = this
    }

    private fun setupNetwork() {
       if (!Utils.hasNetwork(this)) {
           val builder: AlertDialog.Builder = AlertDialog.Builder(this)
           builder.setTitle(resources.getText(R.string.error_network_title))
           builder.setMessage(resources.getText(R.string.error_network_message))
           builder.setPositiveButton(resources.getText(R.string.error_network_connect)) { _, _ ->
               startActivity(Intent(android.provider.Settings.ACTION_WIFI_SETTINGS))
           }

           builder.setNegativeButton(
               resources.getText(R.string.error_network_close)) { dialog, _ -> dialog.dismiss() }
           val alertDialog: AlertDialog = builder.create()
           alertDialog.show()
       }
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
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupSearch() {
        binding.btnReverse.setOnClickListener { reverseResultList() }
        binding.searchView.queryHint = resources.getString(R.string.hint_search_user)
        binding.searchView.setOnQueryTextListener(DebouncingQueryTextListener(this.lifecycle) { query ->
            query?.let {
                if (it.length > 2 && Utils.hasNetwork(this@MainActivity)) {
                    viewModel.getUser(it)
                }
            }
        })
    }

    private fun setupObservers() {
        viewModel.userLiveData.observe(this, {
            it?.let {
                hideWelcomeMessage()
                validateTextIsVisible(false)
                validateUser(it)
            }
        })

        viewModel.errorLiveData.observe(this, {
            it?.let {
                hideWelcomeMessage()
                validateTextIsVisible(resultList.size == 0)
            }
        })

        viewModel.loadingLiveData.observe(this, {
            it?.let {
                if (it) {
                    if (binding.progressBar.visibility == View.INVISIBLE)
                        binding.progressBar.visibility = View.VISIBLE
                } else {
                    if (binding.progressBar.visibility == View.VISIBLE)
                        binding.progressBar.visibility = View.INVISIBLE
                }
            }
        })
    }

    private fun validateUser(userResponse: UserResponse) {
        val result = resultList.filter { user-> user.username == userResponse.username}

        if (result.isEmpty()) {
            if (resultList.size == 5) {
                resultList.removeAt(0)
                adapter.notifyItemRemoved(0)
            }

            resultList.add(userResponse)
            adapter.notifyItemInserted(resultList.size - 1)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun reverseResultList() {
        resultList.reverse()
        adapter.notifyDataSetChanged()
    }

    private fun validateTextIsVisible(show: Boolean) {
        if (show && binding.txtEmptyData.visibility == View.INVISIBLE)
            binding.txtEmptyData.visibility = View.VISIBLE
        else
            binding.txtEmptyData.visibility = View.INVISIBLE
    }

    private fun hideWelcomeMessage() {
        if (binding.txtMessage.visibility == View.VISIBLE)
            binding.txtMessage.visibility = View.INVISIBLE
    }
}