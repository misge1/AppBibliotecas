package com.mirena.appbibliotecas.adapters

import android.view.View

interface GenericInterface <T: Any> {
    fun bindData(item: T, view: View)
}