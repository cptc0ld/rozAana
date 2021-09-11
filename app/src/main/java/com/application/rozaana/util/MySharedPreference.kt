/**
 * SharedPreferences.java Created by Rhythm Gupta on Dec 12, 2014, 7:42:46 PM Copyright � 2014 by
 * Share Infotech Pvt. Ltd All rights reserved.
 */
package com.testbook.tbapp.prefs

import android.content.Context
import android.content.SharedPreferences

class MySharedPreferences private constructor(context: Context) {
    companion object {
        val TAG: String = "MySharedPreferences"

        // Constants will be defined here.
        val SHARED_PREFERENCES: String = "RozAanaPrefs"
        val USER_NAME = "username"
        val USER_ID = "userId"
        private lateinit var sharedPreferences: SharedPreferences
        private var mInstance: MySharedPreferences? = null

        fun instantiate(context: Context) {
            if (mInstance == null) {
                mInstance = MySharedPreferences(context)
            }
        }

        var username: String?
            get() {
                return sharedPreferences.getString(USER_NAME, "")
            }
            set(value) {
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(USER_NAME, value)
                editor.apply()
            }

        var userId: String?
            get() {
                return sharedPreferences.getString(USER_ID, "")
            }
            set(value) {
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(USER_ID, value)
                editor.apply()
            }
    }

    init {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }
}