package com.elanyudho.movrefapplication.ui.detailmovie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.elanyudho.core.abstraction.BaseRecyclerViewAdapter
import com.elanyudho.core.abstraction.BaseViewHolder
import com.elanyudho.movrefapplication.databinding.ItemMovieLinearHorizontalBinding
import com.elanyudho.movrefapplication.domain.model.MovieItem
import com.elanyudho.movrefapplication.utils.extensions.glide

class RecommendationAdapter: BaseRecyclerViewAdapter<RecommendationAdapter.RecommendationViewHolder>() {

    private var listData = mutableListOf<MovieItem>()

    private lateinit var onClick: (data: MovieItem) -> Unit

    fun submitList(newList: List<MovieItem>) {
        listData.clear()
        listData.addAll(newList)
        notifyDataSetChanged()
    }

    inner class RecommendationViewHolder(itemView: ItemMovieLinearHorizontalBinding) :
        BaseViewHolder<MovieItem, ItemMovieLinearHorizontalBinding>(itemView) {
        override fun bind(data: MovieItem) {
            with(binding) {

                tvTitleMovie.text = data.movieName
                ivPosterMovie.glide(itemView, data.moviePoster?: "")

                root.setOnClickListener {
                    onClick.invoke(data)
                }
            }
        }

    }

    override val holderInflater: (LayoutInflater, ViewGroup, Boolean) -> RecommendationViewHolder
        get() = { inflater, viewGroup, boolean ->
            RecommendationViewHolder(
                ItemMovieLinearHorizontalBinding.inflate(
                    inflater,
                    viewGroup,
                    boolean
                )
            )
        }

    override fun onBindViewHolder(holder: RecommendationViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int {
        return if (listData.size > 6) {
            6
        } else {
            listData.size
        }
    }

    fun setOnClickData(onClick: (data: MovieItem) -> Unit) {
        this.onClick = onClick
    }
}