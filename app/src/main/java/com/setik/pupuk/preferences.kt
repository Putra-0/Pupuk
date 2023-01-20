package com.setik.pupuk

import android.content.Context
import android.content.SharedPreferences

class preferences(context: Context) {
    private val TAG_STATUS = "status"
    private val TAG_APP = "app"

    private val pref: SharedPreferences =
        context.getSharedPreferences(TAG_APP, Context.MODE_PRIVATE)

    var prefStatus: Boolean
        get() = pref.getBoolean(TAG_STATUS, false)
        set(value) = pref.edit().putBoolean(TAG_STATUS, value).apply()

    fun prefClear(){
        pref.edit().remove(TAG_STATUS).apply()
    }
}