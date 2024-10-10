package com.example.moviereview.ui.views.discover

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.moviereview.R
import com.example.moviereview.databinding.FragmentDiscoverBinding
import com.example.moviereview.ui.adapters.GenericRvAdapter
import com.example.moviereview.ui.models.UIFilter
import com.example.moviereview.ui.models.UIMedia
import com.example.moviereview.ui.utils.BaseFragment
import com.example.moviereview.ui.utils.DataStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_discover.*

@AndroidEntryPoint
class DiscoverFragment : BaseFragment(R.layout.fragment_discover) {
    private var toolbarMenu: Menu? = null
    private lateinit var binding: FragmentDiscoverBinding
    private var animationController: LayoutAnimationController? = null

    private val discoverViewModel: DiscoverViewModel by viewModels()

    private val typeFilterOptionsAdapter = GenericRvAdapter<UIFilter>()
    private val typeFilterOptionsClickListener = object : GenericRvAdapter.GenericItemListener<UIFilter> {
        override fun onClick(item: UIFilter) {
            discoverViewModel.selectedType(item)
        }
    }

    private val typeGenreOptionsAdapter = GenericRvAdapter<UIFilter>()
    private val typeGenreOptionsClickListener = object : GenericRvAdapter.GenericItemListener<UIFilter> {
        override fun onClick(item: UIFilter) {
            discoverViewModel.selectedGenre(item)
        }
    }

    private val typeNetworkOptionsAdapter = GenericRvAdapter<UIFilter>()
    private val typeNetworkOptionsClickListener = object : GenericRvAdapter.GenericItemListener<UIFilter> {
        override fun onClick(item: UIFilter) {
            discoverViewModel.selectedNetwork(item)
        }
    }

    private val discoverMediaAdapter = GenericRvAdapter<UIMedia>()
    private val discoverMediaClickListener = object : GenericRvAdapter.GenericItemListener<UIMedia> {
        override fun onClick(item: UIMedia) {
            if (item.type == "movie")
                findNavController().navigate(R.id.movieDetailFragment, bundleOf("movieId" to item.id))
            else if (item.type == "tv")
                findNavController().navigate(R.id.tvShowDetailFragment, bundleOf("tvShowId" to item.id))
            discoverViewModel.hideFilters()
        }
    }

    private val discoverMediaLazyLoadListener = GenericRvAdapter.LazyLoadListener {
        discoverViewModel.retrieveNewMedia()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun bindingLayout(binding: ViewDataBinding?) {
        this.binding = binding as FragmentDiscoverBinding
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.discover_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        toolbarMenu = menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.discover_action_filter -> {
                discoverViewModel.showHideFilters()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun initializeViews() {
        animationController = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)

        typeFilterOptionsAdapter.setGenericItemListener(typeFilterOptionsClickListener)
        rvTypeFilterOptions.adapter = typeFilterOptionsAdapter

        typeGenreOptionsAdapter.setGenericItemListener(typeGenreOptionsClickListener)
        rvGenreFilterOptions.adapter = typeGenreOptionsAdapter

        typeNetworkOptionsAdapter.setGenericItemListener(typeNetworkOptionsClickListener)
        rvNetworkFilterOptions.adapter = typeNetworkOptionsAdapter

        discoverMediaAdapter.setLazyLoadListener(discoverMediaLazyLoadListener)
        discoverMediaAdapter.setGenericItemListener(discoverMediaClickListener)
        discoverMediaAdapter.setItemsNeedToLoad(5)

        rvDiscoverMedia.layoutAnimation = animationController
        rvDiscoverMedia.adapter = discoverMediaAdapter

        btnCloseFilters.setOnClickListener {
            discoverViewModel.showHideFilters()
        }

        btnSearchFilters.setOnClickListener {
            discoverViewModel.searchFilters()
        }
    }

    override fun initializeObservers() {
        discoverViewModel.showFilters.observe(viewLifecycleOwner, {
            val searchMenuItem = toolbarMenu?.findItem(R.id.discover_action_filter)
            if (it) {
                context?.let { ctx -> searchMenuItem?.icon = ContextCompat.getDrawable(ctx, R.drawable.ic_close) }
                filterMenu.layoutAnimation = animationController
                filterMenu.visibility = View.VISIBLE
            } else {
                context?.let { ctx -> searchMenuItem?.icon = ContextCompat.getDrawable(ctx, R.drawable.ic_filter) }
                filterMenu.visibility = View.GONE
            }
        })

        discoverViewModel.discoverMedia.observe(viewLifecycleOwner, {
            binding.viewStatus = it.status
            when (it.status) {
                DataStatus.Status.LOADING -> {
                }
                DataStatus.Status.SUCCESS -> {
                    it.data?.let { discoverMediaAdapter.setItems(it) }
                    rvDiscoverMedia.scheduleLayoutAnimation()
                }
                DataStatus.Status.ERROR -> {
                }
                DataStatus.Status.EMPTY -> {
                }
            }
        })

        discoverViewModel.updateMedia.observe(viewLifecycleOwner, {
            binding.viewStatus = it.status

            when (it.status) {
                DataStatus.Status.LOADING -> {
                }
                DataStatus.Status.SUCCESS -> {
                    it.data?.let { discoverMediaAdapter.appendItems(it) }
                }
                DataStatus.Status.ERROR -> {
                }
                DataStatus.Status.EMPTY -> {
                }
            }
        })

        discoverViewModel.filterTypeList.observe(viewLifecycleOwner, {
            typeFilterOptionsAdapter.setItems(it)
        })

        discoverViewModel.filterGenreList.observe(viewLifecycleOwner, {
            typeGenreOptionsAdapter.setItems(it)
        })

        discoverViewModel.filterNetworkList.observe(viewLifecycleOwner, {
            typeNetworkOptionsAdapter.setItems(it)
        })

        discoverViewModel.scrollToTop.observe(viewLifecycleOwner, {
            if (it) rvDiscoverMedia.scrollToPosition(0)
        })
    }
}