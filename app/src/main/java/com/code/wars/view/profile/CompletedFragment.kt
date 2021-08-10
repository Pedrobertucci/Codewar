package com.code.wars.view.profile

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.code.wars.R
import com.code.wars.databinding.FragmentCompletedBinding
import com.code.wars.models.Completed
import com.code.wars.viewModels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CompletedFragment : Fragment() {
    private lateinit var binding: FragmentCompletedBinding
    private var completedList : ArrayList<Completed> = ArrayList()
    private lateinit var adapter : CompletedAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var pastVisibleItems = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0
    private var loading = true

    @Inject
    lateinit var viewModel: UserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding  = DataBindingUtil.inflate(inflater, R.layout.fragment_completed, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupCompletedList()
        viewModel.getCompletedCodeChallenge((requireActivity() as ProfileActivity).userResponse.username)
    }

    private fun setupCompletedList() {
        adapter = CompletedAdapter(completedList)
        linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.addOnScrollListener(onScrollListener)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupObservers() {
        viewModel.completedLiveData.observe(requireActivity() as ProfileActivity, {
            it?.let {
                loading = true
                validateTextIsVisible(it.completed.size <= 0)
                completedList.addAll(it.completed)
                adapter.notifyDataSetChanged()
            }
        })

        viewModel.errorLiveData.observe(requireActivity() as ProfileActivity, {
            it?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.emptyValuesLiveData.observe(requireActivity() as ProfileActivity, {
            it?.let {
                if (it) {
                    validateTextIsVisible(true)
                }
            }
        })

        viewModel.loadingLiveData.observe(requireActivity() as ProfileActivity, {
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

    private val onScrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            visibleItemCount = linearLayoutManager.childCount
            totalItemCount = linearLayoutManager.itemCount
            pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition()

            if (loading) {
                if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                    loading = false
                    viewModel.getCompletedCodeChallenge((requireActivity() as ProfileActivity).userResponse.username)
                }
            }
        }
    }

    private fun validateTextIsVisible(show: Boolean) {
        if (show && binding.txtEmptyData.visibility == View.INVISIBLE)
            binding.txtEmptyData.visibility = View.VISIBLE
        else
            binding.txtEmptyData.visibility = View.INVISIBLE
    }
}