package compras.carrito.www.util

import java.io.File
import java.time.LocalDateTime

object Logger {
    private val logFile = File("errores.log")

    fun logError(msg: String) {
        logFile.appendText("${LocalDateTime.now()} - ERROR: $msg\n")
    }
}
