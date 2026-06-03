# Gilded Rose Inventory Engine

An automated inventory management system built with Spring Boot 4.x and Java 17. The system dynamically upgrades or degrades shop inventory quality and sell-in values on a daily cycle based on flexible, externalized item aging policies.

## Architecture Overview

This project is built following **Hexagonal Architecture (Ports & Adapters)** and **Clean Architecture** principles. The business logic is entirely isolated from external frameworks, database dependencies, and web interfaces.

[ gildedrose-inventory-service ] (Web / REST)
│
▼
[ gildedrose-inventory-adapters ] (Infra implementation)
│
▼
[ gildedrose-inventory-database ] (JPA / H2 Persistence Implementation)
│
▼
[  gildedrose-inventory-core  ] (Domain, Inbound/Outbound Ports, Rules)

### Module Breakdown

1. **`gildedrose-inventory-core`**
    * **Purpose:** Contains core business entities (`AgingItem`), specialized aging rules/policies (`AgedBrieAgingPolicy`), and Hexagonal interfaces (Ports).
    * **Dependencies:** Zero external framework or infrastructure dependencies. Pure Java.
2. **`gildedrose-inventory-database`**
    * **Purpose:** Implements outbound ports (Driven Ports) like `InventoryRepositoryPort`. Manages database entities (`ItemEntity`) and maps them back and forth to domain objects.
    * **Dependencies:** Depends on `core`. Uses Spring Data JPA and Hibernate.
3. **`gildedrose-inventory-adapters`**
    * **Purpose:** The driving application layer (Driving Adapters). infrastructure driven
    * **Dependencies:** Depends on `database` and `core`.
4. **`gildedrose-inventory-service`**
    * **Purpose:**  Contains REST endpoints and the main `@SpringBootApplication` runtime engine.
    * **Dependencies:** Depends on `adapters` and `core`.
5 **`gildedrose-inventory-coverage`**
    * **Purpose:** Aggregates JaCoCo instruction metrics across all child sub-modules to compile a unified code coverage report.

## 👨‍💻 Technical Walkthrough: How I Approached the Refactoring

When I took on the Gilded Rose Kata, my goal wasn't just to make the current code run or patch in a quick fix. I wanted to treat it exactly like an enterprise system undergoing a major legacy modernization. The original code had the classic "Big Ball of Mud" issues—deeply nested `if-else` loops, hardcoded parameters.

Here is the exact step-by-step approach I took to turn it into a clean, future-proof system:

### Step 1: I built a safety net before touching production code
Before changing a single line of application code, I established a bulletproof automated testing suite using **Junit Test**. I mapped out all the legacy business logic constraints—from standard item degradation to special rules like Aged Brie and concert ticket quality. Having this regression testing net meant that if any optimization steps accidentally broke an historical edge case, my test suite would flag it immediately. It gave me the absolute freedom to refactor aggressively.

### Step 2: I broke down the monolith using Hexagonal Architecture
To keep the core business rules completely safe from infrastructure changes, I migrated the project to a multi-module Maven design following **Ports and Adapters** principles.
* I isolated the **Core module** to keep it pure meaning it has absolutely no knowledge of databases, or API protocols.
* I then isolated the infrastructure layers, spinning up a dedicated **Database module** to manage JPA entities and an **Adapters module** to control infra layer.

### Step 3: I killed the conditional blocks using Polymorphism
To solve the maintenance nightmare of the massive `if-else` statement, I implemented the **Strategy Pattern**. I created a clean, unified `ItemAgingPolicy` interface and extracted each item's distinct behavior into its own standalone class—like `AgedBrieAgingPolicy`. This completely fulfills the **Open-Closed Principle**. If the business introduces a brand-new promotional item tomorrow, I don’t have to open up an existing file and risk introducing bugs; I just drop in a brand-new policy class.

### Step 4: I externalized and strongly typed the business parameters
Hardcoded numbers inside code are a major technical liability. I extracted all quality caps, minimum thresholds, and aging rates completely out of the logic and moved them into an external YAML configuration file (`gilded-rose-item-aging-policy.yaml`). To keep it safe, I mapped those properties to immutable Java records (`ItemAgingPolicySettings`) using Spring Boot’s `@ConfigurationProperties` and injected them right into the policy constructors. Now, tuning how fast items age is a configuration change, not a code rewrite.

### Step 5: The Final Verification Layer — Wiring up Cucumber BDD
As the absolute final checkpoint of my refactoring process, I formally wired our comprehensive BDD test definitions to execution hooks inside the application runtime.

---

## Forward-Looking Architecture (Placeholders for Future Enhancements)

To preserve strict isolation between core logic and infrastructure, the architecture designates explicit plugin points for scaling features without modifying core engines:

* **Adapters Placeholder:** Stubs are reserved in the `gildedrose-inventory-adapters` module to seamlessly support for infrastructure.
* **Database Placeholder:** Persistence mappings within `gildedrose-inventory-database` isolate the underlying relational schemas. This structural layer acts as an infrastructure placeholder to support swapping out the in-memory H2 store for high-availability production cluster platforms (such as PostgreSQL or MongoDB NoSQL backends) by simply altering data adapters.
* **Service Placeholder:** Stubs are reserved in the `gildedrose-inventory-service` module to seamlessly support new driving channels without breaking the REST controllers.

---

## ⚙️ Configuration & Database Properties

All operational inventory policies and database pool strategies are fully externalized via YAML configurations.

### Rule-Set Parameterization (`gilded-rose-item-aging-policy.yaml`)
Item logic configurations use safe type-bound record trees (`ItemAgingPolicySettings`) to customi

### Building and Running the System
mvn clean install

After completion, open the generated test report in your browser to inspect coverage details:
gildedrose-inventory-coverage/target/site/jacoco-aggregate/index.html

## Launch the Service Application
mvn spring-boot:run -pl gildedrose-inventory-service