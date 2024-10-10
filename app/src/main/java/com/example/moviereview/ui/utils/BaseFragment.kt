package com.example.moviereview.ui.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

open class BaseFragment(private val layoutId: Int) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: ViewDataBinding? = DataBindingUtil.inflate(inflater, layoutId, container, false)
        bindingLayout(binding)
        return binding?.root ?: super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
        initializeObservers()
    }

    open fun bindingLayout(binding: ViewDataBinding?) {}

    open fun initializeObservers() {}

    open fun initializeViews() {}

}