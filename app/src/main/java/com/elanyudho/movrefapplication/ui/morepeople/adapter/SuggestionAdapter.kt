package com.elanyudho.movrefapplication.ui.morepeople.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.elanyudho.core.abstraction.BaseRecyclerViewAdapter
import com.elanyudho.core.abstraction.BaseViewHolder
import com.elanyudho.movrefapplication.databinding.ItemSearchSuggestionBinding
import com.elanyudho.movrefapplication.domain.model.PeopleItem

class SuggestionAdapter: BaseRecyclerViewAdapter<SuggestionAdapter.SuggestionViewHolder>() {

    var listData = mutableListOf<PeopleItem>()

    private lateinit var onClick: (data: PeopleItem) -> Unit

    fun submitList(newList: List<PeopleItem>) {
        listData.clear()
        listData.addAll(newList)
        notifyDataSetChanged()
    }

    inner class SuggestionViewHolder(itemView: ItemSearchSuggestionBinding) :
        BaseViewHolder<PeopleItem, ItemSearchSuggestionBinding>(itemView) {
        override fun bind(data: PeopleItem) {
            with(binding) {

                tvSuggestionTitle.text = data.peopleName

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

    fun setOnClickData(onClick: (data: PeopleItem) -> Unit) {
        this.onClick = onClick
    }
}