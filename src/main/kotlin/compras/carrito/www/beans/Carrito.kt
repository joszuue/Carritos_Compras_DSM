package compras.carrito.www.beans

import compras.carrito.www.util.*

data class ItemCarrito(
    val producto: Producto,
    var cantidad: Int
) {
    fun subtotal(): Double = producto.precio * cantidad
}

class Carrito {
    private val items = mutableListOf<ItemCarrito>()

    fun menuCarrito(): Boolean {
        var volverMenu = false
        var salirPrograma = false

        while (!volverMenu && !salirPrograma) {
            mostrarCarrito()

            // Opciones del submenú
            val opciones = listOf(
                "1. Eliminar producto",
                "2. Finalizar compra",
                "3. Regresar al menú principal"
            )

            val maxLong = opciones.maxOf { it.length }
            val titulo = "MENÚ CARRITO"
            val ancho = maxOf(maxLong, titulo.length) + 6

            println()
            println(CYAN + "╔" + "═".repeat(ancho) + "╗")
            val padding = (ancho - titulo.length) / 2
            println(
                "║" + " ".repeat(padding) + BLUE + titulo + CYAN +
                        " ".repeat(ancho - titulo.length - padding) + "║"
            )
            println("╠" + "═".repeat(ancho) + "╣")
            opciones.forEach { op ->
                println("║ ${GREEN}${op.padEnd(ancho - 2)}$CYAN ║")
            }
            println("╚" + "═".repeat(ancho) + "╝" + RESET)
            print("${YELLOW}Seleccione una opción: $RESET")

            when (readln()) {
                "1" -> {
                    print("Ingrese ID del producto a eliminar: ")
                    val id = readln().toIntOrNull()
                    if (id == null) {
                        println("${RED}Entrada inválida$RESET")
                    } else if (eliminarProducto(id)) {
                        println("${CYAN}Producto eliminado del carrito$RESET")
                    } else {
                        println("${RED}Producto no encontrado en carrito$RESET")
                    }
                    Thread.sleep(1000)
                }
                "2" -> {
                    if (total() == 0.0) {
                        println("${RED}El carrito está vacío, no puede finalizar la compra.$RESET")
                        Thread.sleep(1000)
                    } else {
                        generarFactura()

                        print("\n¿Desea seguir comprando? (s/n): ")
                        val opcion = readln().trim().lowercase()
                        if (opcion == "s") {
                        } else {
                            println("${GREEN}Gracias por su compra${RESET}")
                            salirPrograma = true
                            items.clear()
                        }
                    }
                }
                "3" -> volverMenu = true
                else -> {
                    println("${RED}Opción inválida$RESET")
                    Thread.sleep(1000)
                }
            }
        }

        return salirPrograma
    }


    fun mostrarCarrito() {
        if (items.isEmpty()) {
            println("${RED}Carrito vacío${RESET}")
            return
        }

        println("\n${BOLD}${CYAN}CARRITO DE COMPRAS${RESET}")

        val filas = items.map {
            listOf(
                it.producto.id.toString(), // 👈 lo pasamos a String
                it.producto.nombre,
                it.cantidad.toString(),
                "$" + "%.2f".format(java.util.Locale.US, it.producto.precio),
                "$" + "%.2f".format(java.util.Locale.US, it.subtotal())
            )
        }

        val headers = listOf("Cod", "Producto", "Cant.", "Precio Uni", "Subtotal")

        val todas = listOf(headers) + filas
        val anchos = (0 until headers.size).map { col ->
            todas.maxOf { it[col].length }.coerceAtMost(32)
        }

        println(
            headers.mapIndexed { i, h -> h.padEnd(anchos[i]) }
                .joinToString("   ")
        )

        filas.forEach { fila ->
            val columnas = listOf(
                fila[0].padEnd(anchos[0]),   //Cod
                fila[1].padEnd(anchos[1]),   //Producto
                fila[2].padStart(anchos[2]), //Cantidad
                fila[3].padStart(anchos[3]), //Precio Uni
                fila[4].padStart(anchos[4])  //Subtotal
            )
            println(columnas.joinToString("   "))
        }

        val totalStrRaw = "$" + "%.2f".format(java.util.Locale.US, total())

        val totalRow = listOf(
            "".padEnd(anchos[0]),
            "".padEnd(anchos[1]),
            "".padEnd(anchos[2]),
            "TOTAL:".padStart(anchos[3]),
            totalStrRaw.padStart(anchos[4])
        )

        println(
            listOf(
                totalRow[0],
                totalRow[1],
                totalRow[2],
                "${BOLD}${YELLOW}${totalRow[3]}${RESET}",
                "${BOLD}${YELLOW}${totalRow[4]}${RESET}"
            ).joinToString("   ")
        )
    }


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

