# techshop

Online store with product catalog, shopping cart, invoicing, and role-based access. Built with Spring Boot and Thymeleaf.

## Features

- Product catalog with categories, browsing, and price-range queries
- Shopping cart and checkout flow, generating invoices (`factura`) and sale line items (`venta`)
- Role-based access control with dynamic, database-driven route permissions
- User registration with email confirmation
- Product images stored in Firebase Cloud Storage
- Multi-language UI (English, Spanish, French, Portuguese)

## Stack

| Technology       | Version | Purpose                |
|------------------|---------|------------------------|
| Java             | 21      | Runtime                |
| Spring Boot      | 4.x     | Backend                |
| Thymeleaf        | -       | Server-side templates  |
| Bootstrap        | 5.3     | UI                     |
| MySQL            | 8.0+    | Database               |
| Spring Security  | -       | Auth and authorization |
| Firebase Storage | -       | Product image hosting  |
| Spring Mail      | -       | Registration emails    |

## Package Layout

```
src/main/java/com/tienda/
  controller/   http handlers (products, categories, cart, users, roles, queries)
  domain/       entities (Producto, Categoria, Factura, Venta, Usuario, Rol, Ruta, Constante)
  repository/   jpa data access
  service/      business logic, including firebase storage and email
  SecurityConfig.java    dynamic role-based route security, driven by the Ruta entity
  StorageConfig.java     firebase storage client bean
  TiendaApplication.java
```

## Setup

**Prerequisites:** JDK 21+, MySQL 8.0+, Maven, a Firebase project with a storage bucket and a service account key

1. Create the database using `src/main/resources/creaTablas.sql`.

2. Copy the env file and fill in your credentials:

   ```sh
   cp .env.example .env
   ```

   You'll need a MySQL user, a Firebase service account JSON file (path set via `FIREBASE_CREDENTIALS_PATH`), and a Gmail account with an app password for outgoing mail.

3. Start the app:

   ```bash
   mvn spring-boot:run
   ```

## License

MIT License, see [LICENSE](LICENSE).
