package com.mirena.appbibliotecas.objects

 data class Usuario(
    var id: Int,
    var nombre: String,
    var activo: Int,
    var fecha_alta: String,
    var fecha_baja: String?,
    var telefono: String,
    var domicilio: String,
    var codigo_postal: String,
    var localidad: String,
    var email: String,
    var password: String,
    var foto: String?
) {

    constructor() : this(0,"" ,0,
       "", "", "",
       "","", "", "", "", ""
    )
}