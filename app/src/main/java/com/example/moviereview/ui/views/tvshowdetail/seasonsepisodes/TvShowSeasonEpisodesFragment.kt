package com.example.moviereview.ui.views.tvshowdetail.seasonsepisodes

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import com.example.moviereview.R
import com.example.moviereview.databinding.FragmentTvShowSeasonEpisodesBinding
import com.example.moviereview.ui.adapters.GenericRvAdapter
import com.example.moviereview.ui.models.UIEpisode
import com.example.moviereview.ui.utils.BaseFragment
import com.example.moviereview.ui.utils.DataStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_tv_show_season_episodes.*

@AndroidEntryPoint
class TvShowSeasonEpisodesFragment() : BaseFragment(R.layout.fragment_tv_show_season_episodes) {
    private var animationController: LayoutAnimationController? = null
    private lateinit var binding: FragmentTvShowSeasonEpisodesBinding

    private val viewModel: TvShowSeasonEpisodesViewModel by viewModels()

    private val seasonEpisodesAdapter = GenericRvAdapter<UIEpisode>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tvShowId = arguments?.let { it.getInt("tvShowId") } ?: return
        val seasonNum = arguments?.let { it.getInt("seasonNumber") } ?: return

        (activity as AppCompatActivity).supportActionBar?.title = "Season $seasonNum"

        viewModel.getTvShowSeasonEpisodes(tvShowId, seasonNum)
    }

    override fun bindingLayout(binding: ViewDataBinding?) {
        this.binding = binding as FragmentTvShowSeasonEpisodesBinding
    }

    override fun initializeViews() {
        animationController = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
        rvTvShowDetailSeasonEpisodes.layoutAnimation = animationController
        rvTvShowDetailSeasonEpisodes.adapter = seasonEpisodesAdapter
    }

    override fun initializeObservers() {
        viewModel.seasonEpisodes.observe(viewLifecycleOwner, {
            binding.viewStatus = it.status
            when (it.status) {
                DataStatus.Status.LOADING -> {
                }
                DataStatus.Status.SUCCESS -> {
                    it.data?.let { seasonEpisodesAdapter.setItems(it) }
                }
                DataStatus.Status.ERROR -> {
                }
                DataStatus.Status.EMPTY -> {
                }
            }
        })
    }
}