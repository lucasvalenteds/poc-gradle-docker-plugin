plugins {
    java
}

repositories {
    jcenter()
}

dependencies {
    testImplementation("org.junit.jupiter", "junit-jupiter", "5.7.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
