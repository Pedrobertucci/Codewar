package com.code.wars.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.code.wars.databinding.MemberCardBinding
import com.code.wars.models.UserResponse

class SearchAdapter(private val resultList: ArrayList<UserResponse>,
                    private val userOnClickListener: UserOnClickListener) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    inner class ViewHolder(private val itemBinding: MemberCardBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(userResponse: UserResponse, userOnClickListener: UserOnClickListener) {
            itemBinding.item = userResponse
            itemBinding.userOnClickListener = userOnClickListener
            itemBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = MemberCardBinding.inflate(
                                    LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(resultList[position], userOnClickListener)
    }

    override fun getItemCount(): Int = resultList.size
}