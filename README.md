# ATA IT - API Automation Assessment

This repository contains the API automation test suite for the GoRest API (https://gorest.co.in/), designed as part of the ATA IT QA Assessment.

## 🚀 How to Run the Tests

This project is built using Java 17, REST Assured, and JUnit 5. It uses Maven for dependency management and test execution.

**Prerequisites:**
- Java JDK 17 or higher
- Apache Maven

**Step 1: Environment Setup**
Due to security best practices, the API token is not committed to this repository. You need to create a `.env` file at the root of the project.
1. Create a file named `.env` in the root directory.
2. Add your GoRest API token as follows:
   ```env
   GOREST_API_TOKEN=your_actual_token_here

Step 2: Execute Tests
Run the following commands in your terminal:

git clone https://github.com/anurakanr-prog/ata-it-api-automation

cd ata-it-api-automation

mvn clean test

🏗️ Architectural Decisions
Java & REST Assured over Scripting Languages: Chosen for strong type safety (preventing payload structure errors) and seamless integration with enterprise backend ecosystems.

API Client Layer (Client Pattern): Similar to the Page Object Model in UI testing, I abstracted the API endpoints into a UserClient class. This promotes reusability; tests do not need to construct headers or base URIs repeatedly.

POJO Data Models (Serialization/Deserialization): Instead of using raw JSON strings or HashMaps, I implemented a User model using Jackson. This ensures strict schema validation and provides auto-completion for test assertions.

Independent Tests: Every test case that mutates data (PUT, DELETE) is responsible for its own data setup (creating a new user first). This prevents cascading failures and ensures tests can be run in parallel or in any order.

Config & Secrets Management: Implemented dotenv-java to keep sensitive Bearer tokens completely out of the source code.

Detailed Logging on Failure: The client is configured with .enableLoggingOfRequestAndResponseIfValidationFails(). The console remains clean when tests pass, but provides full context (Request + Response) when an assertion fails.

🧪 Scenarios Tested & Why
I aimed for a mix of Happy Path (CRUD) operations, Data integrity checks, and Negative/Edge cases to ensure system robustness.

Happy Path (CRUD):

GET /users: Verifies endpoint availability and list retrieval.

GET /users/{id}: Validates specific user retrieval matching the schema.

POST /users: Validates successful user creation and ID generation.

PUT /users/{id}: Verifies data modification updates correctly in the database.

DELETE /users/{id}: Ensures the system returns 204 No Content and the record is no longer accessible (404 Not Found).

Negative & Edge Cases:

POST - 401 Unauthorized: Validates security configurations by simulating a request with an invalid/expired token.

POST - 422 Missing Required Fields: Verifies the API's internal validation by sending a payload without an email.

POST - 422 Duplicate Data: Simulates creating a user with an already existing email to ensure the database constraints hold up.

(Dynamic test data generation using UUID was implemented to ensure email uniqueness per test run, preventing duplicate conflicts.)

🔮 What I'd Do Differently With More Time
If given more time in a real-world scenario, I would enhance the framework by:

JSON Schema Validation: Implementing rest-assured-json-schema-validator to strictly verify the contract/types of the response beyond just the values.

CI/CD Integration: Adding a GitHub Actions (.yml) or Jenkinsfile to trigger mvn clean test automatically on every Pull Request.

Allure Reporting: Integrating Allure for rich, visual HTML test reports that can be easily digested by stakeholders.

Parallel Execution: Configuring JUnit 5 to run tests concurrently to reduce execution time as the test suite grows.
