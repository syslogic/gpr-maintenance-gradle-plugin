// settings.gradle.kts

pluginManagement {
    repositories {
        gradlePluginPortal {
            content {
                includeGroupByRegex("com.gradle.*")
                excludeGroupByRegex("com.(fasterxml|vanniktech|squareup).*")
                excludeGroupByRegex("org.(apache|eclipse|codehaus|jetbrains|junit).*")
                excludeGroup("org.junit")
            }
        }
        mavenCentral {
            content {
                excludeGroupByRegex("com.gradle.*")
                includeGroupByRegex("org.(apache|eclipse|codehaus|jetbrains|junit).*")
                includeGroupByRegex("com.(fasterxml|vanniktech|squareup).*")
                includeGroupByRegex("jakarta.*")
                includeGroup("org.junit")
            }
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
        google()
        mavenCentral()
        mavenLocal()
    }
}

rootProject.name = "gpr-maintenance-gradle-plugin"
