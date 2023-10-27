package com.elanyudho.movrefapplication.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.elanyudho.core.abstraction.BaseRecyclerViewAdapter
import com.elanyudho.core.abstraction.BaseViewHolder
import com.elanyudho.core.domain.model.MovieItem
import com.elanyudho.movrefapplication.databinding.ItemSearchSuggestionBinding

class SuggestionAdapter: BaseRecyclerViewAdapter<SuggestionAdapter.SuggestionViewHolder>() {

    var listData = mutableListOf<MovieItem>()

    private lateinit var onClick: (data: MovieItem) -> Unit

    fun submitList(newList: List<MovieItem>) {
        listData.clear()
        listData.addAll(newList)
        notifyDataSetChanged()
    }

    fun clear() {
        listData.clear()
    }

    inner class SuggestionViewHolder(itemView: ItemSearchSuggestionBinding) :
        BaseViewHolder<MovieItem, ItemSearchSuggestionBinding>(itemView) {
        override fun bind(data: MovieItem) {
            with(binding) {

                tvSuggestionTitle.text = data.movieName

                root.setOnClickListener {
                    onClick.invoke(data)
                }
            }
        }

    }

    override val holderInflater: (LayoutInflater, ViewGroup, Boolean) -> SuggestionViewHolder
        get() = { inflater, viewGroup, boolean ->
            SuggestionViewHolder(
                ItemSearchSuggestionBinding.inflate(
                    inflater,
                    viewGroup,
                    boolean
                )
            )
        }

    override fun onBindViewHolder(holder: SuggestionViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int {
        return if (listData.size > 7) {
            7
        } else {
            listData.size
        }
    }

    fun setOnClickData(onClick: (data: MovieItem) -> Unit) {
        this.onClick = onClick
    }
}