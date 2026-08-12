# DishDash

A small order-tracking dashboard for a food-delivery kitchen. See incoming orders,
move them through the kitchen (Received → Preparing → Ready → Delivered), and watch
today's revenue.

Two services: a **Spring Boot** API (`/backend`) and an **Angular** dashboard (`/frontend`).

## Prerequisites

- **JDK 17** (`java -version`)
- **Node 20+** (`node --version`)

No Docker, no database to install — the backend uses an in-memory H2 database that is
seeded on startup.

## Run it

Backend (terminal 1):

```bash
cd backend
./mvnw spring-boot:run        # serves http://localhost:8080
```

Frontend (terminal 2):

```bash
cd frontend
npm install
npm start                     # serves http://localhost:4200, proxies /api → :8080
```

Open http://localhost:4200.

## What it does

- Lists all orders, newest first, each with a status you can change from a dropdown.
- Filter the list by status.
- The top bar shows **Revenue today** (today's completed and in-progress orders) and the
  count of active orders.
- Changing an order's status is saved immediately and survives a refresh.

## Tests

```bash
cd backend
./mvnw test
```

## Notes

- Data is seeded fresh on every startup; there is no migration to run.
- Ops has mentioned the numbers "look off sometimes" and that "status changes don't always
  stick," but nobody has dug in.
