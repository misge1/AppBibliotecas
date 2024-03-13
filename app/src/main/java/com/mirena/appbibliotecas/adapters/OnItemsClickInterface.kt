package com.mirena.appbibliotecas.adapters

import android.content.Context
import com.mirena.appbibliotecas.objects.Favoritos

interface OnItemsClickInterface {

    fun comprobarFavoritos(idLibro: Int, idUsuario: Int);

    fun getComprobacion();

    fun addFavoritos(favorito: Favoritos, applicationContext: Context, contextActivity: Context)

    fun deleteFavoritos(idLibro: Int,idUsuario: Int, applicationContext: Context, contextActivity: Context )
}