package com.example.imgvu.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imgvu.data.ImageDetail
import com.example.imgvu.databinding.ImgSearchItemBinding

class FavImgVuAdapter : RecyclerView.Adapter<FavImgVuAdapter.FavImgViewHolder>() {



    class FavImgViewHolder(private val binding: ImgSearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(image: ImageDetail){
            binding.apply {
                Glide.with(itemView)
                    .load(image.urls.regular)
                    .centerCrop()
                    .into(searchedImgView)

                tvUserName.text = image.user.username
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavImgViewHolder {
        val binding =
            ImgSearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavImgViewHolder(binding)
    }


    override fun onBindViewHolder(holder: FavImgViewHolder, position: Int) {
        val currentItem = differ.currentList[position]

        if (currentItem != null){
            holder.bind(currentItem)
        }
    }

    private val differCallBack = object : DiffUtil.ItemCallback<ImageDetail>() {
        override fun areItemsTheSame(oldItem: ImageDetail, newItem: ImageDetail): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ImageDetail, newItem: ImageDetail): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}