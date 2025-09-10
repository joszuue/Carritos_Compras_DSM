package compras.carrito.www.beans

data class ItemCarrito(
    val producto: Producto,
    var cantidad: Int
) {
    fun subtotal(): Double = producto.precio * cantidad
}

class Carrito {
    private val items = mutableListOf<ItemCarrito>()

    fun agregarProducto(producto: Producto, cantidad: Int): Boolean {
        if (cantidad <= 0 || cantidad > producto.stock) return false
        val existente = items.find { it.producto.id == producto.id }
        if (existente != null) {
            existente.cantidad += cantidad
        } else {
            items.add(ItemCarrito(producto, cantidad))
        }
        producto.stock -= cantidad
        return true
    }

    fun eliminarProducto(idProducto: Int): Boolean {
        val item = items.find { it.producto.id == idProducto } ?: return false
        item.producto.stock += item.cantidad
        items.remove(item)
        return true
    }

    fun mostrarCarrito() {
        if (items.isEmpty()) {
            println("Carrito vacío")
            return
        }
        println("\n--- Carrito de Compras ---")
        items.forEach {
            println("${it.producto.nombre} - Cant: ${it.cantidad} - " +
                    "Precio: ${it.producto.precio} - Subtotal: ${it.subtotal()}")
        }
        println("TOTAL: ${total()}\n")
    }

    fun total(): Double = items.sumOf { it.subtotal() }
}
