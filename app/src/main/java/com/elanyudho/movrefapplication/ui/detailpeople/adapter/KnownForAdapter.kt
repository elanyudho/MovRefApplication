package com.elanyudho.movrefapplication.ui.detailpeople.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.elanyudho.core.abstraction.BaseRecyclerViewAdapter
import com.elanyudho.core.abstraction.BaseViewHolder
import com.elanyudho.movrefapplication.databinding.ItemKnownForBinding
import com.elanyudho.movrefapplication.domain.model.CreditsPeople
import com.elanyudho.movrefapplication.utils.extensions.glide

class KnownForAdapter: BaseRecyclerViewAdapter<KnownForAdapter.KnownAdapterViewHolder>() {

    private var listData = mutableListOf<CreditsPeople>()

    private lateinit var onClick: (data: CreditsPeople) -> Unit

    fun submitList(newList: List<CreditsPeople>) {
        listData.clear()
        listData.addAll(newList)
        notifyDataSetChanged()
    }

    inner class KnownAdapterViewHolder(itemView: ItemKnownForBinding) :
        BaseViewHolder<CreditsPeople, ItemKnownForBinding>(itemView) {
        @SuppressLint("SetTextI18n")
        override fun bind(data: CreditsPeople) {
            with(binding) {

                tvMovieName.text = data.movieName
                tvCharacter.text = "as " + data.character
                ivPosterMovie.glide(itemView, data.movieImage?: "")

                root.setOnClickListener {
                    onClick.invoke(data)
                }
            }
        }

    }

    override val holderInflater: (LayoutInflater, ViewGroup, Boolean) -> KnownAdapterViewHolder
        get() = { inflater, viewGroup, boolean ->
            KnownAdapterViewHolder(
                ItemKnownForBinding.inflate(
                    inflater,
                    viewGroup,
                    boolean
                )
            )
        }

    override fun onBindViewHolder(holder: KnownAdapterViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int {
        return if (listData.size > 9) {
            9
        } else {
            listData.size
        }
    }

    fun setOnClickData(onClick: (data: CreditsPeople) -> Unit) {
        this.onClick = onClick
    }
}