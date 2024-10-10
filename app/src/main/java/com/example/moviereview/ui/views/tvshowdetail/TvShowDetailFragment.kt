package com.example.moviereview.ui.views.tvshowdetail

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import com.example.moviereview.R
import com.example.moviereview.databinding.FragmentTvShowDetailBinding
import com.example.moviereview.ui.adapters.ViewPagerAdapter
import com.example.moviereview.ui.utils.BaseFragment
import com.example.moviereview.ui.utils.DataStatus
import com.example.moviereview.ui.views.tvshowdetail.information.TvShowDetailInformation
import com.example.moviereview.ui.views.tvshowdetail.seasons.TvShowDetailSeasonsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_tv_show_detail.*

@AndroidEntryPoint
class TvShowDetailFragment : BaseFragment(R.layout.fragment_tv_show_detail) {
    private val viewModel: TvShowDetailViewModel by viewModels()
    private lateinit var binding: FragmentTvShowDetailBinding
    private var tvShowId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        tvShowId = arguments?.let { it.getInt("tvShowId") } ?: -1
        viewModel.getTvShowDetailInfo(tvShowId)
        super.onCreate(savedInstanceState)
    }

    override fun bindingLayout(binding: ViewDataBinding?) {
        this.binding = binding as FragmentTvShowDetailBinding
    }

    override fun initializeViews() {
        val button = tvShowDetailError.findViewById<Button>(R.id.errorBtnTryAgain)
        button.setOnClickListener {
            viewModel.getTvShowDetailInfo(tvShowId)
        }

        fbSaveTvShow.setOnClickListener {
            viewModel.saveRemoveTvShow()
        }
    }

    override fun initializeObservers() {
        viewModel.tvShowDetailInfo.observe(viewLifecycleOwner, {
            binding.viewStatus = it.status

            when (it.status) {
                DataStatus.Status.LOADING -> {
                }
                DataStatus.Status.SUCCESS -> {
                    it.data?.let { tvShowDetail ->
                        (activity as AppCompatActivity).supportActionBar?.title = tvShowDetail.name

                        val viewPagerAdapter = ViewPagerAdapter(childFragmentManager).apply {
                            addPage(getString(R.string.title_information), TvShowDetailInformation.newInstance(tvShowDetail))
                            addPage(getString(R.string.title_seasons), TvShowDetailSeasonsFragment.newInstance(tvShowDetail))
                        }

                        tvShowDetailTabLayout.setupWithViewPager(tvShowDetailViewPager)
                        tvShowDetailViewPager.adapter = viewPagerAdapter

                        binding.isSaved = tvShowDetail.saved
                        binding.data = tvShowDetail
                    }
                }
                DataStatus.Status.ERROR -> {
                }
                DataStatus.Status.EMPTY -> {
                }
            }
        })

        viewModel.isTvShowsSaved.observe(viewLifecycleOwner, {
            binding.isSaved = it
        })
    }
}