plugins {
    java
    application
}

repositories {
    jcenter()
}

dependencies {
    testImplementation("org.junit.jupiter", "junit-jupiter", "5.7.0")
}

configure<ApplicationPluginConvention> {
    mainClassName = "com.example.Main"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
}

tasks.withType<Test> {
    useJUnitPlatform()
    environment("SERVER_URL", "http://localhost:5678")
}

tasks.withType<JavaExec> {
    environment("SERVER_URL", "http://localhost:5678")
}