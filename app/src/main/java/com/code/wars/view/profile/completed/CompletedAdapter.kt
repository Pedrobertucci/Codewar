package com.code.wars.view.profile.completed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.code.wars.databinding.CompletedCardBinding
import com.code.wars.models.Completed
import com.code.wars.view.profile.ProfileOnclickListener

class CompletedAdapter(private val completedList: ArrayList<Completed>,
                       private val profileOnclickListener: ProfileOnclickListener
) :
    RecyclerView.Adapter<CompletedAdapter.ViewHolder>() {

    inner class ViewHolder(private val itemBinding: CompletedCardBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(completed: Completed) {
            itemBinding.item = completed
            itemBinding.completedOnClickListener = profileOnclickListener
            itemBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = CompletedCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(completedList[position])
    }

    override fun getItemCount(): Int = completedList.size
}