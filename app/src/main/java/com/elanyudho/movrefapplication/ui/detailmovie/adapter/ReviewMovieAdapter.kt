package com.elanyudho.movrefapplication.ui.detailmovie.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.elanyudho.core.abstraction.BaseViewHolder
import com.elanyudho.core.abstraction.PagingRecyclerviewAdapter
import com.elanyudho.movrefapplication.databinding.ItemReviewBinding
import com.elanyudho.movrefapplication.domain.model.Review
import com.elanyudho.movrefapplication.utils.extensions.convertDate
import com.elanyudho.movrefapplication.utils.extensions.glide

class ReviewMovieAdapter: PagingRecyclerviewAdapter<ReviewMovieAdapter.ReviewMovieViewHolder, Review>() {

    inner class ReviewMovieViewHolder(itemView: ItemReviewBinding) :
        BaseViewHolder<Review, ItemReviewBinding>(itemView) {
        @SuppressLint("SetTextI18n")
        override fun bind(data: Review) {
            with(binding) {

                tvUsername.text = data.username
                tvRating.text = if (data.rating == -1) {"Not rated"} else {"Rating ${data.rating}/10"}
                ivImageUser.glide(itemView, data.avatar)
                tvComment.text = data.comment
                tvDateComment.text = data.created.convertDate()

            }
        }

    }

    override val holderInflater: (LayoutInflater, ViewGroup, Boolean) -> ReviewMovieViewHolder
        get() = { inflater, viewGroup, boolean ->
            ReviewMovieViewHolder(
                ItemReviewBinding.inflate(
                    inflater,
                    viewGroup,
                    boolean
                )
            )
        }

    override fun onBindViewHolder(holder: ReviewMovieViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

}