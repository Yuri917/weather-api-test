plugins {
    id("java")
    id("io.qameta.allure") version "4.1.0"
}

group = "com.yuri.api.test"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.14.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation("io.rest-assured:rest-assured:5.5.6")

    testImplementation("org.wiremock:wiremock:3.13.2")

    testImplementation("io.qameta.allure:allure-junit5:2.27.0")
    testImplementation("io.qameta.allure:allure-rest-assured:2.27.0")

    testImplementation("org.slf4j:slf4j-simple:2.0.18")

    testImplementation("com.fasterxml.jackson.core:jackson-databind:2.22.1")

    testImplementation("org.assertj:assertj-core:3.27.7")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
    maxHeapSize = "2g"
    testLogging {
        showStandardStreams = true // сразу будем видеть все наши логи расхождений в консоли Gradle
    }
}