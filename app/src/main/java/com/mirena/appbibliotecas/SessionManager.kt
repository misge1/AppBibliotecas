package com.mirena.appbibliotecas

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_ID = "user_id"
    }

    fun saveAuthToken(token: Int) {
        val editor = prefs.edit()
        editor.putInt(USER_ID, token)
        editor.apply()
    }

    fun fetchAuthToken(): Int {
        return prefs.getInt(USER_ID, 0)
    }



}