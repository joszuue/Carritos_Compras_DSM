package compras.carrito.www.controller

import compras.carrito.www.beans.Carrito
import compras.carrito.www.beans.Producto

class AdminController(
    private val productos: MutableList<Producto>,
    private val carrito: Carrito
) {
    fun menu() {
        var salir = false
        while (!salir) {
            println(
                """
                |--- ADMINISTRACIÓN DE PRODUCTOS ---
                |1. Listar productos
                |2. Crear producto
                |3. Editar producto
                |4. Eliminar producto
                |5. Volver
                """.trimMargin()
            )
            print("Opción: ")
            when (readln()) {
                "1" -> listar()
                "2" -> crear()
                "3" -> editar()
                "4" -> eliminar()
                "5" -> salir = true
                else -> println("Opción inválida")
            }
        }
    }

    private fun listar() {
        if (productos.isEmpty()) {
            println("No hay productos.")
            return
        }
        productos.forEach { p ->
            println("${p.id}. ${p.nombre} - Precio: ${"%.2f".format(p.precio)} - Stock: ${p.stock}")
        }
    }

    private fun crear() {
        print("Nombre: "); val nombre = readln().trim()
        print("Precio: "); val precio = readln().toDoubleOrNull()
        print("Stock: ");  val stock  = readln().toIntOrNull()

        if (nombre.isBlank() || precio == null || stock == null) {
            println("Datos inválidos")
            return
        }

        val nextId = (productos.maxOfOrNull { it.id } ?: 0) + 1
        productos.add(Producto(nextId, nombre, precio, stock))
        println("Producto creado con ID $nextId")
    }

    private fun editar() {
        print("ID a editar: "); val id = readln().toIntOrNull() ?: return println("ID inválido")
        val p = productos.find { it.id == id } ?: return println("El producto no existe")

        print("Nombre (${p.nombre}): ")
        readln().takeIf { it.isNotBlank() }?.let { p.nombre = it.trim() }

        print("Precio (${p.precio}): ")
        readln().takeIf { it.isNotBlank() }?.toDoubleOrNull()?.let { p.precio = it }

        print("Stock (${p.stock}): ")
        readln().takeIf { it.isNotBlank() }?.toIntOrNull()?.let { p.stock = it }

        println("Producto actualizado")
    }

    private fun eliminar() {
        print("ID a eliminar: "); val id = readln().toIntOrNull() ?: return println("ID inválido")

        if (carrito.contieneProducto(id)) {
            println("No se puede eliminar: el producto está en el carrito.")
            return
        }

        val removed = productos.removeIf { it.id == id }
        if (removed) println("🗑️ Producto $id eliminado") else println("No se encontró el producto")
    }
}