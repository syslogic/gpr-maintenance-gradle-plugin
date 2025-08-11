// settings.gradle.kts
pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven {
            name = "JitPack"
            url = uri("https://jitpack.io")
        }
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    versionCatalogs {
        create("buildSrc") {
            from(files("gradle/libs.versions.toml"))
        }
    }
    repositories {
        mavenCentral()
        mavenLocal()
    }
}

// The root project name only should be set when building in `buildSrc` ...
// else this will lead to a doubled-up package name, when publishing it.
if (file("../buildSrc").exists()) {
    rootProject.name = "gpr-maintenance-gradle-plugin"
}
