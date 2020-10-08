import com.bmuschko.gradle.docker.tasks.container.DockerCreateContainer
import com.bmuschko.gradle.docker.tasks.container.DockerStartContainer
import com.bmuschko.gradle.docker.tasks.container.DockerStopContainer
import com.bmuschko.gradle.docker.tasks.container.DockerRemoveContainer
import com.bmuschko.gradle.docker.tasks.container.extras.DockerLivenessContainer
import com.bmuschko.gradle.docker.tasks.image.DockerPullImage

plugins {
    java
    application
    id("com.bmuschko.docker-remote-api") version "6.6.1"
}

configure<ApplicationPluginConvention> {
    mainClassName = "com.example.Main"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
}

val containerHostPort = properties.getOrDefault("server.port", 8080)

val pullDockerImage by tasks.creating(DockerPullImage::class) {
    image.set("hashicorp/http-echo:latest")
}

val createContainer by tasks.creating(DockerCreateContainer::class) {
    dependsOn(pullDockerImage)
    targetImageId(pullDockerImage.image.get())
    hostConfig.portBindings.add("$containerHostPort:5678")
    cmd.set(listOf("-text", "Hello world!"))
}

val startContainer by tasks.creating(DockerStartContainer::class) {
    dependsOn(createContainer)
    targetContainerId(createContainer.containerId)
}

val waitContainer by tasks.creating(DockerLivenessContainer::class.java) {
    dependsOn(startContainer)
    targetContainerId(createContainer.containerId)
    livenessProbe("Server is listening")
}

val stopContainer by tasks.creating(DockerStopContainer::class) {
    dependsOn(waitContainer)
    targetContainerId(createContainer.containerId)
}

val removeContainer by tasks.creating(DockerRemoveContainer::class) {
    dependsOn(stopContainer)
    targetContainerId(createContainer.containerId)
}

tasks.withType<JavaExec> {
    dependsOn(waitContainer)
    finalizedBy(removeContainer)

    environment.putIfAbsent("SERVER_URL", "http://localhost:$containerHostPort")
}
