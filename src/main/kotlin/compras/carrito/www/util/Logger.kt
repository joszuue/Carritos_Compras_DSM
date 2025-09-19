package compras.carrito.www.util

import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.io.StringWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Logger {
    private val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    private var logFile: File? = null
    private var canWriteToFile = false

    init {
        initializeLogFile()
    }

    private fun initializeLogFile() {
        try {
            val possibleLocations = listOf(
                    File("carrito_errores.log"),
                    File(System.getProperty("user.home"), "carrito_errores.log"),
                    File(System.getProperty("java.io.tmpdir"), "carrito_errores.log")
            )

            for (location in possibleLocations) {
                try {
                    if (!location.exists()) {
                        location.createNewFile()
                    }

                    if (location.canWrite()) {
                        logFile = location
                        canWriteToFile = true
                        println("📝 Archivo de log configurado en: ${location.absolutePath}")

                        testWrite()
                        break
                    }
                } catch (e: Exception) {
                    continue
                }
            }

            if (!canWriteToFile) {
                println("⚠️ No se pudo configurar archivo de log. Los errores se mostrarán solo en consola.")
            }

        } catch (e: Exception) {
            println("❌ Error al inicializar logger: ${e.message}")
            canWriteToFile = false
        }
    }

    private fun testWrite() {
        try {
            logFile?.let { file ->
                val now = LocalDateTime.now().format(dateFormat)
                FileWriter(file, true).use { writer ->
                    writer.write("[$now] Logger inicializado correctamente\n")
                    writer.flush()
                }
            }
        } catch (e: Exception) {
            canWriteToFile = false
            println("❌ Test de escritura falló: ${e.message}")
        }
    }

    fun logError(e: Exception) {
        val now = LocalDateTime.now().format(dateFormat)
        val sw = StringWriter()
        e.printStackTrace(PrintWriter(sw))
        val stackTrace = sw.toString()

        val logMessage = """
[$now] ERROR: ${e.message}
Stack Trace:
$stackTrace
${"=".repeat(80)}

""".trimIndent()

        println("🔴 ERROR: ${e.message}")

        writeToFile(logMessage)
    }

    fun logError(message: String) {
        val now = LocalDateTime.now().format(dateFormat)

        val logMessage = """
[$now] ERROR: $message
${"=".repeat(80)}

""".trimIndent()

        println("🔴 ERROR: $message")

        writeToFile(logMessage)
    }

    fun logInfo(message: String) {
        val now = LocalDateTime.now().format(dateFormat)

        val logMessage = """
[$now] INFO: $message
${"=".repeat(80)}

""".trimIndent()

        println("ℹ️ INFO: $message")

        writeToFile(logMessage)
    }

    private fun writeToFile(message: String) {
        if (!canWriteToFile || logFile == null) {
            return
        }

        try {
            FileWriter(logFile!!, true).use { writer ->
                writer.write(message)
                writer.flush()
            }
            println("📝 Log guardado en archivo")
        } catch (e: Exception) {
            println("❌ Error al escribir log: ${e.message}")
            // Solo mostrar el error la primera vez
            if (canWriteToFile) {
                canWriteToFile = false
                println("📝 Continuando con logs solo en consola...")
            }
        }
    }

    fun clearLog() {
        if (!canWriteToFile || logFile == null) {
            println("❌ No hay archivo de log disponible para limpiar")
            return
        }

        try {
            FileWriter(logFile!!, false).use { writer ->
                writer.write("")
            }
            println("🗑️ Archivo de log limpiado")
        } catch (e: Exception) {
            println("❌ Error al limpiar log: ${e.message}")
        }
    }

    fun showRecentLogs(lines: Int = 10) {
        if (!canWriteToFile || logFile == null || !logFile!!.exists()) {
            println("📝 No hay archivo de log disponible")
            return
        }

        try {
            val logContent = logFile!!.readLines()
            if (logContent.isEmpty()) {
                println("📝 El archivo de log está vacío")
                return
            }

            println("\n📋 Últimas $lines líneas del log:")
            println("=" .repeat(50))
            logContent.takeLast(lines).forEach { println(it) }
            println("=" .repeat(50))

        } catch (e: Exception) {
            println("❌ Error al leer log: ${e.message}")
        }
    }

    fun getLogFilePath(): String? {
        return logFile?.absolutePath
    }

    fun isWorking(): Boolean {
        return canWriteToFile && logFile != null
    }

    fun getDiagnosticInfo() {
        println("\n🔍 Información de diagnóstico del Logger:")
        println("- Puede escribir archivos: $canWriteToFile")
        println("- Archivo de log: ${logFile?.absolutePath ?: "No configurado"}")
        println("- El archivo existe: ${logFile?.exists() ?: false}")
        println("- Permisos de escritura: ${logFile?.canWrite() ?: false}")
        println("- Directorio actual: ${System.getProperty("user.dir")}")
        println("- Directorio home: ${System.getProperty("user.home")}")
        println("- Directorio temporal: ${System.getProperty("java.io.tmpdir")}")
    }
}