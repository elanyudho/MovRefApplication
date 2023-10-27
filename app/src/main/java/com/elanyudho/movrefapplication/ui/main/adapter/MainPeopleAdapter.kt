package com.elanyudho.movrefapplication.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.elanyudho.core.abstraction.BaseRecyclerViewAdapter
import com.elanyudho.core.abstraction.BaseViewHolder
import com.elanyudho.core.domain.model.PeopleItem
import com.elanyudho.movrefapplication.databinding.ItemPeopleLinearVerticalBinding
import com.elanyudho.core.extension.glide

class MainPeopleAdapter: BaseRecyclerViewAdapter<MainPeopleAdapter.MainPeopleViewHolder>() {

    private var listData = mutableListOf<PeopleItem>()

    private lateinit var onClick: (data: PeopleItem) -> Unit

    fun submitList(newList: List<PeopleItem>) {
        listData.clear()
        listData.addAll(newList)
        notifyDataSetChanged()
    }

    inner class MainPeopleViewHolder(itemView: ItemPeopleLinearVerticalBinding) :
        BaseViewHolder<PeopleItem, ItemPeopleLinearVerticalBinding>(itemView) {
        override fun bind(data: PeopleItem) {
            with(binding) {

                tvPeopleName.text = data.peopleName
                tvPeopleMovie.text = if (data.peopleMovie?.isEmpty() == true) { "Not Available" } else { data.peopleMovie?.joinToString(", ") }
                ivPosterPeople.glide(itemView, data.peopleImage?: "")

                root.setOnClickListener {
                    onClick.invoke(data)
                }
            }
        }

    }

    override val holderInflater: (LayoutInflater, ViewGroup, Boolean) -> MainPeopleViewHolder
        get() = { inflater, viewGroup, boolean ->
            MainPeopleViewHolder(
                ItemPeopleLinearVerticalBinding.inflate(
                    inflater,
                    viewGroup,
                    boolean
                )
            )
        }

    override fun onBindViewHolder(holder: MainPeopleViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int {
        return if (listData.size > 9) {
            9
        } else {
            listData.size
        }
    }

    fun setOnClickData(onClick: (data: PeopleItem) -> Unit) {
        this.onClick = onClick
    }
}