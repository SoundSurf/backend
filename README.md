## Swagger endPoint
{host}/api-docs

## Env variables set
```
DB_CATALOG= ;DB_HOST=;DB_PASSWORD=;DB_PORT=;DB_USERNAME=
```


## How to Run the Project

### Creating Test Database Script

To create the test database script, run the following Gradle task:

```
./gradlew createTestDbScript
```


## Starting the Database

To start the database, run the following Gradle task:

```
./gradlew dbUp
```

## Stopping the Database

To stop the database, run the following Gradle task:

```
./gradlew dbDown
```

## Starting the Test Database

To start the test database, run the following Gradle task:

```
./gradlew testDbUp
```

## Stopping the Test Database

To stop the test database, run the following Gradle task:

```
./gradlew testDbDown
```
