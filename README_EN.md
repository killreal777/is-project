# Space Station Information System

[RU version](README.md)

> Modular Kotlin/Spring Boot application for managing resource production, storage, and trading operations on a space station

## [Wiki](https://github.com/killreal777/is-project/wiki/Home-EN)

## Technologies

- Kotlin 2.3.0
- Spring Boot 4.0.1, Spring Modulith, Spring Web, Spring Data JPA, Spring Security + JWT
- PostgreSQL, Flyway

## Scale

- 19 database tables
- 62 endpoints
- 9 component modules

## Domain

The domain represents a space station. The station's purpose is resource production, storage, and trading.

The station consists of three types of modules:
- **Production Modules** — transform resources through manufacturing cycles
- **Storage Modules** — store resources with capacity management
- **Dock Modules** — provide landing spots for spaceships of different sizes

Modules are built from predefined blueprints. The station can expand by constructing new modules.

The system supports 4 user roles:
- **Owner** — full station and financial management
- **Manager** — module and trade management
- **Engineer** — production operations
- **Pilot** — resource trading with the station

**System Scale:** 9 modules, 19 database tables, 62 API endpoints

## Architecture

Modular monolith with explicit module boundaries via Spring Modulith.

**Station Component Modules:**
- **`production`** — production modules with cycle management (Off, Ready, Manufacturing)
- **`storage`** — storage modules with capacity and resource management
- **`dock`** — docking modules with landing spot distribution by ship size

**Other Modules:**
- **`trade`** — trading system: trade policies, offers, trade execution
- **`security`** — JWT authentication, role-based authorization
- **`shared`** — shared components and abstractions

**Shared Modules (shared submodules):**
- **`common`** — base entities, mappers, utilities, configuration
- **`module`** — station module and blueprint abstractions
- **`resource`** — station resource management
- **`user`** — users, roles, accounts, spaceships

**[Modules Documentation](https://killreal777.github.io/is-project/modulith/all-docs.html)**

## Local Run

### Option 1: Infrastructure in Docker, Application Locally

**Requirements:** JDK 21, Docker

```bash
# Start PostgreSQL and migrations
docker-compose up

# Run application
./gradlew bootRun
```

### Option 2: Everything in Docker

**Requirements:** Docker

```bash
docker-compose -f docker-compose-app.yml up
```

### Local Application Access

- [Swagger UI](http://localhost:8080/swagger-ui.html)
- [Logs](./logs)

