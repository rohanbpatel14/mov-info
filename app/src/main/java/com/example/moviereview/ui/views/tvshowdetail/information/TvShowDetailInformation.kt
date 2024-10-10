package com.example.moviereview.ui.views.tvshowdetail.information

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.findNavController
import com.example.moviereview.R
import com.example.moviereview.databinding.FragmentTvShowDetailInfoBinding
import com.example.moviereview.ui.adapters.GenericRvAdapter
import com.example.moviereview.ui.models.UICast
import com.example.moviereview.ui.models.UIImage
import com.example.moviereview.ui.models.UIPosterMedia
import com.example.moviereview.ui.models.UITvShowDetail
import com.example.moviereview.ui.utils.BaseFragment
import kotlinx.android.synthetic.main.fragment_tv_show_detail_info.*

class TvShowDetailInformation() : BaseFragment(R.layout.fragment_tv_show_detail_info) {
    private var animationController: LayoutAnimationController? = null
    private lateinit var binding: FragmentTvShowDetailInfoBinding

    private val uiTvShowDetail: UITvShowDetail? by lazy { arguments?.getParcelable("tvShowDetail") as UITvShowDetail? }

    private val similarTvShowsAdapter = GenericRvAdapter<UIPosterMedia>()
    private val similarTvShowsClickListener = object : GenericRvAdapter.GenericItemListener<UIPosterMedia> {
        override fun onClick(item: UIPosterMedia) {
            findNavController().navigate(R.id.tvShowDetailFragment, bundleOf("tvShowId" to item.id))
        }
    }
    private val tvShowCastAdapter = GenericRvAdapter<UICast>()
    private val tvShowImagesAdapter = GenericRvAdapter<UIImage>()

    override fun bindingLayout(binding: ViewDataBinding?) {
        this.binding = binding as FragmentTvShowDetailInfoBinding
    }

    override fun initializeViews() {
        animationController = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_slide_right)

        similarTvShowsAdapter.setGenericItemListener(similarTvShowsClickListener)
        rvTvShowDetailSimilar.layoutAnimation = animationController
        rvTvShowDetailSimilar.adapter = similarTvShowsAdapter

        rvTvShowDetailCast.layoutAnimation = animationController
        rvTvShowDetailCast.adapter = tvShowCastAdapter

        rvTvShowDetailImages.layoutAnimation = animationController
        rvTvShowDetailImages.adapter = tvShowImagesAdapter

        similarTvShowsAdapter.setItems(uiTvShowDetail?.similar ?: listOf())
        tvShowImagesAdapter.setItems(uiTvShowDetail?.images ?: listOf())
        tvShowCastAdapter.setItems(uiTvShowDetail?.cast ?: listOf())

        binding.data = uiTvShowDetail
    }

    companion object {
        fun newInstance(uiTvShowDetail: UITvShowDetail): TvShowDetailInformation = TvShowDetailInformation().apply {
            arguments = Bundle().apply { putParcelable("tvShowDetail", uiTvShowDetail) }
        }
    }

}