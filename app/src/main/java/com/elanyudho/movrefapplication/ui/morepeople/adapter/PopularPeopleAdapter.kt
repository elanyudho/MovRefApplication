package com.elanyudho.movrefapplication.ui.morepeople.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.elanyudho.core.abstraction.BaseViewHolder
import com.elanyudho.core.abstraction.PagingRecyclerviewAdapter
import com.elanyudho.core.domain.model.PeopleItem
import com.elanyudho.movrefapplication.databinding.ItemPeopleLinearVerticalBinding
import com.elanyudho.core.extension.glide

class PopularPeopleAdapter: PagingRecyclerviewAdapter<PopularPeopleAdapter.PopularPeopleViewHolder, PeopleItem>() {

    private lateinit var onClick: (data: PeopleItem) -> Unit

    inner class PopularPeopleViewHolder(itemView: ItemPeopleLinearVerticalBinding) :
        BaseViewHolder<PeopleItem, ItemPeopleLinearVerticalBinding>(itemView) {
        override fun bind(data: PeopleItem) {
            with(binding) {

                tvPeopleName.text = data.peopleName
                tvPeopleMovie.text = data.peopleMovie?.joinToString(", ")
                ivPosterPeople.glide(itemView, data.peopleImage?: "")

                root.setOnClickListener {
                    onClick.invoke(data)
                }
            }
        }

    }

    override val holderInflater: (LayoutInflater, ViewGroup, Boolean) -> PopularPeopleViewHolder
        get() = { inflater, viewGroup, boolean ->
            PopularPeopleViewHolder(
                ItemPeopleLinearVerticalBinding.inflate(
                    inflater,
                    viewGroup,
                    boolean
                )
            )
        }

    override fun onBindViewHolder(holder: PopularPeopleViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    fun setOnClickData(onClick: (data: PeopleItem) -> Unit) {
        this.onClick = onClick
    }
}