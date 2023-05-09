package com.mirena.appbibliotecas.objects

data class LibroObject(
    var id:Int,
    var titulo: String,
    var autor: String,
    var idioma: String,
    var description: String,
    var isbn_issn: String,
    var editorial: String,
    var num_ejemplares: Int
)