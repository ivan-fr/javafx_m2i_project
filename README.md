# JavaFX Project

A simple JavaFX application built with Java 21 and Maven.

## Prerequisites

- Java 21 (OpenJDK 21)
- Maven 3.9+

## Project Structure

```
java_fx_project/
├── pom.xml
├── README.md
└── src/main/java/com/project/
    └── Main.java
```

## How to Run

**Option 1: Single command (recommended)**
```bash
mvn clean javafx:run
```

## Documentation

To generate the Javadoc documentation, run the following command:

```bash
mvn javadoc:javadoc
```
The generated documentation will be available in `target/reports/apidocs/index.html`.