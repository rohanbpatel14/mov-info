package com.example.moviereview.ui.views.tvshowdetail.seasons

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.moviereview.R
import com.example.moviereview.ui.adapters.GenericRvAdapter
import com.example.moviereview.ui.models.UISeason
import com.example.moviereview.ui.models.UITvShowDetail
import com.example.moviereview.ui.utils.BaseFragment
import kotlinx.android.synthetic.main.fragment_tv_show_detail_seasons.*

class TvShowDetailSeasonsFragment() : BaseFragment(R.layout.fragment_tv_show_detail_seasons) {
    private var animationController: LayoutAnimationController? = null

    private val uiTvShowDetail: UITvShowDetail? by lazy { arguments?.getParcelable("tvShowDetail") as UITvShowDetail? }

    private val tvShowSeasonsAdapter = GenericRvAdapter<UISeason>()
    private val tvShowSeasonsClickListener = object : GenericRvAdapter.GenericItemListener<UISeason> {
        override fun onClick(item: UISeason) {
            findNavController().navigate(R.id.tvShowSeasonEpisodesFragment, bundleOf("tvShowId" to uiTvShowDetail?.id, "seasonNumber" to item.seasonNumber))
        }
    }

    override fun initializeViews() {
        animationController = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)

        tvShowSeasonsAdapter.setGenericItemListener(tvShowSeasonsClickListener)
        rvTvShowDetailSeasons.layoutAnimation = animationController
        rvTvShowDetailSeasons.adapter = tvShowSeasonsAdapter

        tvShowSeasonsAdapter.setItems(uiTvShowDetail?.seasons ?: listOf())

    }

    companion object {
        fun newInstance(uiTvShowDetail: UITvShowDetail): TvShowDetailSeasonsFragment = TvShowDetailSeasonsFragment().apply {
            arguments = Bundle().apply { putParcelable("tvShowDetail", uiTvShowDetail) }
        }
    }
}