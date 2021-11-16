package com.clivewatts.pawpawscroll.utils

import android.view.ViewGroup
import android.view.Gravity
import com.bumptech.glide.load.engine.DiskCacheStrategy
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import android.content.Context
import android.widget.ProgressBar
import android.widget.ImageButton
import com.google.android.material.internal.ViewUtils.getContentView
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import androidx.annotation.Nullable
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.request.target.Target
import com.clivewatts.pawpawscroll.R
import com.jsibbold.zoomage.ZoomageView


class FullPhotoPopupWindow(
    ctx: Context,
    layout: Int,
    v: View?,
    imageUrl: String?,
    bitmap: Bitmap?
) :
    PopupWindow(
        (ctx.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
            R.layout.fullscreen_popup_image,
            null
        ), ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    ) {
    var view: View
    var mContext: Context
    var photoView: ZoomageView
    var loading: ProgressBar
    var parent: ViewGroup
    fun onPalette(palette: Palette?) {
        if (null != palette) {
            val parent = photoView.getParent().getParent() as ViewGroup
            parent.setBackgroundColor(palette.getDarkVibrantColor(Color.GRAY))
        }
    }

    companion object {
        private val instance: FullPhotoPopupWindow? = null
    }

    init {
        if (Build.VERSION.SDK_INT >= 21) {
            elevation = 5.0f
        }
        mContext = ctx
        view = contentView
        val closeButton = view.findViewById(R.id.ib_close) as ImageButton
        isOutsideTouchable = true
        isFocusable = true
        // Set a click listener for the popup window close button
        closeButton.setOnClickListener { // Dismiss the popup window
            dismiss()
        }
        //---------Begin customising this popup--------------------
        photoView = view.findViewById(R.id.image) as ZoomageView
        loading = view.findViewById(R.id.loading)
        parent = photoView.parent as ViewGroup
        // ImageUtils.setZoomable(imageView);
        //----------------------------
        if (bitmap != null) {
            loading.visibility = View.GONE
            if (Build.VERSION.SDK_INT >= 16) {
                parent.background = BitmapDrawable(
                    mContext.getResources(),
                    Constants.fastblur(Bitmap.createScaledBitmap(bitmap, 50, 50, true))
                ) // ));
            } else {
                onPalette(Palette.from(bitmap).generate())
            }
            photoView.setImageBitmap(bitmap)
        } else {
            loading.isIndeterminate = true
            loading.visibility = View.VISIBLE
            Glide.with(ctx).asBitmap()
                .load(imageUrl)
                .listener(object : RequestListener<Bitmap?> {

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Bitmap?>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        loading.isIndeterminate = false
                        loading.setBackgroundColor(Color.LTGRAY)
                        return false
                    }

                    override fun onResourceReady(
                        resource: Bitmap?,
                        model: Any?,
                        target: Target<Bitmap?>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        if (Build.VERSION.SDK_INT >= 16) {
                            parent.background = BitmapDrawable(
                                mContext.getResources(), Constants.fastblur(
                                    Bitmap.createScaledBitmap(
                                        resource!!, 50, 50, true
                                    )
                                )
                            ) // ));
                        } else {
                            onPalette(resource?.let { Palette.from(it).generate() })
                        }
                        photoView.setImageBitmap(resource)
                        loading.visibility = View.GONE
                        return false
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(photoView)
            showAtLocation(v, Gravity.CENTER, 0, 0)
        }
        //------------------------------
    }
}