## Endpoints

| Description        | URL                                                 |
| ------------------ | --------------------------------------------------- |
| Total bank balance | `http://localhost:8080/account/balance`             | 
| Account balance    | `http://localhost:8080/account/balance/{accountId}` |

## Building and deploying the application

### Building the application

The project uses [Gradle](https://gradle.org) as a build tool. It already contains
`./gradlew` wrapper script, so there's no need to install gradle.

To build the project execute the following command:

```bash
  ./gradlew build
```

### Running the application

Create the image of the application by executing the following command:

```bash
  ./gradlew assemble
```
Create the fatJar image of the application by executing the following command:

```bash
  ./gradlew bootJar
```

### Debugging

Embedded in-memory database console: `http://localhost:8080/h2-console`
