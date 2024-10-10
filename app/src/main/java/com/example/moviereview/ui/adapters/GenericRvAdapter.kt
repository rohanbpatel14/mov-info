package com.example.moviereview.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviereview.R
import com.example.moviereview.ui.models.*

class GenericRvAdapter<T> : RecyclerView.Adapter<BaseViewHolder>() {
    private var genericItemClickListener: GenericItemListener<T>? = null
    private var lazyLoadListener: LazyLoadListener? = null
    private var items: List<T> = mutableListOf()

    private var lazyLoadEnabled: Boolean = false
    private var needItemsToLoad: Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder = BaseViewHolder.from(parent, viewType)

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (lazyLoadEnabled && position == items.size - needItemsToLoad) {
            lazyLoadListener?.onLoadNeeded()
        }

        holder.bind(items[position], genericItemClickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is UICast -> R.layout.item_cast
            is UIImage -> R.layout.item_image
            is UIPosterMedia -> R.layout.item_poster
            is UIBackdropMedia -> R.layout.item_backdrop
            is UISeason -> R.layout.item_season
            is UIEpisode -> R.layout.item_episode
            is UIMedia -> R.layout.item_media
            is UIFilter -> R.layout.item_filter
            else -> throw NotImplementedError("Item type not accepted")
        }
    }

    fun setItems(items: List<T>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun appendItems(listOfItems: List<T>) {
        items = items + listOfItems
        notifyItemInserted(items.size - listOfItems.size)
    }

    fun setItemsNeedToLoad(items: Int) {
        needItemsToLoad = items
        lazyLoadEnabled = true
    }

    fun enableLazyLoad() {
        lazyLoadEnabled = true
    }

    fun disableLazyLoad() {
        lazyLoadEnabled = false
    }

    fun setGenericItemListener(genericItemListener: GenericItemListener<T>) {
        this.genericItemClickListener = genericItemListener
    }

    fun setLazyLoadListener(lazyLoadListener: LazyLoadListener) {
        this.lazyLoadListener = lazyLoadListener
    }

    interface GenericItemListener<T> {
        fun onClick(item: T)
    }

    class LazyLoadListener(val loadListener: () -> Unit) {
        fun onLoadNeeded() = loadListener()
    }

}