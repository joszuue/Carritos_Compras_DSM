package compras.carrito.www.beans

data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Double,
    var stock: Int
)
