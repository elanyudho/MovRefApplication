package com.elanyudho.movrefapplication.ui.moremovie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.elanyudho.core.abstraction.BaseViewHolder
import com.elanyudho.core.abstraction.PagingRecyclerviewAdapter
import com.elanyudho.core.domain.model.MovieItem
import com.elanyudho.movrefapplication.databinding.ItemMovieGridBinding
import com.elanyudho.core.extension.glide

class MoreMovieAdapter: PagingRecyclerviewAdapter<MoreMovieAdapter.MoreMovieViewHolder, MovieItem>() {

    private lateinit var onClick: (data: MovieItem) -> Unit

    inner class MoreMovieViewHolder(itemView: ItemMovieGridBinding) :
        BaseViewHolder<MovieItem, ItemMovieGridBinding>(itemView) {
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

    override val holderInflater: (LayoutInflater, ViewGroup, Boolean) -> MoreMovieViewHolder
        get() = { inflater, viewGroup, boolean ->
            MoreMovieViewHolder(
                ItemMovieGridBinding.inflate(
                    inflater,
                    viewGroup,
                    boolean
                )
            )
        }

    override fun onBindViewHolder(holder: MoreMovieViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    fun setOnClickData(onClick: (data: MovieItem) -> Unit) {
        this.onClick = onClick
    }
}