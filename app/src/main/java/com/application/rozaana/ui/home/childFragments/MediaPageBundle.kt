package com.application.rozaana.ui.home.childFragments

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Parcelize
@Keep
data class MediaPageBundle(
    var name: String = "",
    var url: String = "",
    var desc: String = ""
) : Parcelable
