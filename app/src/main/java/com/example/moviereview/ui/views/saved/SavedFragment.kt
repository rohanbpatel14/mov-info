package com.example.moviereview.ui.views.saved

import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.moviereview.R
import com.example.moviereview.databinding.FragmentSavedBinding
import com.example.moviereview.ui.adapters.GenericRvAdapter
import com.example.moviereview.ui.models.UIMedia
import com.example.moviereview.ui.utils.BaseFragment
import com.example.moviereview.ui.utils.DataStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_saved.*

@AndroidEntryPoint
class SavedFragment : BaseFragment(R.layout.fragment_saved) {
    private var animationController: LayoutAnimationController? = null
    private lateinit var binding: FragmentSavedBinding

    private val viewModel: SavedViewModel by viewModels()

    private val savedMediaAdapter = GenericRvAdapter<UIMedia>()
    private val savedMediaClickListener = object : GenericRvAdapter.GenericItemListener<UIMedia> {
        override fun onClick(item: UIMedia) {
            if (item.type == "movie")
                findNavController().navigate(R.id.movieDetailFragment, bundleOf("movieId" to item.id))
            else if (item.type == "tv")
                findNavController().navigate(R.id.tvShowDetailFragment, bundleOf("tvShowId" to item.id))
        }
    }

    override fun bindingLayout(binding: ViewDataBinding?) {
        this.binding = binding as FragmentSavedBinding
    }

    override fun initializeObservers() {
        viewModel.savedMedia.observe(viewLifecycleOwner, {
            binding.viewStatus = it.status

            when (it.status) {
                DataStatus.Status.LOADING -> {
                }
                DataStatus.Status.SUCCESS -> {
                    it.data?.let { savedMediaAdapter.setItems(it) }
                    rvSavedMedia.scheduleLayoutAnimation()
                }
                DataStatus.Status.ERROR -> {
                }
                DataStatus.Status.EMPTY -> {
                }
            }
        })
    }

    override fun initializeViews() {
        animationController = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)

        savedMediaAdapter.setGenericItemListener(savedMediaClickListener)
        rvSavedMedia.layoutAnimation = animationController
        rvSavedMedia.adapter = savedMediaAdapter
    }
}