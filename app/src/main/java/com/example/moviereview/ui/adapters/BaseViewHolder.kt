package com.example.moviereview.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.moviereview.BR

class BaseViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    fun <T> bind(item: T, itemClickListener: GenericRvAdapter.GenericItemListener<T>?) {
        binding.setVariable(BR.item, item)
        binding.setVariable(BR.clickListener, itemClickListener)
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup, viewType: Int): BaseViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return BaseViewHolder(DataBindingUtil.inflate(inflater, viewType, parent, false))
        }
    }

}