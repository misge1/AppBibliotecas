package com.mirena.appbibliotecas.objects

data class Prestamo(
    var id: Int?,
    var id_usuario: Int,
    var fecha_prestamo: String,
    var fecha_devolucion: String,
    var id_ejemplar: Int,
    var id_libro: Int,
    var estado: Int
) {
}