// :buildSrc
buildscript {
    repositories {
        mavenCentral()
        maven {url = uri("https://jitpack.io") }
    }
    dependencies {
        // Add the plugin to classpath when not building in `buildSrc`.
        if (! file("../buildSrc").exists()) {
            classpath(buildSrc.gpr.maintenance)
        }
    }
}

plugins {
    alias(buildSrc.plugins.gradle.plugin)
    alias(buildSrc.plugins.maven.publish)
}

// Apply plugin to classpath when not running  in `buildSrc`.
if (! file("../buildSrc").exists()) {
    pluginManager.apply("io.syslogic.gpr.maintenance")
}

val pluginId: String by extra(buildSrc.versions.plugin.id.get())
val pluginCls: String by extra(buildSrc.versions.plugin.cls.get())
val pluginGroup: String by extra(buildSrc.versions.plugin.group.get())
val pluginVersion: String by extra(buildSrc.versions.plugin.version.get())
val pluginName: String by extra(buildSrc.versions.plugin.name.get())
val pluginDesc: String by extra(buildSrc.versions.plugin.desc.get())
val pluginIdentifier: String by extra(buildSrc.versions.plugin.identifier.get())
val githubHandle: String by extra(buildSrc.versions.github.handle.get())
val githubEmail: String by extra(buildSrc.versions.github.email.get())
val githubDev: String by extra(buildSrc.versions.github.dev.get())

group = pluginGroup
version = pluginVersion

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

dependencies {
    api(gradleApi())
    implementation(buildSrc.bundles.http.client)
    implementation(buildSrc.annotations)
    implementation(buildSrc.gson)

    testImplementation(platform(buildSrc.junit.bom))
    testImplementation(buildSrc.bundles.junit.jupiter)
    testRuntimeOnly(buildSrc.bundles.junit.runtime)

    testImplementation(gradleTestKit())
    testImplementation(buildSrc.annotations)
    testImplementation(project)
}

gradlePlugin {
    plugins {
        create("GprMaintenancePlugin") {
            id = pluginId
            implementationClass = pluginCls
            displayName = pluginName
            description = pluginDesc
        }
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

tasks.withType<AbstractTestTask>().configureEach {
    failOnNoDiscoveredTests = false
}

tasks.withType<Jar>().configureEach {
    archiveBaseName.set(pluginIdentifier)
    archiveVersion.set(pluginVersion)
}

// Gradle 9.0 deprecation fix
val implCls: Configuration by configurations.creating {
    extendsFrom(configurations.getByName("implementation"))
    isCanBeResolved = true
}

val javadocs by tasks.registering(Javadoc::class) {
    title = "$pluginName $version API"
    classpath += implCls.asFileTree.filter {it.extension == "jar"}
    destinationDir = rootProject.file("build/javadoc")
    source = sourceSets.main.get().allJava
    // options.links = "https://docs.oracle.com/en/java/javase/17/docs/api/"
    // options.linkSource = true
    // options.author = true
    isFailOnError = false
}

val javadocJar by tasks.registering(Jar::class) {
    from(rootProject.file("build/javadoc"))
    archiveClassifier.set("javadoc")
    dependsOn(javadocs)
}

val sourcesJar by tasks.registering(Jar::class) {
    from(sourceSets.main.get().java.srcDirs)
    archiveClassifier.set("sources")
}

artifacts {
    archives(javadocJar)
    archives(sourcesJar)
}

afterEvaluate {
    configure<PublishingExtension> {
        repositories {
            if (System.getenv("GITHUB_ACTOR") != null && System.getenv("GITHUB_TOKEN") != null) {
                maven {
                    name = "GitHubPackages"
                    url = uri("https://maven.pkg.github.com/${githubHandle}/${pluginIdentifier}")
                    credentials {
                        username = System.getenv("GITHUB_ACTOR")
                        password = System.getenv("GITHUB_TOKEN")
                    }
                }
            }
        }
        publications {
            create<MavenPublication>("Plugin") {
                from(components["java"])
                groupId = pluginGroup
                artifactId = pluginIdentifier
                version = pluginVersion
                pom {
                    name = pluginName
                    description = pluginDesc
                    url = "https://github.com/${githubHandle}/${pluginIdentifier}"
                    licenses {
                        license {
                            name = "MIT License"
                            url = "http://www.opensource.org/licenses/mit-license.php"
                        }
                    }
                    scm {
                        connection = "scm:git:git://github.com/${githubHandle}/${pluginIdentifier}.git"
                        developerConnection = "scm:git:ssh://github.com/${githubHandle}/${pluginIdentifier}.git"
                        url = "https://github.com/${githubHandle}/${pluginIdentifier}/"
                    }
                    developers {
                        developer {
                            id = githubHandle
                            email = githubEmail
                            name = githubDev
                        }
                    }
                }
            }
        }
    }
}
