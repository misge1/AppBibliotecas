package com.mirena.appbibliotecas.objects

data class Comentario(
    var id_comentario: Int,
    var texto: String,
    var usuario: String,
    var id_libro: Int,
    var fecha: String
)
