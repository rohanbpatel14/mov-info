package com.example.moviereview.ui.views.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.moviereview.R
import com.example.moviereview.databinding.FragmentMovieDetailBinding
import com.example.moviereview.ui.adapters.GenericRvAdapter
import com.example.moviereview.ui.models.UICast
import com.example.moviereview.ui.models.UIImage
import com.example.moviereview.ui.models.UIPosterMedia
import com.example.moviereview.ui.utils.BaseFragment
import com.example.moviereview.ui.utils.DataStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_detail.*

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment(R.layout.fragment_movie_detail) {
    private var animationController: LayoutAnimationController? = null
    private lateinit var binding: FragmentMovieDetailBinding

    private val viewModel: MovieDetailViewModel by viewModels()

    private val similarMoviesAdapter = GenericRvAdapter<UIPosterMedia>()
    private val similarMoviesClickListener =
        object : GenericRvAdapter.GenericItemListener<UIPosterMedia> {
            override fun onClick(item: UIPosterMedia) {
                findNavController().navigate(
                    R.id.movieDetailFragment,
                    bundleOf("movieId" to item.id)
                )
            }
        }

    private val movieCastAdapter = GenericRvAdapter<UICast>()
    private val movieImagesAdapter = GenericRvAdapter<UIImage>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val movieId = arguments?.let { it.getInt("movieId") } ?: -1
        viewModel.getMovieDetailInfo(movieId)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun bindingLayout(binding: ViewDataBinding?) {
        this.binding = binding as FragmentMovieDetailBinding
    }

    override fun initializeObservers() {
        viewModel.movieDetailInfo.observe(viewLifecycleOwner, {
            binding.viewStatus = it.status

            when (it.status) {
                DataStatus.Status.LOADING -> {
                }
                DataStatus.Status.SUCCESS -> {
                    it.data?.let { movieDetail ->
                        (activity as AppCompatActivity).supportActionBar?.title = movieDetail.name

                        similarMoviesAdapter.setItems(movieDetail.similar)
                        movieImagesAdapter.setItems(movieDetail.images)
                        movieCastAdapter.setItems(movieDetail.cast)

                        binding.isSaved = movieDetail.saved
                        binding.data = movieDetail
                    }
                }
                DataStatus.Status.ERROR -> {
                }
                DataStatus.Status.EMPTY -> {
                }
            }
        })

        viewModel.isMovieSaved.observe(viewLifecycleOwner, {
            binding.isSaved = it
        })
    }

    override fun initializeViews() {
        animationController = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_slide_right)

        similarMoviesAdapter.setGenericItemListener(similarMoviesClickListener)
        rvMovieDetailSimilar.layoutAnimation = animationController
        rvMovieDetailSimilar.adapter = similarMoviesAdapter

        rvMovieDetailCast.layoutAnimation = animationController
        rvMovieDetailCast.adapter = movieCastAdapter

        rvMovieDetailImages.layoutAnimation = animationController
        rvMovieDetailImages.adapter = movieImagesAdapter

        fbSaveMovie.setOnClickListener {
            viewModel.saveRemoveMovie()
        }
    }
}