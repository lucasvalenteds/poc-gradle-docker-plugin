# POC: Gradle Docker Plugin

It demonstrate how to manage a [Docker](https://github.com/docker) container lifecycle using the [Gradle Docker plugin](https://github.com/bmuschko/gradle-docker-plugin).

We want to start a Docker container running a [HTTP server](https://github.com/hashicorp/http-echo), run a Java application that sends a request to that server and stop the container after the HTTP call succeeds. We also want to be able to change the port exposed by the container to access the Web server via command line argument or environment variable.

To achieve that goal we create custom Gradle tasks, based on those implemented by the plugin, and configure the Java plugin to run the custom task that starts the container before running compiled code and run the task that stops the container before finishing the script execution.

## How to run

| Description | Command |
| :--- | :--- |
| Run with default port | `./gradlew run` |
| Run with custom port (CLI) | `./gradlew run -Pport=<port>` |
| Run with custom port (Environment) | `PORT=<port> ./gradlew run` |

## Preview

### Default port

```
$ ./gradlew clean run

> Task :pullDockerImage
Pulling image 'hashicorp/http-echo:latest'.

> Task :createContainer
Created container with ID '7c0d24d2846ba1e2e6880c66e6c536afb4fac86f3c665c9dc70b435d10e86058'.

> Task :startContainer
Starting container with ID '7c0d24d2846ba1e2e6880c66e6c536afb4fac86f3c665c9dc70b435d10e86058'.

> Task :waitContainer
Starting liveness probe on container with ID '7c0d24d2846ba1e2e6880c66e6c536afb4fac86f3c665c9dc70b435d10e86058'.

> Task :run
Server URL: http://localhost:8080
Status code: 200
Response body: Hello world!

> Task :stopContainer
Stopping container with ID '7c0d24d2846ba1e2e6880c66e6c536afb4fac86f3c665c9dc70b435d10e86058'.

> Task :removeContainer
Removing container with ID '7c0d24d2846ba1e2e6880c66e6c536afb4fac86f3c665c9dc70b435d10e86058'.

BUILD SUCCESSFUL in 14s
9 actionable tasks: 9 executed
```

### Custom port (CLI)

```
$ ./gradlew clean run -Pport=8081

> Task :pullDockerImage
Pulling image 'hashicorp/http-echo:latest'.

> Task :createContainer
Created container with ID '383b6f7e4db96b507b5fe5d65403073486b0bc5e4603d699b73085deb81e6155'.

> Task :startContainer
Starting container with ID '383b6f7e4db96b507b5fe5d65403073486b0bc5e4603d699b73085deb81e6155'.

> Task :waitContainer
Starting liveness probe on container with ID '383b6f7e4db96b507b5fe5d65403073486b0bc5e4603d699b73085deb81e6155'.

> Task :run
Server URL: http://localhost:8081
Status code: 200
Response body: Hello world!

> Task :stopContainer
Stopping container with ID '383b6f7e4db96b507b5fe5d65403073486b0bc5e4603d699b73085deb81e6155'.

> Task :removeContainer
Removing container with ID '383b6f7e4db96b507b5fe5d65403073486b0bc5e4603d699b73085deb81e6155'.

BUILD SUCCESSFUL in 14s
9 actionable tasks: 9 executed
```

### Custom port (Environment)

```
$ PORT=8082 ./gradlew clean run

> Task :pullDockerImage
Pulling image 'hashicorp/http-echo:latest'.

> Task :createContainer
Created container with ID 'adff26441060dd0486d68518424b910bf1ae8a8c49a71ef843524720edda0277'.

> Task :startContainer
Starting container with ID 'adff26441060dd0486d68518424b910bf1ae8a8c49a71ef843524720edda0277'.

> Task :waitContainer
Starting liveness probe on container with ID 'adff26441060dd0486d68518424b910bf1ae8a8c49a71ef843524720edda0277'.

> Task :run
Server URL: http://localhost:8082
Status code: 200
Response body: Hello world!

> Task :stopContainer
Stopping container with ID 'adff26441060dd0486d68518424b910bf1ae8a8c49a71ef843524720edda0277'.

> Task :removeContainer
Removing container with ID 'adff26441060dd0486d68518424b910bf1ae8a8c49a71ef843524720edda0277'.

BUILD SUCCESSFUL in 16s
9 actionable tasks: 9 executed
```
