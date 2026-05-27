# IntegrityToolBackend — User REST API

> Java · Spring Boot 3.2 · PostgreSQL · JWT · Swagger

[![Java](https://img.shields.io/badge/Java-17-ED8B00?style=flat-square&logo=java)](https://www.java.com)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2-6DB33F?style=flat-square&logo=spring-boot)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-336791?style=flat-square&logo=postgresql)](https://www.postgresql.org)
[![Swagger](https://img.shields.io/badge/Swagger-OpenAPI_3-85EA2D?style=flat-square&logo=swagger)](http://localhost:8080/swagger-ui.html)

Part of the [IntegrityTool](https://github.com/integrity-tool) platform — see the org README for full system architecture.

---

## What this service does

The user-facing REST API for the IntegrityTool platform. Handles patient registration, authentication, insurance policy management, claim submission, and patient service record retrieval. Built around an abstract `Person` hierarchy using Java inheritance and interface-driven design.

---

## Domain model

```
Person (abstract)
├── Patient
│     ├── patientCondition
│     ├── patientRelationshipToInsured
│     ├── referringProvider
│     ├── hospitalizationDate
│     ├── priorAuthorizationNumber
│     └── → PatientServiceRecord (CPT/HCPCS, diagnosis, charges)
├── Doctor
│     └── → EDIFile (upload, backup, integrity check)
├── HealthCareProvider
└── HealthInsuranceProvider
      └── → ProviderVerification (interface)
```

Key entities managed by this service:

| Entity | Description |
|---|---|
| `Person` | Abstract base — name, gender, DOB, address, health insurance no. |
| `Patient` | Extends Person — condition, insurance relationship, service records |
| `PatientServiceRecord` | CPT/HCPCS codes, diagnosis, procedures, charges, service dates |
| `InsurancePolicy` | Policy number, type, effective date, other health benefits |
| `Claim` | Links Person → InsurancePolicy via `person_claim_mapping` |
| `Session` | Explicit session tracking — userId, createdAt, expiresAt, isActive |
| `Address` | streetNo, city, State, zipcode — linked to Person |

---

## Architecture

```
Angular Frontend (IntegrityToolUI)
        │
        │  HTTP + JWT Bearer token
        ▼
┌────────────────────────────────────────┐
│           Spring Boot App              │
│                                        │
│  ┌──────────────┐  ┌────────────────┐  │
│  │ Controllers  │  │  AuthService   │  │
│  │              │  │                │  │
│  │ /auth/*      │  │ login()        │  │
│  │ /person/*    │  │ createSession()│  │
│  │ /claim/*     │  │ checkAuth()    │  │
│  │ /policy/*    │  │ generateToken()│  │
│  │ /service-    │  │ invalidate     │  │
│  │  record/*    │  │  Session()     │  │
│  └──────┬───────┘  └────────────────┘  │
│         │                              │
│  ┌──────▼───────────────────────────┐  │
│  │         Service Layer            │  │
│  │  PatientService  ClaimService    │  │
│  │  PolicyService   ProviderService │  │
│  └──────────────────┬───────────────┘  │
│                     │                  │
│  ┌──────────────────▼───────────────┐  │
│  │      JPA Repositories            │  │
│  └──────────────────┬───────────────┘  │
└─────────────────────┼──────────────────┘
                      │
                      ▼
                 PostgreSQL
```

---

## API endpoints

### Authentication

```
POST   /auth/login              Sign in → returns JWT + session
POST   /auth/logout             Invalidate session
GET    /auth/session            Check session validity
POST   /auth/change-password    Update password (authenticated)
```

### Person / Patient

```
POST   /person/register         Register new patient
GET    /person/{id}             Get person profile
PUT    /person/{id}             Update profile
GET    /person/{id}/address     Get address
PUT    /person/{id}/address     Update address
```

### Insurance policy

```
GET    /policy/{personId}           Get all policies for person
POST   /policy                      Add insurance policy
PUT    /policy/{policyId}           Update policy
GET    /policy/{policyId}/details   Full policy details
```

### Claims

```
POST   /claim                       Submit a new claim
GET    /claim/{personId}            Get all claims for person
GET    /claim/{claimId}/details     Full claim with service records
GET    /claim/{claimId}/cms1500     CMS-1500 mapped output
```

### Patient service records

```
GET    /service-record/{personId}           All service records
GET    /service-record/{recordId}/detail    Single record detail
                                            (CPT, diagnosis, charges)
```

---

## Session management

This service uses explicit server-side sessions alongside JWT tokens — a deliberate design decision for healthcare compliance. Sessions can be invalidated server-side on logout or suspicious activity, unlike pure stateless JWT which cannot be revoked before expiry.

```java
// Session lifecycle
Session session = authService.createSession(user);
// session.sessionId, session.userId, session.createdAt,
// session.expiresAt, session.isActive

// Validation on each request
boolean valid = authService.checkAuthorization(username, token);
```

---

## Getting started

### Prerequisites

- Java 17+
- Maven 3.8+
- PostgreSQL 15

### Setup

```bash
# 1. Clone
git clone https://github.com/integrity-tool/IntegrityToolBackend.git
cd IntegrityToolBackend

# 2. Configure database
# Edit src/main/resources/application.properties:
# spring.datasource.url=jdbc:postgresql://localhost:5432/integritytool
# spring.datasource.username=YOUR_USER
# spring.datasource.password=YOUR_PASSWORD

# 3. Run migrations
mvn flyway:migrate

# 4. Start
mvn spring-boot:run

# API:       http://localhost:8080
# Swagger:   http://localhost:8080/swagger-ui.html
```

### Run tests

```bash
mvn test
mvn jacoco:report    # Coverage report → target/site/jacoco/
```

---

## Project structure

```
IntegrityToolBackend/
├── src/main/java/com/integritytool/
│   ├── controller/
│   │   ├── AuthController.java
│   │   ├── PersonController.java
│   │   ├── ClaimController.java
│   │   └── PolicyController.java
│   ├── service/
│   │   ├── AuthService.java
│   │   ├── PatientService.java
│   │   └── ClaimService.java
│   ├── model/
│   │   ├── Person.java           (abstract)
│   │   ├── Patient.java
│   │   ├── Doctor.java
│   │   ├── HealthCareProvider.java
│   │   ├── HealthInsuranceProvider.java
│   │   ├── PatientServiceRecord.java
│   │   ├── InsurancePolicy.java
│   │   ├── Claim.java
│   │   ├── Session.java
│   │   └── Address.java
│   ├── repository/
│   ├── security/
│   │   └── JwtFilter.java
│   └── interfaces/
│       ├── IProviderVerification.java
│       └── IClaimValidation.java
└── src/test/
```

---

## Related repositories

| Repo | Role |
|---|---|
| [IntegrityToolUI](https://github.com/integrity-tool/IntegrityToolUI) | Angular frontend that consumes this API |
| [IntegrityToolAdmin](https://github.com/integrity-tool/IntegrityToolAdmin) | Admin API (separate service) |
| [IntegrityToolAdminUI](https://github.com/integrity-tool/IntegrityToolAdminUI) | Admin dashboard |

---

## Author

**Krishna Solanki** — [github.com/krishnasolanki](https://github.com/krishnasolanki) · [LinkedIn](https://linkedin.com/in/krishnasolanki) · Montreal, QC
