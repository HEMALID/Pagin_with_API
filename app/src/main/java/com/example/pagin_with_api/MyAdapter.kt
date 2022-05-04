package com.example.pagin_with_api

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pagin_with_api.databinding.DataItemBinding
import com.example.pagin_with_api.dataclass.Data

class MyAdapter(var con: Context, private var userlist: ArrayList<Data.DataItem>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {



    class ViewHolder(var binding: DataItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Data.DataItem) {
            binding.user = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val userItemBinding = DataItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(userItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userlist[position])
    }

    override fun getItemCount(): Int {
        return userlist.size
    }


}