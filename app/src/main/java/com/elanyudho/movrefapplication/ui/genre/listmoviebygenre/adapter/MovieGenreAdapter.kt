package com.elanyudho.movrefapplication.ui.genre.listmoviebygenre.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.elanyudho.core.abstraction.BaseViewHolder
import com.elanyudho.core.abstraction.PagingRecyclerviewAdapter
import com.elanyudho.movrefapplication.databinding.ItemMovieGridBinding
import com.elanyudho.movrefapplication.domain.model.MovieItem
import com.elanyudho.movrefapplication.utils.extensions.glide

class MovieGenreAdapter: PagingRecyclerviewAdapter<MovieGenreAdapter.MovieGenreViewHolder, MovieItem>() {

    private lateinit var onClick: (data: MovieItem) -> Unit

    inner class MovieGenreViewHolder(itemView: ItemMovieGridBinding) :
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

    override val holderInflater: (LayoutInflater, ViewGroup, Boolean) -> MovieGenreViewHolder
        get() = { inflater, viewGroup, boolean ->
            MovieGenreViewHolder(
                ItemMovieGridBinding.inflate(
                    inflater,
                    viewGroup,
                    boolean
                )
            )
        }

    override fun onBindViewHolder(holder: MovieGenreViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    fun setOnClickData(onClick: (data: MovieItem) -> Unit) {
        this.onClick = onClick
    }
}