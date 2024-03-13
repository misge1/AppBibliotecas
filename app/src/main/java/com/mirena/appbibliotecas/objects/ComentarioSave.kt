package com.mirena.appbibliotecas.objects

data class ComentarioSave(
    var id_comentario: Int?,
    var texto: String,
    var id_usuario: Int,
    var id_libro: Int,
    var fecha: String
)
