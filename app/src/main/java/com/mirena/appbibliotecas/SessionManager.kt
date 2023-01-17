package com.mirena.appbibliotecas

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_ID = "user_id"
        const val STATE_BUTTON = "state_button"
    }

    fun saveAuthToken(token: Int) {
        val editor = prefs.edit()
        editor.putInt(USER_ID, token)
        editor.apply()
    }

    fun saveButtonState(state: Int){
        val editor = prefs.edit()
        editor.putInt(STATE_BUTTON, state)
        editor.apply()
    }

    fun fetchAuthToken(): Int {
        return prefs.getInt(USER_ID, 0)
    }

    fun fetchButtonState(): Int {
        return prefs.getInt(STATE_BUTTON,0)
    }

    fun deleteAuthToken() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }



}