    private fun generarFactura() {
        println();
        if (items.isEmpty()) {
            println("${RED}Carrito vacío${RESET}")
            return
        }

        val filas = items.map {
            listOf(
                it.producto.id.toString(),
                it.producto.nombre,
                it.cantidad.toString(),
                "$" + "%.2f".format(java.util.Locale.US, it.producto.precio),
                "$" + "%.2f".format(java.util.Locale.US, it.subtotal())
            )
        }
        val headers = listOf("Cod", "Producto", "Cant.", "Precio Uni", "Subtotal")

        val todas = listOf(headers) + filas
        val anchos = (0 until headers.size).map { col ->
            todas.maxOf { it[col].length }.coerceAtMost(32)
        }
        val sep = "   "
        val lineWidth = anchos.sum() + sep.length * (anchos.size - 1)

        fun hr() = println(CYAN + "═".repeat(lineWidth) + RESET)

        hr()
        run {
            val titulo = "FACTURA"
            val left = ((lineWidth - titulo.length) / 2).coerceAtLeast(0)
            val right = lineWidth - titulo.length - left
            println(" ".repeat(left) + BLUE + BOLD + titulo + RESET + " ".repeat(right))
        }
        println()

        println(headers.mapIndexed { i, h -> h.padEnd(anchos[i]) }.joinToString(sep))

        filas.forEach { fila ->
            val columnas = listOf(
                fila[0].padEnd(anchos[0]),   // Cod
                fila[1].padEnd(anchos[1]),   // Producto
                fila[2].padStart(anchos[2]), // Cant.
                fila[3].padStart(anchos[3]), // Precio Uni
                fila[4].padStart(anchos[4])  // Subtotal
            )
            println(columnas.joinToString(sep))
        }

        val subtotal = total()
        val iva = subtotal * 0.13
        val totalFinal = subtotal + iva

        val subRow = listOf(
            "".padEnd(anchos[0]),
            "".padEnd(anchos[1]),
            "".padEnd(anchos[2]),
            "Subtotal:".padStart(anchos[3]),
            ("$" + "%.2f".format(java.util.Locale.US, subtotal)).padStart(anchos[4])
        )
        println(subRow.joinToString(sep))

        val ivaRow = listOf(
            "".padEnd(anchos[0]),
            "".padEnd(anchos[1]),
            "".padEnd(anchos[2]),
            "IVA (13%):".padStart(anchos[3]),
            ("$" + "%.2f".format(java.util.Locale.US, iva)).padStart(anchos[4])
        )
        println(ivaRow.joinToString(sep))

        val totalLabelRaw = "TOTAL:".padStart(anchos[3])
        val totalValRaw = ("$" + "%.2f".format(java.util.Locale.US, totalFinal)).padStart(anchos[4])
        println(
            listOf(
                "".padEnd(anchos[0]),
                "".padEnd(anchos[1]),
                "".padEnd(anchos[2]),
                "${BOLD}${YELLOW}$totalLabelRaw$RESET",
                "${BOLD}${YELLOW}$totalValRaw$RESET"
            ).joinToString(sep)
        )
        hr()
    }

    fun total(): Double = items.sumOf { it.subtotal() }

    fun contieneProducto(idProducto: Int): Boolean =
        items.any { it.producto.id == idProducto }
}