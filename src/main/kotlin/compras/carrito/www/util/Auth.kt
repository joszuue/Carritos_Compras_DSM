package compras.carrito.www.util

object Auth {
    private val ADMIN_PASS: String = System.getenv("ADMIN_PASS") ?: "admin123"

    fun solicitarAdmin(maxIntentos: Int = 3): Boolean {
        repeat(maxIntentos) { intento ->
            val pwd = leerPassword("Contraseña admin: ")
            if (pwd == ADMIN_PASS) return true
            println("Contraseña incorrecta (${intento + 1}/$maxIntentos)")
        }
        return false
    }

    private fun leerPassword(prompt: String): String {
        val console = System.console()
        return if (console != null) {
            print(prompt)
            String(console.readPassword())
        } else {
            print(prompt)
            readlnOrNull() ?: ""
        }
    }
}