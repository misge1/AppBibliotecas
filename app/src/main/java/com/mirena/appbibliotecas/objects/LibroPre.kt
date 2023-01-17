package com.mirena.appbibliotecas.objects

data class LibroPre(
    var id:Int,
    var titulo: String,
    var autor: String,
    var idioma: String,
    var description: String,
    var isbn_issn: String,
    var editorial: String
)
