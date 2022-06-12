package com.elanyudho.movrefapplication.data.pref

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.Exception
import javax.inject.Inject

class Session
@Inject constructor(
    @ApplicationContext context: Context
) {
    private val gson = Gson()
    private val sp : SharedPreferences by lazy {
        context.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE)
    }

    private val spe : SharedPreferences.Editor by lazy {
        sp.edit()
    }

    fun clear() {
        spe.clear().apply()
    }

    var isFirstTime: Boolean
        get() = sp.getBoolean(IS_FIRST_TIME, true)
        set(value) = spe.putBoolean(IS_FIRST_TIME, value).apply()

    companion object {
        private const val SESSION_NAME = "BUMNSession"
        private const val IS_FIRST_TIME = "first_time"
        private const val USER_PROFILE = "user_profile"
        private const val IS_LOGIN = "is login"
    }
}