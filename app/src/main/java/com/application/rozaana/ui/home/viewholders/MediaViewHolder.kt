package com.application.rozaana.ui.home.viewholders

import android.graphics.ImageDecoder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.rozaana.R
import com.application.rozaana.baseui.BaseUiUtil
import com.application.rozaana.baseui.ImageUtil
import com.application.rozaana.databinding.ItemMediaBinding
import com.application.rozaana.model.PexelData
import com.bumptech.glide.Glide

class MediaViewHolder(
    val binding: ItemMediaBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        val LAYOUT = R.layout.item_media

        fun create(
            inflater: LayoutInflater,
            viewGroup: ViewGroup
        ): MediaViewHolder {
            val binding = DataBindingUtil.inflate<ItemMediaBinding>(
                inflater,
                LAYOUT,
                viewGroup,
                false
            )
            return MediaViewHolder(binding)
        }
    }

    fun bind(data: PexelData.Media) {
        binding.label.text = data.photographer
        Glide.with(itemView.context).load(data.src?.portrait).into(binding.imageView)
    }
}
