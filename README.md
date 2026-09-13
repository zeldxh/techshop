# techshop

online store with product catalog, shopping cart, invoicing, and role-based access. built with spring boot and thymeleaf.

## features

- product catalog with categories, browsing, and price-range queries
- shopping cart and checkout flow, generating invoices (`factura`) and sale line items (`venta`)
- role-based access control with dynamic, database-driven route permissions
- user registration with email confirmation
- product images stored in firebase cloud storage
- multi-language ui (english, spanish, french, portuguese)

## stack

| technology       | version | purpose                    |
|-------------------|---------|----------------------------|
| java              | 21      | runtime                    |
| spring boot       | 4.x     | backend                    |
| thymeleaf         | -       | server-side templates      |
| bootstrap         | 5.3     | ui                         |
| mysql             | 8.0+    | database                   |
| spring security   | -       | auth and authorization     |
| firebase storage  | -       | product image hosting      |
| spring mail       | -       | registration emails        |

## package layout

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

## setup

**prerequisites:** jdk 21+, mysql 8.0+, maven, a firebase project with a storage bucket and a service account key

1. create the database using `src/main/resources/creaTablas.sql`.

2. copy the env file and fill in your credentials:

   ```sh
   cp .env.example .env
   ```

   you'll need a mysql user, a firebase service account json file (path set via `FIREBASE_CREDENTIALS_PATH`), and a gmail account with an app password for outgoing mail.

3. start the app:

   ```bash
   mvn spring-boot:run
   ```

## license

MIT License, see [LICENSE](LICENSE).
