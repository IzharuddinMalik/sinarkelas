package com.sinarkelas.util

import android.content.Context
import android.content.SharedPreferences

class PrefManager(context: Context) {

    var mCtx = context

    var PRIVATE_MODE : Int = 0

    val PREF_NAME : String = "introSlider"
    val IS_FIRST_TIME_LAUNCH : String = "IsFirstTimeLaunch"

    var pref = mCtx.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    var editor = pref!!.edit()

    fun setFirstTimeLaunch(isFirstTime: Boolean) {
        editor!!.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime)
        editor!!.commit()
    }

    fun isFirstTimeLaunch(): Boolean {
        return pref!!.getBoolean(IS_FIRST_TIME_LAUNCH, true)
    }
}