package com.elanyudho.movrefapplication.ui.detailmovie.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.elanyudho.core.abstraction.BaseRecyclerViewAdapter
import com.elanyudho.core.abstraction.BaseViewHolder
import com.elanyudho.movrefapplication.databinding.ItemCastBinding
import com.elanyudho.movrefapplication.domain.model.CreditsMovie
import com.elanyudho.movrefapplication.utils.extensions.glide

class CastAdapter: BaseRecyclerViewAdapter<CastAdapter.CastViewHolder>() {

    private var listData = mutableListOf<CreditsMovie>()

    private lateinit var onClick: (data: CreditsMovie) -> Unit

    fun submitList(newList: List<CreditsMovie>) {
        listData.clear()
        listData.addAll(newList)
        notifyDataSetChanged()
    }

    inner class CastViewHolder(itemView: ItemCastBinding) :
        BaseViewHolder<CreditsMovie, ItemCastBinding>(itemView) {
        @SuppressLint("SetTextI18n")
        override fun bind(data: CreditsMovie) {
            with(binding) {

                tvPeopleName.text = data.peopleName
                tvCharacter.text = "as " + data.character
                ivImagePeople.glide(itemView, data.peopleImage?: "")

                root.setOnClickListener {
                    onClick.invoke(data)
                }
            }
        }

    }

    override val holderInflater: (LayoutInflater, ViewGroup, Boolean) -> CastViewHolder
        get() = { inflater, viewGroup, boolean ->
            CastViewHolder(
                ItemCastBinding.inflate(
                    inflater,
                    viewGroup,
                    boolean
                )
            )
        }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int {
        return if (listData.size > 9) {
            9
        } else {
            listData.size
        }
    }

    fun setOnClickData(onClick: (data: CreditsMovie) -> Unit) {
        this.onClick = onClick
    }
}