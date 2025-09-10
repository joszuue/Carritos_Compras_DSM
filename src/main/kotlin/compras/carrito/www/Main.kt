package compras.carrito.www

import compras.carrito.www.controller.TiendaController
import compras.carrito.www.view.Consola

fun main() {
    val controller = TiendaController()
    val consola = Consola(controller)
    consola.iniciar()
}
