package compras.carrito.www.beans

data class Producto(
    val id: Int,
    var nombre: String,
    var precio: Double,
    var stock: Int
)
