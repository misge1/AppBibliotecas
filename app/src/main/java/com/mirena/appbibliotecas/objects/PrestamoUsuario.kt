package com.mirena.appbibliotecas.objects

data class PrestamoUsuario(
    var id: Int,
    var titulo: String,
    var image: String,
    var nombre: String,
    var fecha_prestamo: String,
    var fecha_recogida: String,
    var fecha_devolucion: String
) {
}