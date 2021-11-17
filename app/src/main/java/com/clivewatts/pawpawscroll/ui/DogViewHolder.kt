package com.clivewatts.pawpawscroll.ui

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.clivewatts.pawpawscroll.databinding.DogListItemBinding

class DogViewHolder(private val binding: DogListItemBinding, val callback: DogActionsInterface, private val context : Context ) :
    RecyclerView.ViewHolder(binding.root) {

    init {

    }

    fun bind(url : String?) {
        binding.apply {
            Glide.with(context)
                .load(url)
                .addListener(ImageLoadingListener(this.imagePendingAnimation, this.buttonContainer))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(this.listItemImage);
            binding.listItemImage.setOnClickListener(View.OnClickListener {
                url?.let { it1 -> callback.onImageTapped(it1) }
            })
            binding.savePupper.setOnClickListener {
                url?.let { urlz -> callback.onSaveTapped(urlz) }
            }
            binding.sharePupper.setOnClickListener {
                url?.let { urlx -> callback.onShareTapped(urlx) }
            }
        }
    }


}