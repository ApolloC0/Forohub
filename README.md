# Forohub
Proyecto de desafio para curso de AluraLatam 

API REST para un sistema de foros con autenticación JWT, gestión de tópicos y usuarios.

📌 Características Principales Autenticación segura con JWT (JSON Web Tokens)

CRUD completo para tópicos y usuarios

Validación robusta de datos

Manejo centralizado de errores

Documentación con Swagger (próximamente)

🚀 Requisitos Técnicos Java 17+

Maven 3.8+

PostgreSQL 14+

Insomnia/Postman para pruebas

⚙️ Configuración Clonar el repositorio:

bash git clone https://github.com/tu-usuario/foro-challenge-api.git cd foro-challenge-api Configurar base de datos:

Crear una base de datos PostgreSQL

Configurar las credenciales en application.properties:

properties spring.datasource.url=jdbc:postgresql://localhost:5432/foro_db spring.datasource.username=tu_usuario spring.datasource.password=tu_contraseña spring.jpa.hibernate.ddl-auto=update Variables de entorno (opcional):

properties 
JWT_SECRET=tu_clave_secreta_para_jwt 
JWT_EXPIRATION=86400000 #24 horas en ms 
🏗️ Estructura del Proyecto 
text src/ 
├── main/ 
│ 
├── java/ 
│ 
│ 
└── com/forochallenge/api/ 
│ 
│ 
├── config/ # Configuraciones 
│ 
│ 
├── controller/ # Controladores REST 
│ 
│ 
├── dto/ # Objetos de transferencia 
│ 
│ 
├── entity/ # Entidades de BD 
│ 
│ 
├── exception/ # Manejo de errores 
│ 
│ 
├── repository/ # Repositorios JPA 
│ 
│ 
├── security/ # Config seguridad 
│ 
│ 
└── service/ # Lógica de negocio 
│ 
└── resources/ 
│ 
└── application.properties 

🔐 Endpoints de Autenticación Registro de usuario text POST 

/api/auth/register Content-Type: application/json

{ "nombre": "Usuario Ejemplo", "email": "usuario@example.com", "password": "contraseñaSegura123" } Inicio de sesión text POST /api/auth/login Content-Type: application/json

{ "email": "usuario@example.com", "password": "contraseñaSegura123" } 📝 Endpoints de Tópicos Crear tópico (requiere autenticación) text POST /topicos Authorization: Bearer <JWT_TOKEN> Content-Type: application/json

{ "titulo": "Mi primer tópico", "mensaje": "Contenido del tópico..." } Listar tópicos text GET /topicos 🛠️ Ejecución Compilar y ejecutar:

bash mvn spring-boot:run Acceder a la API:

text http://localhost:8080 🧪 Pruebas Ejecutar tests unitarios:

bash mvn test
