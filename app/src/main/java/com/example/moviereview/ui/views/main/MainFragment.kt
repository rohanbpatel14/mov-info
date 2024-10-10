package com.example.moviereview.ui.views.main

import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.moviereview.R
import com.example.moviereview.databinding.FragmentMainBinding
import com.example.moviereview.ui.adapters.GenericRvAdapter
import com.example.moviereview.ui.models.UIBackdropMedia
import com.example.moviereview.ui.models.UIPosterMedia
import com.example.moviereview.ui.utils.BaseFragment
import com.example.moviereview.ui.utils.DataStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class MainFragment : BaseFragment(R.layout.fragment_main) {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding

    private var animationController: LayoutAnimationController? = null

    private val trendingMediaAdapter = GenericRvAdapter<UIBackdropMedia>()
    private val trendingMediaClickListener = object : GenericRvAdapter.GenericItemListener<UIBackdropMedia> {
        override fun onClick(item: UIBackdropMedia) {
            if (item.mediaType == "movie")
                findNavController().navigate(R.id.movieDetailFragment, bundleOf("movieId" to item.id))
            else if (item.mediaType == "tv")
                findNavController().navigate(R.id.tvShowDetailFragment, bundleOf("tvShowId" to item.id))
        }
    }

    private val popularMoviesAdapter = GenericRvAdapter<UIPosterMedia>()
    private val popularMoviesClickListener = object : GenericRvAdapter.GenericItemListener<UIPosterMedia> {
        override fun onClick(item: UIPosterMedia) {
            findNavController().navigate(R.id.movieDetailFragment, bundleOf("movieId" to item.id))
        }
    }

    private val popularTvShowsAdapter = GenericRvAdapter<UIPosterMedia>()
    private val popularTvShowsClickListener = object : GenericRvAdapter.GenericItemListener<UIPosterMedia> {
        override fun onClick(item: UIPosterMedia) {
            findNavController().navigate(R.id.tvShowDetailFragment, bundleOf("tvShowId" to item.id))
        }
    }

    override fun bindingLayout(binding: ViewDataBinding?) {
        this.binding = binding as FragmentMainBinding
    }

    override fun initializeObservers() {
        mainViewModel.trendingMediaToday.observe(viewLifecycleOwner, {
            binding.trendingStatus = it.status

            when (it.status) {
                DataStatus.Status.LOADING -> {
                }
                DataStatus.Status.SUCCESS -> {
                    trendingMediaAdapter.setItems(it.data ?: listOf())
                    rvTrendingMedia.scheduleLayoutAnimation()
                }
                DataStatus.Status.ERROR -> {
                }
                DataStatus.Status.EMPTY -> {
                }
            }
        })

        mainViewModel.popularMovies.observe(viewLifecycleOwner, {
            binding.popularMoviesStatus = it.status

            when (it.status) {
                DataStatus.Status.LOADING -> {
                }
                DataStatus.Status.SUCCESS -> {
                    popularMoviesAdapter.setItems(it.data ?: listOf())
                    rvPopularMovies.scheduleLayoutAnimation()
                }
                DataStatus.Status.ERROR -> {
                }
                DataStatus.Status.EMPTY -> {
                }
            }
        })

        mainViewModel.popularTvShows.observe(viewLifecycleOwner, {
            binding.popularShowsStatus = it.status

            when (it.status) {
                DataStatus.Status.LOADING -> {
                }
                DataStatus.Status.SUCCESS -> {
                    popularTvShowsAdapter.setItems(it.data ?: listOf())
                    rvPopularTvShows.scheduleLayoutAnimation()
                }
                DataStatus.Status.ERROR -> {
                }
                DataStatus.Status.EMPTY -> {
                }
            }
        })

        mainViewModel.viewState.observe(viewLifecycleOwner, {
            binding.viewStatus = it
        })
    }

    override fun initializeViews() {
        animationController = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_slide_right)

        trendingMediaAdapter.setGenericItemListener(trendingMediaClickListener)
        rvTrendingMedia.adapter = trendingMediaAdapter
        rvTrendingMedia.layoutAnimation = animationController

        popularMoviesAdapter.setGenericItemListener(popularMoviesClickListener)
        rvPopularMovies.adapter = popularMoviesAdapter
        rvPopularMovies.layoutAnimation = animationController

        popularTvShowsAdapter.setGenericItemListener(popularTvShowsClickListener)
        rvPopularTvShows.adapter = popularTvShowsAdapter
        rvPopularTvShows.layoutAnimation = animationController
    }
}