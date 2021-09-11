package com.application.rozaana.util.diffutil

import androidx.recyclerview.widget.DiffUtil

class XDiffCallback : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(old: Any, new: Any): Boolean {
        return false
    }

    override fun areContentsTheSame(old: Any, new: Any): Boolean {
        return false
    }
}
