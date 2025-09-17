package compras.carrito.www.controller
import compras.carrito.www.beans.*
import compras.carrito.www.util.Logger
import compras.carrito.www.util.*

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
                Evento.VER_CARRITO -> carrito.menuCarrito()
                Evento.SALIR -> println("Vuelva pronto!")
            }
        } catch (e: Exception) {
            Logger.logError("Error en evento $evento: ${e.message}")
            println("❌ Error: ${e.message}")
        }
    }

    private fun mostrarProductos() {
        println("\n${BOLD}${CYAN}CATÁLOGO${RESET}")
        if (productos.isEmpty()) {
            println("${DIM}No hay productos cargados.${RESET}")
            return
        }

        val filas = productos.map { p ->
            listOf(
                "#${p.id}",
                p.nombre,
                "$" + "%.2f".format(java.util.Locale.US, p.precio),
                if (p.stock > 0) "DISPONIBLE" else "AGOTADO",
                "(${p.stock})"
            )
        }

        val headers = listOf("Código", "Nombre", "Precio Uni", "Estado", "Stock")

        val todas = listOf(headers) + filas
        val anchos = (0 until headers.size).map { col ->
            todas.maxOf { it[col].length }.coerceAtMost(32)
        }

        println(
            headers.mapIndexed { i, h -> h.padEnd(anchos[i]) }
                .joinToString("   ")
        )

        filas.forEach { fila ->
            val estado = fila[3]
            val estadoColor = if (estado == "DISPONIBLE")
                "${BG_GREEN}${BLACK}${estado.padEnd(anchos[3])}$RESET"
            else
                "${BG_RED}${WHITE}${estado.padEnd(anchos[3])}$RESET"

            val columnas = listOf(
                fila[0].padEnd(anchos[0]),
                fila[1].padEnd(anchos[1]),
                fila[2].padStart(anchos[2]),
                estadoColor,
                fila[4].padEnd(anchos[4])
            )
            println(columnas.joinToString("   "))
        }
    }

    private fun agregarProducto() {
        mostrarProductos()
        println();
        print("Ingrese ID del producto: ")
        val id = readln().toIntOrNull()
        print("Cantidad: ")
        val cantidad = readln().toIntOrNull()

        val producto = productos.find { it.id == id }
        if (producto == null || cantidad == null) {
            println("${RED}Entrada inválida$RESET")
            return
        }

        if (carrito.agregarProducto(producto, cantidad)) {
            println("${CYAN}Producto agregado al carrito$RESET")
        } else {
            println("${RED}No se pudo agregar (stock insuficiente o cantidad inválida)$RESET")
        }
    }

    private fun eliminarProducto() {
        print("Ingrese ID del producto a eliminar del carrito: ")
        val id = readln().toIntOrNull()
        if (id == null) {
            println("${RED}Entrada inválida$RESET")
            return
        }
        if (carrito.eliminarProducto(id)) {
            println("Producto eliminado del carrito")
        } else {
            println("${RED}Producto no encontrado en carrito$RESET")
        }
    }

    fun productosRef(): MutableList<Producto> = productos
    fun carritoRef(): Carrito = carrito
}
