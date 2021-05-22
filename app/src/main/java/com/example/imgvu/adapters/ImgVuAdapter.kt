package com.example.imgvu.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.imgvu.R
import com.example.imgvu.data.ImageDetail
import com.example.imgvu.databinding.ImgSearchItemBinding

class ImgVuAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<ImageDetail, ImgVuAdapter.SearchImgViewHolder>(
        differ
    ) {

    inner class SearchImgViewHolder(private val binding: ImgSearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClicked(item)
                    }
                }
            }
        }

        fun bind(photo: ImageDetail) {
            binding.apply {
                Glide.with(itemView)
                    .load(photo.urls.regular)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_img_not_found)
                    .into(searchedImgView)

                tvUserName.text = photo.user.username
            }
        }
    }


    override fun onBindViewHolder(holder: SearchImgViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchImgViewHolder {
        val binding =
            ImgSearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchImgViewHolder(binding)
    }

    interface OnItemClickListener {
        fun onItemClicked(photo: ImageDetail)
    }

    companion object {
        val differ = object : DiffUtil.ItemCallback<ImageDetail>() {
            override fun areItemsTheSame(oldItem: ImageDetail, newItem: ImageDetail): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ImageDetail, newItem: ImageDetail): Boolean {
                return oldItem == newItem
            }

        }
    }
}