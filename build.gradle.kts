plugins {
  `maven-publish`
  `kotlin-dsl`
  id("net.kyori.indra.license-header") version "2.0.2"
}

group = "xyz.jpenilla"
version = "1.0.0-SNAPSHOT"

repositories {
  mavenCentral()
  gradlePluginPortal()
}

dependencies {
  implementation("org.spongepowered", "configurate-hocon", "4.1.1")
  implementation("org.spongepowered", "configurate-extra-kotlin", "4.1.1")
  implementation("org.jetbrains.kotlinx", "kotlinx.dom", "0.0.10")
  implementation("gradle.plugin.com.github.jengelman.gradle.plugins", "shadow", "7.0.0")
}

java {
  sourceCompatibility = JavaVersion.toVersion(8)
  targetCompatibility = JavaVersion.toVersion(8)
  withSourcesJar()
}

kotlin {
  explicitApi()
}

tasks {
  compileKotlin {
    kotlinOptions.apiVersion = "1.4"
    kotlinOptions.jvmTarget = "1.8"
  }
}

gradlePlugin {
  plugins {
    create("Toothpick") {
      id = "xyz.jpenilla.toothpick"
      implementationClass = "xyz.jpenilla.toothpick.Toothpick"
    }
  }
}

extensions.getByType<PublishingExtension>().publications.withType<MavenPublication>().configureEach {
  pom {
    name.set(project.name)
    description.set(project.description)
    url.set("https://github.com/jpenilla/Toothpick")

    developers {
      developer {
        id.set("jmp")
        timezone.set("America/Los Angeles")
      }
    }

    licenses {
      license {
        name.set("MIT")
        url.set("https://github.com/jpenilla/Toothpick/raw/master/license.txt")
        distribution.set("repo")
      }
    }
  }
}

publishing.repositories.maven {
  url = if (project.version.toString().endsWith("-SNAPSHOT")) {
    uri("https://repo.jpenilla.xyz/snapshots")
  } else {
    uri("https://repo.jpenilla.xyz/releases")
  }
  credentials(PasswordCredentials::class)
}
