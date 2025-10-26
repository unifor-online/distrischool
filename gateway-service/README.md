# Gateway Service

API Gateway for Distrischool microservices architecture.

## Overview

This gateway service provides a single entry point for all microservices in the Distrischool platform.

## Features

- ✅ Centralized routing to all microservices
- ✅ CORS configuration
- ✅ Circuit Breaker pattern with Resilience4j
- ✅ Load balancing
- ✅ Request/Response logging
- ✅ Health monitoring
- ✅ Fallback responses for service failures

## Routes

### Main Services
- **User Service**: `http://localhost:8080/api/users/**`
- **Student Service**: `http://localhost:8080/api/students/**`
- **Teacher Service**: `http://localhost:8080/api/teachers/**`
- **Admin Staff Service**: `http://localhost:8080/api/admin/**`

### Service Health Monitoring
- **User Service Health**: `http://localhost:8080/services/user/actuator/health`
- **Student Service Health**: `http://localhost:8080/services/student/actuator/health`
- **Teacher Service Health**: `http://localhost:8080/services/teacher/actuator/health`
- **Admin Service Health**: `http://localhost:8080/services/admin/actuator/health`

### Gateway Management
- **Gateway Health**: `http://localhost:8080/actuator/health`
- **Gateway Routes**: `http://localhost:8080/actuator/gateway/routes`
- **Gateway Metrics**: `http://localhost:8080/actuator/metrics`

## Running Locally

```bash
mvn spring-boot:run
```

## Running with Docker

```bash
docker build -t gateway-service .
docker run -p 8080:8080 gateway-service
```

## Configuration

Environment variables:
- `PORT`: Server port (default: 8080)

## Circuit Breaker

The gateway implements circuit breaker pattern for all downstream services with the following default configuration:
- Sliding window size: 10 calls
- Failure rate threshold: 50%
- Wait duration in open state: 10 seconds
- Timeout duration: 5 seconds

## Testing

Example requests through the gateway:

```bash
# List all teachers
curl http://localhost:8080/api/teachers

# Create a student
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{"nome":"João Silva","matricula":"2024001"}'

# Get user by ID
curl http://localhost:8080/api/users/1
```
