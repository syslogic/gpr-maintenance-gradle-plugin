### GitHub Package Registry Maintenance Gradle Plugin

![Social Media Preview](https://raw.githubusercontent.com/syslogic/gpr-maintenance-gradle-plugin/master/screenshots/repository.png)

Legal Disclaimer: This product is **NOT** officially endorsed or certified by GitHub.<br/>
The trademarks are being referenced for identification purposes only, in terms of a nominative fair use.

The official GitHub repositories can be found there: [@GitHub](https://github.com/orgs/GitHub/repositories).

 ---
### Features

- It automates the GitHub Packages API.
- It can list and delete Maven packages.

### Development

The plugin source code can be swiftly installed into any Gradle project with `git clone`:

````bash
git clone https://github.com/syslogic/gpr-maintenance-gradle-plugin.git ./buildSrc
````

### Package Installation

Plugin `io.syslogic.gpr.maintenance` depends on `maven-publish` (it will be applied).

The plugin can be set up in the `buildscript` block of the root project's `build.gradle`:
````groovy
buildscript {
    repositories {
        maven { url 'https://jitpack.io' }
    }
    dependencies {
        classpath "io.syslogic:gpr-maintenance-gradle-plugin:1.0.0"
    }
}
````

Or in version-catalog `gradle/libs.versions.toml`:
````toml
[versions]
gpr_maintenance_plugin = "1.0.0"

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
When running in GitHub workflow, one has to pass environmental variables, which are also picked up.  

````yaml
      - name: Publish :library
        env:
          GITHUB_ACTOR: ${{ GITHUB.REPOSITORY_OWNER }}
          GITHUB_TOKEN: ${{ SECRETS.GITHUB_TOKEN }}
        run: |
          chmod + ./gradlew
          ./gradlew --no-daemon :library:publish
          ls -la ./library/build/outputs/aar | grep aar
````
The `GprMaintenanceExtension` can be configured with the following properties:

- `tokenProperties`: The absolute path to the `token.properties` file.
- `deleteOnConflict`: Delete conflicting package version upon publish: true/false.
- `deleteLastVersion`: Delete package, when deleting the last version fails: true/false.
- `listPackagesAfterPublish`: List all Maven packages after publish: true/false.
- `logHttp`: HTTP logging: true/false.

````groovy
gpr {
    tokenProperties = rootProject.file("token.properties").absolutePath
    deleteOnConflict = true
    deleteLastVersion = true
    listPackagesAfterPublish = true
    logHttp = false
}
````

````kotlin
configure<GprMaintenanceExtension> {
    tokenProperties = rootProject.file("token.properties").absolutePath
    deleteOnConflict = true
    deleteLastVersion = true
    listPackagesAfterPublish = true
    logHttp = false
}
````

This configuration is optional, while providing the config file at the default location:

 - `buildSrc/token.properties` or the parent project directory `buildSrc/../token.properties`.

### Plugin Tasks

![Gradle Plugin Tasks](https://raw.githubusercontent.com/syslogic/gpr-maintenance-gradle-plugin/master/screenshots/screenshot_01.png)

### Example Log Output

    > Configure project :library
    | + 46168200 ~ 1.0.5 -> https://api.github.com/users/syslogic/packages/maven/io.syslogic.androidx-github/versions/46168200

    > Task :library:gprPackageDel_46168200
    > [GPR] package io.syslogic.androidx-github deleted altogether.

    > Task :library:gprPackageList
    Package {id=2607064, name="io.syslogic.agconnect-publishing-gradle-plugin", packageType="maven", visibility="public", versionCount=1, url="https://api.github.com/users/syslogic/packages/maven/io.syslogic.agconnect-publishing-gradle-plugin"}
    | + 46071972 ~ 1.4.2 -> https://api.github.com/users/syslogic/packages/maven/io.syslogic.agconnect-publishing-gradle-plugin/versions/46071972

### Support
- [Documentation](https://docs.github.com/en/rest/packages/packages)
- [Stack Overflow](https://stackoverflow.com/questions/tagged/github-package-registry)
- [Issue Tracker](https://github.com/syslogic/gpr-maintenance-gradle-plugin/issues)

### Status

[![Gradle CI](https://github.com/syslogic/gpr-maintenance-gradle-plugin/actions/workflows/gradle.yml/badge.svg)](https://github.com/syslogic/gpr-maintenance-gradle-plugin/actions/workflows/gradle.yml)

[![Release](https://jitpack.io/v/syslogic/gpr-maintenance-gradle-plugin.svg)](https://jitpack.io/#io.syslogic/gpr-maintenance-gradle-plugin)

[![MIT License](https://img.shields.io/github/license/syslogic/gpr-maintenance-gradle-plugin)](https://github.com/syslogic/gpr-maintenance-gradle-plugin/blob/master/LICENSE)<!-- @IGNORE PREVIOUS: link -->
