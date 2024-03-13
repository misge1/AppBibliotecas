package com.mirena.appbibliotecas

import android.content.Context
import android.content.SharedPreferences
import java.util.StringTokenizer

class SessionManager(context: Context) {

    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    companion object {
        const val USER_ID = "user_id"
        const val BIBLIOTECA_ELECCION = "bibliotecaEleccion"
        const val SUBGENERO_ELECCION = "subgeneroEleccion"
        const val GENERO_ELECCION = "generoEleccion"
        const val ARRAYLIST_SUBGENEROS = "arrayListSubgenero"
        const val ARRAYLIST_BIBLIOTECAS = "arrayListBiblioteca"
        const val ARRAYLIST_IDIOMAS = "arrayListIdiomas"
        const val DISPONIBLIDAD = "disponibilidad"
        const val TOKEN_DISPO = "tokenDispositivo"
    }

    fun saveAuthToken(token: Int) {
        val editor = prefs.edit()
        editor.putInt(USER_ID, token)
        editor.apply()
    }

    fun saveDispoToken(token: String){
        val editor = prefs.edit()
        editor.putString(TOKEN_DISPO, token)
        editor.apply()
    }

    fun fetchDispoToken(): String {
        return prefs.getString(TOKEN_DISPO, "")!!
    }

    fun deleteDispoToken() {
        val editor = prefs.edit()
        editor.remove(TOKEN_DISPO)
        editor.apply()
    }
    fun deleteFiltros(){
        val editor = prefs.edit()
        editor.remove(ARRAYLIST_BIBLIOTECAS)
        editor.remove(ARRAYLIST_SUBGENEROS)
        editor.remove(ARRAYLIST_IDIOMAS)
        editor.remove(DISPONIBLIDAD)
        editor.apply()
    }

    fun deleteBibliotecas(){
        val editor = prefs.edit()
        editor.remove(ARRAYLIST_BIBLIOTECAS)
        editor.apply()
    }

    fun deleteSubgeneros(){
        val editor = prefs.edit()
        editor.remove(ARRAYLIST_SUBGENEROS)
        editor.apply()
    }

    fun deleteIdiomas(){
        val editor = prefs.edit()
        editor.remove(ARRAYLIST_IDIOMAS)
        editor.apply()
    }

    fun filtrarSubgeneros(arrayListSubgeneros: ArrayList<Int>){
        val editor = prefs.edit()
        var arraysubgeneros = prefs.getString(ARRAYLIST_SUBGENEROS,"");

        val st = StringTokenizer(arraysubgeneros, ",")
        val savedList = arrayListOf<Int>()
        while(st.hasMoreTokens()){
            savedList.add(st.nextToken().toInt())
        }

        val str = StringBuilder()
        for (i in arrayListSubgeneros.indices) {
            if (!savedList.contains(arrayListSubgeneros[i])){
                str.append(arrayListSubgeneros[i]).append(",")
            }

        }
        arraysubgeneros = arraysubgeneros.plus(str.toString())
        editor.putString(ARRAYLIST_SUBGENEROS, arraysubgeneros)
        editor.apply()

    }

    fun deleteSubgeneros(arrayListSubgeneros: ArrayList<Int>){
        val editor = prefs.edit()
        val arraySubgeneros = prefs.getString(ARRAYLIST_SUBGENEROS, "")
        val st = StringTokenizer(arraySubgeneros, ",")
        val savedList = arrayListOf<Int>()
        while(st.hasMoreTokens()){
            savedList.add(st.nextToken().toInt())
        }

        for(i in arrayListSubgeneros.indices){
            if(savedList.contains(arrayListSubgeneros[i])){
                savedList.remove(arrayListSubgeneros[i])
            }
        }

        val str = StringBuilder()
        for (i in savedList.indices) {
            str.append(savedList[i]).append(",")
        }
        editor.putString(ARRAYLIST_SUBGENEROS, str.toString())
        editor.apply()
    }

    fun fetchFiltroSubgeneros(): String{
        return prefs.getString(ARRAYLIST_SUBGENEROS, "")!!
    }

    fun filtrarBibliotecas(arrayListBibliotecas: ArrayList<Int>){
        val editor = prefs.edit()
        val str = StringBuilder()
        for (i in arrayListBibliotecas.indices) {
            str.append(arrayListBibliotecas[i]).append(",")
        }
        editor.putString(ARRAYLIST_BIBLIOTECAS, str.toString())
        editor.apply()
    }

    fun fetchFiltroBibliotecas(): String{
        return prefs.getString(ARRAYLIST_BIBLIOTECAS, "")!!
    }

    fun filtrarIdiomas(arrayListIdiomas: ArrayList<Int>){
        val editor = prefs.edit()
        val str = StringBuilder()
        for (i in arrayListIdiomas.indices) {
            str.append(arrayListIdiomas[i]).append(",")
        }
        editor.putString(ARRAYLIST_IDIOMAS, str.toString())
        editor.apply()
    }

    fun fetchFiltroIdiomas(): String{
        return prefs.getString(ARRAYLIST_IDIOMAS, "")!!
    }

    fun filtrarDisponibles(disponibles: Int){
        val editor = prefs.edit()
        editor.putInt(DISPONIBLIDAD, disponibles)
        editor.apply()
    }

    fun fetchDisponibles(): Int{
        return prefs.getInt(DISPONIBLIDAD, 0);
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