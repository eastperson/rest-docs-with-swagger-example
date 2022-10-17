import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.4"
    id("io.spring.dependency-management") version "1.0.14.RELEASE"
    id("org.asciidoctor.convert") version "2.4.0"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
}

group = "com.ep"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

extra["snippetsDir"] = file("build/generated-snippets")

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")

    implementation("org.springdoc:springdoc-openapi-ui:1.6.11")
    implementation("org.springdoc:springdoc-openapi-webmvc-core:1.6.11")
    testImplementation("com.epages:restdocs-api-spec:0.16.2")
    testImplementation("com.epages:restdocs-api-spec-mockmvc:0.16.2")
}

val snippetsDir by extra { file("build/generated-snippets") }

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    withType<Test> {
        useJUnitPlatform()
        outputs.dir(snippetsDir)
    }

    test {
        useJUnitPlatform()
        outputs.dir(snippetsDir)
    }

    asciidoctor {
        inputs.dir(snippetsDir)
        dependsOn(test)

        doFirst {
            delete {
                file("src/main/resources/static/docs")
            }
        }
    }

    val copyDocument = register<Copy>("copyDocument") {
        dependsOn(asciidoctor)
        from("${rootProject.rootDir}/build/asciidoc/html5")
        into(File("${rootProject.rootDir}/src/main/resources/static/docs"))
    }

    build {
        dependsOn(copyDocument)
    }

    bootJar {
        dependsOn(asciidoctor)
        dependsOn(copyDocument)
    }
}