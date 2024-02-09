package com.mirena.appbibliotecas.objects

import java.sql.Blob

data class LibroPre(
    var id:Int,
    var titulo: String,
    var author: String,
    var description: String,
    var idioma: String,
    var isbn_issn: String,
    var editorial: String,
    var image: String,
    var num_ejemplar: String
) {
}
