package compras.carrito.www.view

import compras.carrito.www.beans.Evento
import compras.carrito.www.controller.*

class Consola(private val controller: TiendaController) {
    fun iniciar() {
        var salir = false
        while (!salir) {
            println("""
                \n--- MENÚ ---
                1. Mostrar productos
                2. Agregar producto al carrito
                3. Eliminar producto del carrito
                4. Ver carrito
                5. Salir
            """.trimIndent())

            print("Seleccione una opción: ")
            when (readln()) {
                "1" -> controller.procesarEvento(Evento.MOSTRAR_PRODUCTOS)
                "2" -> controller.procesarEvento(Evento.AGREGAR_PRODUCTO)
                "3" -> controller.procesarEvento(Evento.ELIMINAR_PRODUCTO)
                "4" -> controller.procesarEvento(Evento.VER_CARRITO)
                "5" -> {
                    controller.procesarEvento(Evento.SALIR)
                    salir = true
                }
                else -> println("Opción inválida")
            }
        }
    }
}
