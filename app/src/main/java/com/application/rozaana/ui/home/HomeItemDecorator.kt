package com.application.rozaana.ui.home

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.application.rozaana.baseui.BaseUiUtil

class HomeItemDecorator(val context: Context) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        with(outRect) {
            val position = parent.getChildAdapterPosition(view)
            val itemType = parent.adapter!!.getItemViewType(position)

            val dp6 = BaseUiUtil.dpToPx(6)
            val dp10 = BaseUiUtil.dpToPx(10)
            val dp12 = BaseUiUtil.dpToPx(12)
            val dp14 = BaseUiUtil.dpToPx(14)
            val dp20 = BaseUiUtil.dpToPx(20)
            val dp24 = BaseUiUtil.dpToPx(24)
            val dp75 = BaseUiUtil.dpToPx(75)

            val pageDefaultHorizontalDimen = dp24
            val pageDefaultVerticalDimen = dp75

            when (itemType) {
            }
        }
    }
}
