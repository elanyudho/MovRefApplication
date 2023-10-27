package com.elanyudho.movrefapplication.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.elanyudho.core.abstraction.BaseRecyclerViewAdapter
import com.elanyudho.core.abstraction.BaseViewHolder
import com.elanyudho.core.domain.model.MovieItem
import com.elanyudho.movrefapplication.databinding.ItemMovieLinearHorizontalBinding
import com.elanyudho.core.extension.glide

class MainMovieAdapter: BaseRecyclerViewAdapter<MainMovieAdapter.MainMovieViewHolder>() {

    private var listData = mutableListOf<MovieItem>()

    private lateinit var onClick: (data: MovieItem) -> Unit

    fun submitList(newList: List<MovieItem>) {
        listData.clear()
        listData.addAll(newList)
        notifyDataSetChanged()
    }

    fun clear() {
        listData.clear()
    }

    inner class MainMovieViewHolder(itemView: ItemMovieLinearHorizontalBinding) :
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

    override val holderInflater: (LayoutInflater, ViewGroup, Boolean) -> MainMovieViewHolder
        get() = { inflater, viewGroup, boolean ->
            MainMovieViewHolder(
                ItemMovieLinearHorizontalBinding.inflate(
                    inflater,
                    viewGroup,
                    boolean
                )
            )
        }

    override fun onBindViewHolder(holder: MainMovieViewHolder, position: Int) {
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