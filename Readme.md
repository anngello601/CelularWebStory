CelularWebStory (Versión 2.0)
Sitio Web + Backend Spring Boot / Thymeleaf

Integrantes:
Ccasa Yapo, Anngello Fernando
Marca Alegre, Frank Mariano
Mendez Quispe, Luis Angel
Paico Gomez, Luis Eduardo

📌 Descripción del Proyecto

CelularWebStory es un proyecto web desarrollado para la visualización y presentación de teléfonos celulares mediante una interfaz web dinámica creada con Spring Boot y Thymeleaf.

Esta segunda versión del proyecto incorpora múltiples páginas funcionales conectadas mediante un controlador en Java, permitiendo una mejor organización de la navegación del sitio.

La versión actual incluye:
Página principal de inicio del sistema.
Catálogo de productos dinámico usando Thymeleaf.
Modelo de productos implementado en Java.
Navegación entre páginas mediante Spring Boot Controller.
Sistema de carrito de compras.
Página de compra.
Página de ayuda al usuario.
Página de contacto.
Página de garantía.
Página de inicio de sesión.
Integración de imágenes de productos móviles.
Diseño personalizado mediante CSS.

Cómo Ejecutar el Proyecto
⚡ Opción 1: Ejecutar Spring Boot localmente
1. Descargar o clonar el repositorio
Descomprimir el archivo ZIP del proyecto o clonar desde GitHub.

2. Abrir el proyecto en Visual Studio Code o IntelliJ/NetBeans
Abrir la carpeta:
CelularWebStory-master

3. Ejecutar el proyecto
Abrir terminal en la raíz del proyecto y ejecutar:
./mvnw.cmd spring-boot:run

4. Abrir el navegador
Ingresar a:
http://localhost:8080/inicio


🔧 Tecnologías Implementadas
| Tecnología         | Propósito                   |
| ------------------ | --------------------------- |
| Java 17            | Lógica del sistema          |
| Spring Boot        | Framework backend           |
| Thymeleaf          | Plantillas dinámicas        |
| HTML5              | Estructura web              |
| CSS3               | Diseño y estilos            |
| Maven              | Gestión de dependencias     |
| Dockerfile         | Configuración de contenedor |
| Visual Studio Code | Desarrollo del proyecto     |

✅ Funcionalidades Implementadas
🏗️ Estructura General del Sitio
✔ Página principal (inicio)
✔ Navegación entre vistas del sistema
✔ Diseño personalizado mediante CSS
✔ Imágenes integradas de celulares
✔ Aplicación estructurada con Spring Boot MVC

📱 Catálogo de Productos
Se implementó una sección de productos dinámica conectada mediante un controlador Java.
Actualmente se muestran celulares como:
Galaxy A04
Galaxy S23 Ultra
Cada producto contiene:
✔ ID del producto
✔ Nombre
✔ Precio
✔ Stock disponible
✔ Imagen del producto

La información se genera dinámicamente desde el controlador:
WebController.java
usando una lista en memoria mediante el modelo:
Producto.java

🧠 Backend y Arquitectura MVC
Controlador Principal
src/main/java/com/example/celular/Controller/WebController.java
Este controlador administra las rutas del sistema:
| Ruta         | Función                  |
| ------------ | ------------------------ |
| /inicio      | Página principal         |
| /productos   | Catálogo de celulares    |
| /producto    | Información del producto |
| /carrito     | Carrito de compras       |
| /compra      | Página de compra         |
| /ayuda       | Centro de ayuda          |
| /contactanos | Contacto                 |
| /garantia    | Garantía                 |
| /login       | Inicio de sesión         |

## 📁 Estructura del Proyecto

```text
CelularWebStory-master/
│── .mvn/
│── .vscode/
│── Dockerfile
│── mvnw
│── mvnw.cmd
│── pom.xml
│── src/
│   └── main/
│       ├── java/
│       │   └── com/example/celular/
│       │       ├── Controller/
│       │       │   └── WebController.java
│       │       ├── Model/
│       │       │   └── Producto.java
│       │       └── CelularApplication.java
│       │
│       └── resources/
│           ├── application.properties
│           ├── static/
│           │   ├── css/
│           │   └── imagenes/
│           │
│           └── templates/
│               ├── inicio.html
│               ├── productos.html
│               ├── Carrito.html
│               ├── Compra.html
│               ├── Ayuda.html
│               ├── Contactanos.html
│               ├── Garantia.html
│               └── sesion.html
```

⚙️ Dependencias Utilizadas
Dentro del archivo:
pom.xml
se implementaron las siguientes dependencias:
Spring Boot Starter Web MVC: Permite manejar rutas y navegación web.

Spring Boot Thymeleaf: Permite generar vistas dinámicas HTML.

Spring Boot DevTools: Facilita la actualización automática del proyecto durante el desarrollo.

Maven Wrapper: Permite ejecutar el proyecto sin instalar Maven manualmente.

📚 Aprendizajes del Segundo Avance
Durante este segundo avance del proyecto se aprendió a:
Implementar una aplicación usando Spring Boot.
Crear controladores para manejar rutas web.
Integrar Thymeleaf con páginas HTML.
Organizar el proyecto mediante arquitectura MVC.
Crear modelos de datos en Java.
Mostrar información dinámica de productos.
Gestionar dependencias con Maven.
Organizar recursos estáticos como imágenes y estilos CSS.
