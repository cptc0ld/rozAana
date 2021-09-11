package com.application.rozaana.ui.home

import android.view.View
import androidx.viewpager2.widget.ViewPager2

class SliderTransformer() : ViewPager2.PageTransformer {

    private val MIN_SCALE = 0.90f

    override fun transformPage(page: View, position: Float) {
        when {
            position < -1 -> {     // [-Infinity,-1)
                // This page is way off-screen to the left.
                page.alpha = 0f;

            }
            position <= 0 -> {    // [-1,0]
                page.alpha = 1f;
                page.translationY = 0f
                page.scaleX = 1f;
                page.scaleY = 1f;
                page.translationZ = 1f
//                page.rotationX = -120*Math.abs(position);
            }
            position <= 1 -> {    // (0,1]
                page.alpha = 1f - position;
                page.translationY = -position*page.height;
                page.translationZ = -Math.abs(position);
                val scaleFactor = (MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position)))
                page.scaleX = scaleFactor
                page.scaleY = scaleFactor
//                page.rotationX = 120*Math.abs(position);

            }
            else -> {    // (1,+Infinity]
                // This page is way off-screen to the right.
                page.alpha = 0f;

            }
        }
    }
}