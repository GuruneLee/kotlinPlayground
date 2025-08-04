# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Structure

This is a multi-module Kotlin/Java Gradle project containing various proof-of-concept implementations and tutorials:

- **Root project**: Main build configuration with submodules
- **shared**: Shared utilities and common code (file system repositories, CSV/properties utilities, logging)
- **poc-lock**: Spring Boot application demonstrating database locking patterns with JPA
- **poc-status-pattern**: JPA playground with post status pattern implementation  
- **poc-restdocs-adoc**: Spring REST Docs with AsciiDoc documentation generation
- **poc-swagger-ui**: Spring Boot with Swagger UI integration
- **tuto-async**: Java concurrency tutorials with CompletableFuture and threading
- **tuto-memoryleak**: Memory leak tutorial examples

## Build Commands

### Main Build Tasks
```bash
./gradlew build          # Build all modules
./gradlew test           # Run tests for all modules
./gradlew clean          # Clean build artifacts
./gradlew assemble       # Assemble without tests
```

### Module-Specific Tasks
```bash
./gradlew :shared:build              # Build shared module only
./gradlew :poc-lock:bootRun          # Run Spring Boot lock POC
./gradlew :poc-status-pattern:bootRun # Run status pattern POC
./gradlew :poc-restdocs-adoc:bootRun  # Run REST docs POC
./gradlew :poc-swagger-ui:bootRun     # Run Swagger UI POC
```

### Single Test Execution
```bash
./gradlew :shared:test --tests "FileSystemRepositoryTest"
./gradlew :poc-lock:test --tests "CouponIssueServiceTest"
```

## Architecture Notes

### Spring Boot Modules
Most POC modules use Spring Boot 3.4.x with:
- Kotlin 2.1.0
- Java 21 toolchain
- JPA with H2/MySQL databases
- Spring Web for REST APIs

### Key Patterns Demonstrated
- **Database locking**: Optimistic and pessimistic locking strategies in poc-lock
- **Status pattern**: State machine implementation for post entities in poc-status-pattern
- **Documentation**: REST API documentation with Spring REST Docs in poc-restdocs-adoc
- **File operations**: Reusable file system utilities in shared module
- **Async programming**: CompletableFuture and threading examples in tuto-async

### Testing Strategy
- JUnit 5 platform across all modules
- Spring Boot Test for integration testing
- MockK for Kotlin mocking (where used)
- Test resources in standard Maven layout

## Known Issues
- Kotlin Gradle plugin loaded multiple times warning (affects build performance but not functionality)
- Consider centralizing plugin management in root build.gradle.kts