package com.code.wars.view.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.code.wars.R
import com.code.wars.viewModels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthoredFragment : Fragment() {

    @Inject
    lateinit var viewModel: UserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_authored, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.completedLiveData.observe(requireActivity() as ProfileActivity, {

        })

        viewModel.errorLiveData.observe(requireActivity() as ProfileActivity, {

        })

        viewModel.emptyValuesLiveData.observe(requireActivity() as ProfileActivity, {

        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAuthoredChallenges((requireActivity() as ProfileActivity).userResponse.username)
    }
}