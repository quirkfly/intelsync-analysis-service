
# Analysis Service

The `analysis-service` is a Spring Boot microservice responsible for analyzing ingested text and extracting named entities (e.g., person names), which are stored as graph data in ArangoDB. This service is designed to work in coordination with the ingestion and search modules of the IntelSync platform.

---

## 🔧 Features

- Entity extraction from free-form text using Apache OpenNLP
- Heuristic fallback support
- Stores extracted entities in ArangoDB per case
- Configurable through `application.yml`
- REST API for extraction and persistence

---

## 📁 Project Structure

```bash
analysis-service/
├── controller/
├── service/
├── model/
├── config/
├── resources/
│   └── application.yml
├── pom.xml
└── README.md
```

---

## ⚙️ Configuration

Add this to your `application.yml`:

```yaml
arangodb:
  host: localhost
  port: 8529
  username: intelsync_user
  password: XXXXXX
  database: intelsync_db
  protocol: HTTP

opennlp:
  person-model-path: models/en-ner-person.bin
```

The OpenNLP model should be placed under `src/main/resources/models/`.

---

## 🚀 API Documentation

### Base URL

```
POST http://localhost:8082/api/analysis
```

---

### 🧠 Extract Named Entities

**Endpoint:**
```
POST /api/analysis/entities
```

**Description:**
Extracts person names from a block of raw text using OpenNLP.

**Request Body:**
```text
A string of plain text to analyze.
```

**Example cURL:**
```bash
curl -X POST http://localhost:8082/api/analysis/entities   -H "Content-Type: text/plain"   --data "John Doe and Jane Smith were present at the location."
```

**Response:**
```json
{
  "persons": [
    "John Doe",
    "Jane Smith"
  ]
}
```

---

### 💾 Save Extracted Entities

**Endpoint:**
```
POST /api/analysis/save-entities
```

**Description:**
Saves a list of extracted person names to ArangoDB, associated with a specific case.

**Request Parameters:**
- `caseId`: required (string)

**Request Body:**
```json
[
  "John Doe",
  "Jane Smith"
]
```

**Example cURL:**
```bash
curl -X POST "http://localhost:8082/api/analysis/save-entities?caseId=case-123"   -H "Content-Type: application/json"   -d '["John Doe", "Jane Smith"]'
```

**Response:**
```json
{
  "message": "Entities saved successfully"
}
```

---

## 🐳 Docker ArangoDB (Optional)

```yaml
version: '3'
services:
  arangodb:
    image: arangodb:latest
    ports:
      - "8529:8529"
    environment:
      ARANGO_ROOT_PASSWORD: your_password
    volumes:
      - arango-data:/var/lib/arangodb3
volumes:
  arango-data:
```

Start with:

```bash
docker-compose up -d
```

---

## 🧪 Local Run

```bash
mvn spring-boot:run
```

---

## 📬 Contact

For support, reach out to the IntelSync dev team or open an issue in the project repository.

---
