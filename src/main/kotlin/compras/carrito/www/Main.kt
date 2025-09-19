package compras.carrito.www

import compras.carrito.www.controller.TiendaController
import compras.carrito.www.view.Consola
import compras.carrito.www.util.Logger

fun main() {
    try {
        println("🚀 Iniciando aplicación...")

        Logger.getDiagnosticInfo()
        println()

        Logger.logInfo("Aplicación iniciada")

        if (Logger.isWorking()) {
            println("✅ Logger funcionando correctamente")
        } else {
            println("⚠️ Logger funcionando solo en consola")
        }

        try {
            throw RuntimeException("Prueba de error al iniciar la aplicación")
        } catch (e: Exception) {
            Logger.logError(e)
        }

        Logger.getLogFilePath()?.let { path ->
            println("📁 Archivo de log: $path")
        }

        println("\nPresiona Enter para continuar...")
        readln()

        val controller = TiendaController()
        val consola = Consola(controller)

        consola.iniciar()

        Logger.logInfo("Aplicación terminada correctamente")

    } catch (e: Exception) {
        Logger.logError(e)
        println("❌ Error fatal en la aplicación: ${e.message}")
    }
}