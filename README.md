# 🛒 Carrito de Compras en Kotlin

Este proyecto consiste en el desarrollo de un **sistema de carrito de compras** en **Kotlin**, creado como práctica académica para reforzar conceptos de **programación orientada a objetos**, manejo de **colecciones** y simulación de una **interfaz de consola** dentro de Android Studio.  

---

## 🎯 Objetivo
Permitir a los estudiantes practicar los fundamentos de Kotlin y Android mediante la creación de un sistema sencillo de compras, que incluya interacción con el usuario, validaciones y facturación.

---

## 📌 Requerimientos Técnicos
- ✅ Implementado en **Kotlin**.  
- ✅ Uso de **POO**: clases y objetos (Producto, Carrito, Factura).  
- ✅ Manejo de **colecciones** (`MutableList`).  
- ✅ Simulación de **interfaz de consola** con `TextView` y botones en Android.  
- ✅ Manejo de eventos para acciones del usuario.  
- ✅ Validación de entradas para evitar errores.  
- ✅ Registro de errores y operaciones en un **archivo de log**.  

---

## 📌 Requerimientos Funcionales
1. **Selección de productos**  
   - Mostrar lista de productos con: nombre, precio y cantidad disponible.  
   - Permitir elegir productos e ingresar cantidad deseada.  

2. **Gestión del carrito**  
   - Agregar productos seleccionados al carrito.  
   - Eliminar productos del carrito.  

3. **Visualización del carrito**  
   - Mostrar dinámicamente nombre, cantidad, precio unitario y total por producto.  
   - Mostrar el **total general** del carrito.  

4. **Facturación**  
   - Generar factura con detalle de la compra.  
   - Incluir impuestos o tasas si aplica.  
   - Mostrar total general.  

5. **Interactividad**  
   - Permitir seguir comprando después de generar la factura.  
   - Actualizar dinámicamente el inventario de productos.  

---

## 🛠️ Tecnologías Utilizadas
- **Lenguaje:** Kotlin  
- **IDE:** IntelliJ IDEA  
- **Colecciones:** MutableList  
- **Logs:** Registro en archivo de texto  

---

## 📚 Aprendizaje Clave
Este proyecto permite al estudiante:  
- Aplicar **POO en Kotlin** (abstracción, clases y objetos).  
- Comprender el **uso de colecciones** para gestionar datos.  
- Implementar una **interfaz interactiva** en Android.  
- Manejar **eventos y validaciones**.  
- Simular un flujo de compra real con **facturación e inventario**.  

---


---

## 🚀 Funcionalidades
- **Menú principal** con opciones:
    1. Agregar producto al carrito
    2. Ver carrito
    3. Administración de productos (requiere acceso de administrador)
    4. Salir

- **Carrito de compras**:
    - Agregar productos
    - Eliminar productos
    - Ver el carrito en tabla formateada
    - Generar factura con subtotal, IVA (13%) y total

- **Catálogo de productos** inicial:
    - 🍎 Manzana (stock: 10)
    - 🥛 Leche (stock: 5)
    - 🍞 Pan (stock: 8)

- **Administración** (solo admin):
    - Permite gestionar productos y stock
    - Para tener acceso al admin la contraseña es: admin123

- **Colores ANSI en consola** para mejorar la experiencia visual.

---

- 🛠️ Requisitos

Kotlin 1.9+

JDK 17 o superior

IntelliJ IDEA (recomendado)

## ▶️ Cómo ejecutar

- A continuación se muestra un ejemplo de la ejecución del proyecto

- Clonar el repositorio
- Utilizando Intellij IDEA...

```bash
git clone https://github.com/joszuue/Carritos_Compras_DSM.git
cd Carritos_Compras_DSM

╔══════════════════════╗
║     MENÚ PRINCIPAL   ║
╠══════════════════════╣
║ 1. Agregar producto  ║
║ 2. Ver carrito       ║
║ 3. Administración    ║
║ 4. Salir             ║
╚══════════════════════╝
Seleccione una opción: 


  
