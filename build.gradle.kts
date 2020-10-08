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

val containerHostPort = properties.getOrDefault("port", 8080)

val pullDockerImage by tasks.registering(DockerPullImage::class) {
    image.set("hashicorp/http-echo:latest")
}

val createContainer by tasks.registering(DockerCreateContainer::class) {
    dependsOn(pullDockerImage)
    targetImageId(pullDockerImage.get().image)
    hostConfig.portBindings.add("$containerHostPort:5678")
    cmd.set(listOf("-text", "Hello world!"))
}

val startContainer by tasks.registering(DockerStartContainer::class) {
    dependsOn(createContainer)
    targetContainerId(createContainer.get().containerId)
}

val waitContainer by tasks.registering(DockerLivenessContainer::class) {
    dependsOn(startContainer)
    targetContainerId(createContainer.get().containerId)
    livenessProbe("Server is listening")
}

val stopContainer by tasks.registering(DockerStopContainer::class) {
    dependsOn(waitContainer)
    targetContainerId(createContainer.get().containerId)
}

val removeContainer by tasks.registering(DockerRemoveContainer::class) {
    dependsOn(stopContainer)
    targetContainerId(createContainer.get().containerId)
}

tasks.withType<JavaExec>().configureEach {
    if ("PORT" !in environment.keys) {
        dependsOn(waitContainer)
        finalizedBy(removeContainer)
        environment("PORT", "$containerHostPort")
    }
}
