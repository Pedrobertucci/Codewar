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
import com.code.wars.R
import com.code.wars.databinding.FragmentAuthoredBinding
import com.code.wars.models.Challenge
import com.code.wars.viewModels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthoredFragment : Fragment() {
    private lateinit var binding: FragmentAuthoredBinding
    private var challenges : ArrayList<Challenge> = ArrayList()
    private lateinit var adapter : AuthoredAdapter

    @Inject
    lateinit var viewModel: UserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding  = DataBindingUtil.inflate(inflater, R.layout.fragment_authored, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupAdapter()
    }

    private fun setupAdapter() {
        adapter = AuthoredAdapter(challenges)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupObservers() {
        viewModel.authoredLiveData.observe(requireActivity() as ProfileActivity, {
            it?.let {
                challenges.addAll(it.challenges)
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
                    Toast.makeText(requireContext(),
                        requireContext().getText(R.string.authored_empty_values), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAuthoredChallenges((requireActivity() as ProfileActivity).userResponse.username)
    }
}