# 🏪 Mercado Incaico — Sistema de Gestión

> Proyecto universitario desarrollado para el curso de **Análisis y Diseño de Sistemas II**

---

## 👥 Integrantes

| Nombre | Código |
|--------|--------|
| Portocarrero Huallullo, Pier Antoni | 2121232 |
| Gallegos Ramos, Mauricio Santiago | 2120508 |
| Villar Rosado, Maylor Mar | 2213792 |
| Tirado Cainicela, Arnethe Araceli | 2011078 |
| Baca Taipe, Luis Enrique | 1220103 |

---

## 📋 Descripción del Proyecto

Sistema de gestión integral para el **Mercado Incaico**, diseñado para cubrir los procesos de ventas, compras, almacén y atención de reclamos. El proyecto abarca desde el análisis de requerimientos hasta el diseño de la arquitectura de software, incluyendo modelos UML, base de datos y estructura de capas.

---

## 🧩 Casos de Uso Principales

- **Gestión de Ventas** — Consulta de productos, generación de tickets de pedido, registro de comprobantes de pago y control de stock.
- **Gestión de Compras** — Consulta de pedidos, generación de órdenes de compra, registro de proveedores y guías de entrada.
- **Gestión de Almacén** — Consulta de guías de productos, registro de productos a reabastecer, generación de informes de reabastecimiento, consulta de inventario y alerta de productos próximos a vencer.
- **Gestión de Reclamos** — Registro y consulta de informes de reclamo, generación de informes y emisión de notas de crédito.

---

## 🏗️ Arquitectura del Software

El sistema sigue un patrón **MVC por capas** con el patrón **DAO (Data Access Object)**:

### Capas

| Capa | Paquete base | Descripción |
|------|-------------|-------------|
| **Presentación** | `com.ads.web.resources.templates` | Vistas HTML (home, login, productos, ventas) |
| **Controladora** | `com.ads.web.*.controller` | Servlets/Controllers por módulo |
| **Negocio (Service)** | `com.ads.web.*.service` | Lógica de negocio e interfaces de servicio |
| **Persistencia (DAO)** | `com.ads.web.*.repository` | Acceso a datos, conexión a BD |

---

## 🛠️ Tecnologías Utilizadas

| Categoría | Tecnología |
|-----------|-----------|
| Backend | Java / Spring Boot |
| Frontend | HTML, CSS, React.js / Angular |
| Base de Datos | Microsoft SQL Server |
| Modelado UML | Enterprise Architect / Visual Paradigm |
| Control de Versiones | Git |

---

## 🗄️ Modelo de Base de Datos

Las principales tablas del sistema son:

- `usuario` — Usuarios del sistema con roles (cajero, vendedor, almacenero)
- `ventas` / `detalle_venta` — Registro de ventas y productos vendidos
- `productos` / `categoria` — Catálogo de productos con stock
- `ordenes_compra` / `detalle_orden_compra` — Pedidos de reabastecimiento
- `proveedores` — Registro de proveedores
- `reclamos` — Reclamos asociados a ventas y usuarios

---

## ⚙️ Requisitos No Funcionales

| Clasificación | Requisito |
|---------------|-----------|
| **Seguridad** | Protección de datos personales y comerciales conforme a normativas de privacidad (GDPR) |
| **Usabilidad** | Interfaz intuitiva con soporte de manuales y ayuda en línea |
| **Rendimiento** | Tiempo de respuesta menor a 3 segundos en operaciones principales |
| **Disponibilidad** | 99.9% de tiempo activo; recuperación ante fallos en menos de 1 hora |
| **Escalabilidad** | Soporte para crecimiento en usuarios y transacciones sin degradación |
| **Compatibilidad** | Integración con sistemas de facturación e inventario; acceso desde PC y móviles |

---

## 📐 Actores del Sistema

- **Vendedor** — Consulta productos y genera tickets de pedido
- **Cajero** — Registra comprobantes de pago, gestiona reclamos y notas de crédito
- **Operador de Almacén** — Gestiona inventario, proveedores y órdenes de compra

---

## 📁 Estructura del Proyecto (Backend)

```
com.ads.web/
├── almacen/
│   ├── controller/   (AlmacenController.java)
│   ├── service/      (OperadorAlmacenService.java)
│   └── repository/   (OperadorAlmacenDAO.java)
├── ventas/
│   ├── controller/   (VentaController.java)
│   ├── service/      (ProductoService.java, VentaService.java)
│   └── repository/   (ProductoDAO.java)
├── compras/
│   ├── controller/   (CompraController.java)
│   ├── service/      (CompraService.java)
│   └── repository/   (CompraDAO.java)
├── reclamos/
│   ├── controller/   (ReclamoController.java)
│   ├── service/      (ReclamoService.java)
│   └── repository/   (ReclamoDAO.java)
└── resources/
    └── templates/    (home.html, login.html, ventas.html, ...)
```

---

## 📄 Documentación

El proyecto incluye los siguientes artefactos de análisis y diseño:

- Modelo de Requerimientos (Especificación de Software)
- **Modelo de Análisis y Diseño** ← este repositorio
  - Diagramas de Clases de Análisis y Diseño
  - Diagramas de Secuencia por escenario
  - Modelo Lógico y Físico de Datos (Diccionario de Datos)
  - Vista de Capas y Subsistemas
  - Vista de Despliegue
  - Diagrama de Componentes

---

*Curso: Análisis y Diseño de Sistemas II — Fecha: 25/02/2025*
