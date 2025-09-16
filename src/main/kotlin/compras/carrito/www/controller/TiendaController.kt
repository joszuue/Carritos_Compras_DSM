package compras.carrito.www.controller
import compras.carrito.www.beans.*
import compras.carrito.www.util.Logger

class TiendaController {
    private val productos = mutableListOf(
        Producto(1, "Manzana", 0.5, 10),
        Producto(2, "Leche", 1.2, 5),
        Producto(3, "Pan", 0.8, 8)
    )

    private val carrito = Carrito()

    fun procesarEvento(evento: Evento) {
        try {
            when (evento) {
                Evento.MOSTRAR_PRODUCTOS -> mostrarProductos()
                Evento.AGREGAR_PRODUCTO -> agregarProducto()
                Evento.ELIMINAR_PRODUCTO -> eliminarProducto()
                Evento.VER_CARRITO -> carrito.mostrarCarrito()
                Evento.SALIR -> println("Gracias por su compra!")
            }
        } catch (e: Exception) {
            Logger.logError("Error en evento $evento: ${e.message}")
            println("❌ Error: ${e.message}")
        }
    }

    private fun mostrarProductos() {
        println("\n--- Lista de productos ---")
        productos.forEach { p ->
            println("${p.id}. ${p.nombre} - Precio: ${p.precio} - Stock: ${p.stock}")
        }
    }

    private fun agregarProducto() {
        mostrarProductos()
        print("Ingrese ID del producto: ")
        val id = readln().toIntOrNull()
        print("Cantidad: ")
        val cantidad = readln().toIntOrNull()

        val producto = productos.find { it.id == id }
        if (producto == null || cantidad == null) {
            println("Entrada inválida")
            return
        }

        if (carrito.agregarProducto(producto, cantidad)) {
            println("Producto agregado al carrito")
        } else {
            println("No se pudo agregar (stock insuficiente o cantidad inválida)")
        }
    }

    private fun eliminarProducto() {
        print("Ingrese ID del producto a eliminar del carrito: ")
        val id = readln().toIntOrNull()
        if (id == null) {
            println("Entrada inválida")
            return
        }
        if (carrito.eliminarProducto(id)) {
            println("Producto eliminado del carrito")
        } else {
            println("Producto no encontrado en carrito")
        }
    }

    fun productosRef(): MutableList<Producto> = productos
    fun carritoRef(): Carrito = carrito
}
