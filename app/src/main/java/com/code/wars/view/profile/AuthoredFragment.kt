package com.code.wars.view.profile

import android.annotation.SuppressLint
import android.content.Intent
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
import com.code.wars.utils.Constants
import com.code.wars.view.details.ChallengeDetailActivity
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
        viewModel.getAuthoredChallenges((requireActivity() as ProfileActivity).userResponse.username)
    }

    private var onClickListener = object : ChallengeOnClickListener {
        override fun onClick(challenge: Challenge) {
            val intent = Intent(requireActivity() as ProfileActivity, ChallengeDetailActivity::class.java)
            intent.putExtra(Constants.argsChallenge, challenge)
            startActivity(intent)
        }
    }

    private fun setupAdapter() {
        adapter = AuthoredAdapter(challenges, onClickListener)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupObservers() {
        viewModel.authoredLiveData.observe(requireActivity() as ProfileActivity, {
            it?.let {
                validateTextIsVisible(it.challenges.size <= 0)
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

    private fun validateTextIsVisible(show: Boolean) {
        if (show && binding.txtEmptyData.visibility == View.INVISIBLE)
            binding.txtEmptyData.visibility = View.VISIBLE
        else
            binding.txtEmptyData.visibility = View.INVISIBLE
    }
}