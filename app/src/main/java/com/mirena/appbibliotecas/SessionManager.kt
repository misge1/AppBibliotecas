package com.mirena.appbibliotecas

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_ID = "user_id"
        const val BIBLIOTECA_ELECCION = "bibliotecaEleccion"
        const val SUBGENERO_ELECCION = "subgeneroEleccion"
        const val GENERO_ELECCION = "generoEleccion"
    }

    fun saveAuthToken(token: Int) {
        val editor = prefs.edit()
        editor.putInt(USER_ID, token)
        editor.apply()
    }


    fun saveBibliotecaEleccion(token: String){
        val editor = prefs.edit()
        editor.putString(BIBLIOTECA_ELECCION, token)
        editor.apply()
    }

    fun saveGeneroEleccion(token: String){
        val editor = prefs.edit()
        editor.putString(GENERO_ELECCION, token)
        editor.apply()
    }

    fun saveSubgeneroEleccion(token: String){
        val editor = prefs.edit()
        editor.putString(SUBGENERO_ELECCION, token)
        editor.apply()
    }

    fun fetchAuthToken(): Int {
        return prefs.getInt(USER_ID, 0)
    }

    fun fetchBiblioEleccion(): String {
        return prefs.getString(BIBLIOTECA_ELECCION, "")!!
    }

    fun fetchGeneroEleccion(): String {
        return prefs.getString(GENERO_ELECCION, "")!!
    }

    fun fetchSubgeneroEleccion(): String {
        return prefs.getString(SUBGENERO_ELECCION, "")!!
    }

    fun deleteAuthToken() {
        val editor = prefs.edit()
        editor.remove("user_id")
        editor.apply()
    }

    fun deleteBiblioToken(){
        val editor = prefs.edit()
        editor.remove("bibliotecaEleccion")
        editor.apply()
    }

    fun deleteSubgeneroToken(){
        val editor = prefs.edit()
        editor.remove("subgeneroEleccion")
        editor.apply()

    }

    fun deleteGeneroToken(){
        val editor = prefs.edit()
        editor.remove("generoEleccion")
        editor.apply()
    }



}