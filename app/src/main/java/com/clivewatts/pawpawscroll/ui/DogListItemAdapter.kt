package com.clivewatts.pawpawscroll.ui

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.clivewatts.pawpawscroll.R
import com.clivewatts.pawpawscroll.databinding.DogListItemBinding
import android.graphics.drawable.Drawable
import android.widget.LinearLayout
import androidx.annotation.Nullable
import com.bumptech.glide.load.DataSource

import com.bumptech.glide.load.engine.GlideException

import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


class DogListItemAdapter(private var data: MutableList<String?>?, private val context : Context, private val callback: DogActionsInterface ) : RecyclerView.Adapter<DogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : DogViewHolder =
        DogViewHolder(DogListItemBinding.inflate(LayoutInflater.from(context)), callback, context)

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        data?.get(position).let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = data?.size!!

    fun updateDataSet(moreDogs: MutableList<String?>?) {
        val currentRange = data?.size
        data = moreDogs
        notifyDataSetChanged()
    }

}

fun ImageLoadingListener(pendingImage: LottieAnimationView, buttonContainer : LinearLayout): RequestListener<Drawable?>? {
    return object : RequestListener<Drawable?> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable?>?,
            isFirstResource: Boolean
        ): Boolean {
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable?>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            //hide the animation
            pendingImage.pauseAnimation()
            pendingImage.visibility = View.GONE
            buttonContainer.visibility = View.VISIBLE
            return false //let Glide handle everything else
        }
    }
}