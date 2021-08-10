package com.code.wars.view.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.code.wars.databinding.AuthoredCardBinding
import com.code.wars.models.Challenge

class AuthoredAdapter(private val challenges: ArrayList<Challenge>,
                      private val challengeOnClickListener: ChallengeOnClickListener) :
    RecyclerView.Adapter<AuthoredAdapter.ViewHolder>() {

    inner class ViewHolder(private val itemBinding: AuthoredCardBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(challenge: Challenge) {
            itemBinding.item = challenge
            itemBinding.challengeOnClickListener = challengeOnClickListener
            itemBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = AuthoredCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(challenges[position])
    }

    override fun getItemCount(): Int = challenges.size
}