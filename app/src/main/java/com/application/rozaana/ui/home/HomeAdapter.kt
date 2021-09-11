package com.application.rozaana.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.application.rozaana.model.PexelData
import com.application.rozaana.ui.home.viewholders.MediaViewHolder
import com.application.rozaana.util.diffutil.XDiffCallback

class HomeAdapter(
    val context: Context
) : ListAdapter<Any, RecyclerView.ViewHolder>(XDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            MediaViewHolder.LAYOUT -> {
                viewHolder = MediaViewHolder.create(inflater, parent)
            }
        }

        return viewHolder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        when (holder) {
            is MediaViewHolder -> holder.bind(item as PexelData.Media)
        }
    }

    override fun getItemViewType(position: Int): Int {

        if (position >= 0) {
            when (getItem(position)) {
                is PexelData.Media -> return MediaViewHolder.LAYOUT
            }
        }
        return super.getItemViewType(position)
    }
}
