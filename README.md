### GitHub Package Registry Maintenance Gradle Plugin

![Social Media Preview](https://raw.githubusercontent.com/syslogic/gpr-maintenance-gradle-plugin/master/screenshots/repository.png)

Legal Disclaimer: This product is **NOT** officially endorsed or certified by GitHub.<br/>
The trademarks are being referenced for identification purposes only, in terms of a nominative fair use.

The official GitHub repositories can be found there: [@GitHub](https://github.com/orgs/GitHub/repositories).

 ---

This Gradle plugin was inspired by this [discussion](https://github.com/orgs/community/discussions/149386#discussioncomment-14017558)
and it may count as another GitHub Developer Program contribution, as it is built for and with GitHub API.
It is also being used in this repository's workflow: [`androidx-github`](https://github.com/syslogic/androidx-github).
I've recycled some of the models from the Android Java implementation, as they're merely the same.

### Features

- It interacts with the GitHub Packages API.
- It can list and delete versions and packages.

### Development

The plugin source code can be swiftly installed into any Gradle project with `git clone`:

````bash
git clone https://github.com/syslogic/gpr-maintenance-gradle-plugin.git ./buildSrc
````

### Installation

Plugin `io.syslogic.gpr.maintenance` depends on `maven-publish` (it will be applied).

The plugin can be set up in the `buildscript` block of the root project's `build.gradle`:
````groovy
buildscript {
    repositories {
        maven {
            name = "JitPack"
            url = uri("https://jitpack.io")
        }
    }
    dependencies {
        classpath("io.syslogic:gpr-maintenance-gradle-plugin:<PLUGIN_VERSION>")
    }
}
````

Or in version-catalog `gradle/libs.versions.toml`:
````toml
[versions]
gpr_maintenance_plugin = "<PLUGIN_VERSION>"

[plugins]
gpr_maintenance = { id = "io.syslogic.gpr.maintenance", version.ref = "gpr_maintenance_plugin" }
````

To be applied in a library module's `build.gradle`:
````groovy
plugins {
    // id("maven-publish")    
    // id("io.syslogic.gpr.maintenance")
    alias(libs.plugins.gpr.maintenance)
}
````

### Configuration

The credentials are either being picked up from file `token.properties` (format: `username token`).
When running in GitHub workflow, one has to declare these environmental variables, which are being picked up instead.  

````yaml
- name: 🐘 Publish AAR to GPR
  env:
    GITHUB_ACTOR: ${{ GITHUB.REPOSITORY_OWNER }}
    GITHUB_TOKEN: ${{ SECRETS.GITHUB_TOKEN }}
  run: |
    chmod + ./gradlew
    ./gradlew --max-workers=2 :library:publishLibraryPublicationToGitHubPackagesRepository
    ls -la ./library/build/outputs/aar | grep aar
````
The [`GprMaintenanceExtension`](https://github.com/syslogic/gpr-maintenance-gradle-plugin/blob/master/src/main/java/io/syslogic/gpr/GprMaintenanceExtension.java) can be configured with the following properties:

| Property                    | Description                                                                                                                                      | Type      | Default       |
|-----------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------|-----------|---------------|
| `packageType`               | The package-type to query; any of: `npm`, `maven`, `rubygems`, `docker`, `nuget`, `container`.                                                   | `String`  | `maven`       |
| `groupId`                   | The group-name of the package to look up (project value override).                                                                               | `String`  | project value |
| `packageName`               | The package-name of the package to look up (project value override).                                                                             | `String`  | project value |
| `tokenProperties`           | The absolute path to file `token.properties`, containing following format: `<username> <token>`. Valid string-separators are: ` `, `\|` and `/`. | `String`  | `null`        |
| `deleteOnConflict`          | Delete conflicting package version upon publish.                                                                                                 | `Boolean` | `false`       |
| `deleteLastVersion`         | Delete the whole package, when attempting to delete the last version.                                                                            | `Boolean` | `false`       |
| `listPackagesAfterPublish`  | List all packages after publish.                                                                                                                 | `Boolean` | `false`       |
| `logHttp`                   | HTTP logging                                                                                                                                     | `Boolean` | `false`       |
| `pageSize`                  | Paginated package Listing page-size (max value `100`)                                                                                            | `Integer` | `30`          |

In Groovy `build.gradle`, the extension is being called `gpr`.
````groovy
gpr {
    tokenProperties = rootProject.file("token.properties").absolutePath
    deleteOnConflict = true
    deleteLastVersion = true
    listPackagesAfterPublish = true
    logHttp = false
}
````

In Kotlin `build.gradle.kts` one has to use `configure<?>()`.
````kotlin
configure<GprMaintenanceExtension> { /* ... */ }
````

Configuration `tokenProperties` is optional, while providing the config file at the default location:

 - `buildSrc/token.properties` or the parent project directory `buildSrc/../token.properties`.

### Plugin Tasks

![Gradle Plugin Tasks](https://raw.githubusercontent.com/syslogic/gpr-maintenance-gradle-plugin/master/screenshots/screenshot_01.png)

### Example Log Output

    > Configure project :library
    | + 46168200 ~ 1.0.5 -> https://api.github.com/users/syslogic/packages/maven/io.syslogic.androidx-github/versions/46168200

    > Task :library:gprPackageDel_46168200
    > [GPR] package io.syslogic.androidx-github deleted.

    > Task :library:gprPackageList
    Package {id=2607064, name="io.syslogic.agconnect-publishing-gradle-plugin", packageType="maven", visibility="public", versionCount=1, url="https://api.github.com/users/syslogic/packages/maven/io.syslogic.agconnect-publishing-gradle-plugin"}
    | + 46071972 ~ 1.4.2 -> https://api.github.com/users/syslogic/packages/maven/io.syslogic.agconnect-publishing-gradle-plugin/versions/46071972

### Support
- [Documentation](https://docs.github.com/en/rest/packages/packages)
- [Stack Overflow](https://stackoverflow.com/questions/tagged/github-package-registry)
- [Issue Tracker](https://github.com/syslogic/gpr-maintenance-gradle-plugin/issues)

### Status

[![Gradle CI](https://img.shields.io/github/actions/workflow/status/syslogic/gpr-maintenance-gradle-plugin/gradle.yml?style=for-the-badge)](https://github.com/syslogic/gpr-maintenance-gradle-plugin/actions)

[![JitPack](https://img.shields.io/jitpack/version/io.syslogic/gpr-maintenance-gradle-plugin?style=for-the-badge)](https://jitpack.io/#io.syslogic/gpr-maintenance-gradle-plugin)

[![MIT License](https://img.shields.io/github/license/syslogic/gpr-maintenance-gradle-plugin?style=for-the-badge)](https://github.com/syslogic/gpr-maintenance-gradle-plugin/blob/master/LICENSE)<!-- @IGNORE PREVIOUS: link -->
