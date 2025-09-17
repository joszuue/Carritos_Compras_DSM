package compras.carrito.www.view

import compras.carrito.www.beans.Evento
import compras.carrito.www.controller.*
import compras.carrito.www.util.*

class Consola(private val controller: TiendaController) {
    private fun clearConsole() {
        print("\u001b[H\u001b[2J")
        System.out.flush()
    }

    private fun mostrarMenu() {
        clearConsole()
        val opciones = listOf(
            "1. Agregar producto al carrito",
            "2. Ver carrito",
            "",
            "3. Administración de productos",
            "4. Salir"
        )
        val maxLong = opciones.maxOf { it.length }
        val titulo = "MENÚ PRINCIPAL"
        val ancho = maxOf(maxLong, titulo.length) + 6
        println();
        println(CYAN + "╔" + "═".repeat(ancho) + "╗")
        val padding = (ancho - titulo.length) / 2
        println("║" + " ".repeat(padding) + BLUE + titulo + CYAN + " ".repeat(ancho - titulo.length - padding) + "║")
        println("╠" + "═".repeat(ancho) + "╣")
        opciones.forEach { op ->
            println("║ ${GREEN}${op.padEnd(ancho - 2)}$CYAN ║")
        }
        println("╚" + "═".repeat(ancho) + "╝" + RESET)
        print("${YELLOW}Seleccione una opción: $RESET")
    }

    fun iniciar() {
        var salir = false
        while (!salir) {
            mostrarMenu()
            when (readln()) {
                "1" -> controller.procesarEvento(Evento.AGREGAR_PRODUCTO)
                "2" -> controller.procesarEvento(Evento.VER_CARRITO)
                "3" -> {
                    if (Auth.solicitarAdmin()) {
                        val admin = AdminController(
                            controller.productosRef(),
                            controller.carritoRef()
                        )
                        admin.menu()
                    } else {
                        println("${RED}Acceso denegado$RESET")
                        Thread.sleep(1000)
                    }
                }
                "4" -> {
                    controller.procesarEvento(Evento.SALIR)
                    salir = true
                }
                else -> {
                    println("${RED}Opción inválida$RESET")
                    Thread.sleep(1000)
                }
            }
        }
    }
}
