# Documentación de la Aplicación E-commerce

Este documento describe la estructura y funcionalidad de una aplicación de comercio electrónico basada en una arquitectura de microservicios. La aplicación se organiza en tres componentes principales:

1. **Front E-commerce**: Interfaz de usuario que interactúa con los clientes.
2. **Auth**: Microservicio responsable de la autenticación, incluyendo el registro y el inicio de sesión de usuarios.
3. **Market**: Microservicio encargado de la gestión de productos, categorías, ítems y carritos de compra.

## Tabla de Contenidos

1. [Estructura del Proyecto](#estructura-del-proyecto)
2. [Front E-commerce](#front-e-commerce)
   - 2.1 [Descripción](#21-descripción)
   - 2.2 [Tecnologías Utilizadas](#22-tecnologías-utilizadas)
   - 2.3 [Instalación y Configuración](#23-instalación-y-configuración)
3. [Auth](#auth)
   - 3.1 [Descripción](#31-descripción)
   - 3.2 [Tecnologías Utilizadas](#32-tecnologías-utilizadas)
   - 3.3 [Instalación y Configuración](#33-instalación-y-configuración)
   - 3.4 [Endpoints Principales](#34-endpoints-principales)
4. [Market](#market)
   - 4.1 [Descripción](#41-descripción)
   - 4.2 [Tecnologías Utilizadas](#42-tecnologías-utilizadas)
   - 4.3 [Instalación y Configuración](#43-instalación-y-configuración)
   - 4.4 [Endpoints Principales](#44-endpoints-principales)
5. [Agradecimientos](#agradecimientos)


## Estructura del Proyecto

La estructura del proyecto es la siguiente:

```
/ecommerce-app
├── /front-ecommerce
├── /aute
└── /market
```

- **/front-ecommerce**: Contiene el código fuente del frontend de la aplicación.
- **/auth**: Contiene el microservicio de autenticación.
- **/market**: Contiene el microservicio de gestión de productos y carritos.

## Front E-commerce

### 2.1 Descripción

El frontend de la aplicación es la interfaz con la que interactúan los usuarios finales. Proporciona funcionalidades como navegación de productos, gestión del carrito de compras, gestión de usuariuos y demas.

### 2.2 Tecnologías Utilizadas

- **Framework**: Angular
- **Gestión de Estado**: Ngrx
- **Enrutamiento**: Angular Router
- **HTTP Client**: HttpClientModule

### 2.3 Instalación y Configuración

1. **Clonar el repositorio**:

   ```bash
   git clone https://github.com/RicharLeon/E-Commerce
   cd ecommerce-app/front-ecommerce
   ```

2. **Instalar dependencias**:

   ```bash
   npm install
   ```



3. **Iniciar la aplicación**:

   ```bash
   npm start
   ```

## Auth

### 3.1 Descripción

El microservicio **Auth** gestiona la autenticación de usuarios, incluyendo el registro, inicio de sesión y manejo de tokens de acceso.

### 3.2 Tecnologías Utilizadas

- **Lenguaje**: Java
- **Framework**: Spring Boot
- **Autenticación**: Spring Security con JWT

### 3.3 Instalación y Configuración

1. **Clonar el repositorio**:

   ```bash
   git clone https://github.com/usuario/ecommerce-app.git
   cd ecommerce-app/auth
   ```

2. **Instalar dependencias**:

   ```bash
   ./mvnw clean install
   ```

3. **Configurar variables de entorno**:

    Modificar el archivo application.properties ubicado en src/main/resources/ con las siguientes propiedades:

   ```env
    spring.datasource.url=jdbc:mysql://localhost:3306/aute_db
    spring.datasource.username=tu_usuario
    spring.datasource.password=tu_contraseña

   ```


4. **Ejecutar la aplicación**:

   ```bash
   ./mvnw spring-boot:run
   ```

### 3.4 Endpoints Principales

- **Registro de Usuario**: `POST /auth/register`
- **Inicio de Sesión**: `POST /auth/login/`
- **Refrescar Token**: `POST /auth/refresh/`

## Market

### 4.1 Descripción

El microservicio **Market** se encarga de la gestión de productos, categorías, ítems y carritos de compra. Proporciona las operaciones CRUD necesarias para estas entidades.

### 4.2 Tecnologías Utilizadas

- **Lenguaje**: Java
- **Framework**: Spring Boot
- **Autenticación**: Spring Security con JWT


### 4.3 Instalación y Configuración

1. **Clonar el repositorio**:

   ```bash
   git clone https://github.com/usuario/ecommerce-app.git
   cd ecommerce-app/auth
   ```

2. **Instalar dependencias**:

   ```bash
   ./mvnw clean install
   ```

3. **Configurar variables de entorno**:

    Modificar el archivo application.properties ubicado en src/main/resources/ con las siguientes propiedades:

   ```env
    spring.datasource.url=jdbc:mysql://localhost:3306/aute_db
    spring.datasource.username=tu_usuario
    spring.datasource.password=tu_contraseña

   ```


4. **Ejecutar la aplicación**:

   ```bash
   ./mvnw spring-boot:run
   ```
   
### 4.4 Endpoints Principales

- **Gestión del Carrito**: `POST /carrito`
- **Gestión de los Items**: `POST /carrito-items`
- **Gestión de la Categoria**: `POST /category`
- **Gestión de Pagos**: `POST /pay`
- **Gestión de Productos**: `POST /products`
- **Gestión de Reportes**: `POST /reportes`


## Agradecimientos

Quiero expresar mi sincero agradecimiento al entorno que me ha permitido llevar a cabo este proyecto de forma independiente. La disponibilidad de recursos en línea, comunidades de desarrolladores y documentación accesible han sido fundamentales para superar los desafíos técnicos y conceptuales que surgieron durante el desarrollo de la aplicación.