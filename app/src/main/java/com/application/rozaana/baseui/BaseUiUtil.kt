package com.application.rozaana.baseui

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.os.SystemClock
import android.text.Html
import android.text.Spanned
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.application.rozaana.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.exoplayer2.util.Util
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.json.JSONException
import org.json.JSONObject

object BaseUiUtil {
    @JvmStatic
    fun setToolbarBackAndTitles(
        toolbar: Toolbar,
        title: String?,
        subTitle: String?
    ): ImageView {
        val clickListener =
            View.OnClickListener { view -> view.isSelected = !view.isSelected }
        val toolbarBack =
            toolbar.findViewById<View>(R.id.toolbar_navigation_iv) as ImageView
        if (toolbar.findViewById<View?>(R.id.toolbar_texts) != null) toolbar.findViewById<View>(
            R.id.toolbar_texts
        ).visibility = View.VISIBLE
        toolbarBack.visibility = View.VISIBLE
        toolbarBack.setImageResource(R.drawable.back_arrow)
        val toolbarTitle =
            toolbar.findViewById<View>(R.id.toolbar_title_tv) as TextView
        val toolbarSubTitle =
            toolbar.findViewById<View>(R.id.toolbar_sub_title) as TextView
        toolbarSubTitle.visibility = if (TextUtils.isEmpty(subTitle)) View.GONE else View.VISIBLE
        val animate = TextUtils.isEmpty(toolbarSubTitle.text)
        toolbarTitle.text = title
        toolbarSubTitle.text = subTitle
        toolbarTitle.isSelected = true
        toolbarSubTitle.isSelected = true
        toolbarTitle.setOnClickListener(clickListener)
        toolbarSubTitle.setOnClickListener(clickListener)
        return toolbarBack
    }

    @JvmStatic
    fun setToolbarBackWithTitleImage(
        toolbar: Toolbar,
        drawableForTitle: Int
    ): ImageView {
        val clickListener =
            View.OnClickListener { view -> view.isSelected = !view.isSelected }
        val toolbarBack =
            toolbar.findViewById<View>(R.id.toolbar_navigation_iv) as ImageView
        toolbarBack.visibility = View.VISIBLE
        toolbarBack.setImageResource(R.drawable.back_arrow)

//        val toolbarTitleImage =
//            toolbar.findViewById<View>(R.id.title_image) as ImageView
//        toolbarTitleImage.visibility = View.VISIBLE
//        toolbarTitleImage.setImageResource(drawableForTitle)
        return toolbarBack
    }

    fun spToPixel(context: Context, sp: Float): Float {
        val scaledDensity = context.resources.displayMetrics.scaledDensity
        return Math.round(sp * scaledDensity).toFloat()
    }

    fun getColorFromTheme(context: Context, colorAttrId: Int): Int {
        val typedValue = TypedValue()
        val theme = context.theme
        theme.resolveAttribute(colorAttrId, typedValue, true)
        return typedValue.data
    }

    fun getDrawableIdFromAttr(context: Context, attrId: Int): Int {
        val a =
            context.theme.obtainStyledAttributes(intArrayOf(attrId))
        return a.getResourceId(0, 0)
    }

    fun fromChangeView(searchView: android.widget.SearchView): Observable<String> {
        val subject = PublishSubject.create<String>()
        searchView.setOnQueryTextListener(object :
                android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {

                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let { subject.onNext(it) }
                    return false
                }
            })
//            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//                override fun onQueryTextSubmit(query: String?): Boolean {
//                    query?.let { subject.onNext(it) }
//                    return false
//                }
//
//                override fun onQueryTextChange(newText: String?): Boolean {
//                    return false
//                }
//            })
        return subject
    }

    fun View.clickWithDebounce(debounceTime: Long = 600L, action: () -> Unit) {
        this.setOnClickListener(object : View.OnClickListener {
            private var lastClickTime: Long = 0

            override fun onClick(v: View) {
                if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
                else action()

                lastClickTime = SystemClock.elapsedRealtime()
            }
        })
    }

    fun RecyclerView.addOnScrollListener(scrollDown: () -> Unit, scrollUp: () -> Unit) {
        this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private var lastScrollTime: Long = 0

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (SystemClock.elapsedRealtime() - lastScrollTime < 600L) return
                else {
                    if (dy > 0)
                        scrollDown()
                    else
                        scrollUp()
                }

                lastScrollTime = SystemClock.elapsedRealtime()
            }
        })
    }

    fun pxToDp(context: Context, px: Int): Int {
        val displayMetrics = context.resources.displayMetrics
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }

    fun dpToPx(context: Context, dp: Float): Int {
        val displayMetrics = context.resources.displayMetrics
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }

    fun pixelsToSp(context: Context, px: Float): Float {
        val scaledDensity = context.resources.displayMetrics.scaledDensity
        return Math.round(px / scaledDensity).toFloat()
    }

    fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().getDisplayMetrics().density).toInt()
    }

    fun pxToDp(px: Int): Int {
        return (px / Resources.getSystem().getDisplayMetrics().density).toInt()
    }

    fun getImageUrl(url: String): String {
        var imageUrl = url
        if (!imageUrl.startsWith("http")) {
            if (url.startsWith("//")) {
                imageUrl = "https:$imageUrl"
            } else {
                imageUrl = "https://$imageUrl"
            }
        }
        return imageUrl
    }
}
