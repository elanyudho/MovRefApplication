package com.elanyudho.movrefapplication.ui.detailpeople.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.elanyudho.core.abstraction.BaseRecyclerViewAdapter
import com.elanyudho.core.abstraction.BaseViewHolder
import com.elanyudho.core.domain.model.PeopleImage
import com.elanyudho.movrefapplication.databinding.ItemPhotosBinding
import com.elanyudho.core.extension.glide

class PhotosAdapter: BaseRecyclerViewAdapter<PhotosAdapter.PhotosViewHolder>() {

    private var listData = mutableListOf<PeopleImage>()

    private lateinit var onClick: (data: PeopleImage) -> Unit

    fun submitList(newList: List<PeopleImage>) {
        listData.clear()
        listData.addAll(newList)
        notifyDataSetChanged()
    }

    inner class PhotosViewHolder(itemView: ItemPhotosBinding) :
        BaseViewHolder<PeopleImage, ItemPhotosBinding>(itemView) {
        @SuppressLint("SetTextI18n")
        override fun bind(data: PeopleImage) {
            with(binding) {

                ivImagePeople.glide(itemView, data.peopleImage?: "")

                root.setOnClickListener {
                    onClick.invoke(data)
                }
            }
        }

    }

    override val holderInflater: (LayoutInflater, ViewGroup, Boolean) -> PhotosViewHolder
        get() = { inflater, viewGroup, boolean ->
            PhotosViewHolder(
                ItemPhotosBinding.inflate(
                    inflater,
                    viewGroup,
                    boolean
                )
            )
        }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int {
        return if (listData.size > 9) {
            9
        } else {
            listData.size
        }
    }

    fun setOnClickData(onClick: (data: PeopleImage) -> Unit) {
        this.onClick = onClick
    }
}