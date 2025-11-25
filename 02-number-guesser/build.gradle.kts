plugins {
    id("java")
    application
}

group = "org.java-tests"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

// Default run task - runs simple version
application {
    mainClass.set("simple.Main")
}

// Task to run simple version
tasks.register<JavaExec>("runSimple") {
    group = "application"
    description = "Run the simple version (all logic in Main)"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("simple.Main")
    standardInput = System.`in`
}

// Task to run refactored version
tasks.register<JavaExec>("runRefactored") {
    group = "application"
    description = "Run the refactored version (with GuessChecker and GuessResult classes)"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("refactored.Main")
    standardInput = System.`in`
}

