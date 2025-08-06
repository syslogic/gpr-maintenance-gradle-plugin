// :buildSrc
plugins {
    alias(buildSrc.plugins.maven.publish)
    alias(buildSrc.plugins.gradle.plugin)
    alias(buildSrc.plugins.gradle.publish)
    alias(buildSrc.plugins.jetbrains.dokka)
}

// Loading common strings from version-catalog.
project.ext.set("github_handle",  buildSrc.versions.github.handle.get())
project.ext.set("artifact_id",    buildSrc.versions.plugin.artifact.get())
project.ext.set("group_id",       buildSrc.versions.plugin.group.get())
project.ext.set("plugin_id",      buildSrc.versions.plugin.id.get())
project.ext.set("plugin_class",   buildSrc.versions.plugin.cls.get())
project.ext.set("plugin_dev",     buildSrc.versions.plugin.dev.get())
project.ext.set("plugin_name",    buildSrc.versions.plugin.name.get())
project.ext.set("plugin_desc",    buildSrc.versions.plugin.desc.get())
project.ext.set("plugin_version", buildSrc.versions.plugin.version.get())

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
            id = "${project.ext.get("plugin_id")}"
            implementationClass = "${project.ext.get("plugin_class")}"
            displayName = "${project.ext.get("plugin_name")}"
            description = "${project.ext.get("plugin_desc")}"
        }
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

tasks.withType<Jar>().configureEach {
    archiveBaseName.set("${project.ext.get("artifact_id")}")
    archiveVersion.set("${project.ext.get("plugin_version")}")
}

// Gradle 9.0 deprecation fix
val implCls: Configuration by configurations.creating {
    extendsFrom(configurations.getByName("implementation"))
    isCanBeResolved = true
}

val javadocs by tasks.registering(Javadoc::class) {
    title = "${project.ext.get("plugin_name")} ${project.ext.get("plugin_version")} API"
    classpath += implCls.asFileTree.filter {it.extension == "jar"}
    destinationDir = rootProject.file("build/javadoc")
    source = sourceSets.main.get().allJava
    // options.links = "https://docs.oracle.com/en/java/javase/17/docs/api/"
    // options.linkSource = true
    // options.author = true
    isFailOnError = false
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
    from(rootProject.file("build/javadoc"))
    dependsOn(javadocs)
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().java.srcDirs)
}

group = "${project.ext.get("group_id")}"
version = "${project.ext.get("plugin_version")}"

artifacts {
    archives(javadocJar)
    archives(sourcesJar)
}

configure<PublishingExtension> {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/${project.ext.get("github_handle")}/${project.ext.get("artifact_id")}")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }

    publications {
        register<MavenPublication>("Maven") {
            from(components.getByName("java"))
            groupId = "${project.ext.get("group_id")}"
            artifactId = "${project.ext.get("artifact_id")}"
            version = "${project.ext.get("plugin_version")}"
            pom {
                name = "${project.ext.get("plugin_name")}"
                description = "${project.ext.get("plugin_desc")}"
                url = "https://github.com/${project.ext.get("github_handle")}/${project.ext.get("artifact_id")}"
                developers {
                    developer {
                        id = "${project.ext.get("github_handle")}"
                        email = "${project.ext.get("github_handle")}@users.noreply.github.com"
                        name = "${project.ext.get("plugin_dev")}"
                    }
                }
                scm {
                    connection = "scm:git:git://github.com/${project.ext.get("github_handle")}/${project.ext.get("artifact_id")}.git"
                    developerConnection = "scm:git:ssh://github.com/${project.ext.get("github_handle")}/${project.ext.get("artifact_id")}.git"
                    url = "https://github.com/${project.ext.get("github_handle")}/${project.ext.get("artifact_id")}/"
                }
                licenses {
                    license {
                        name = "MIT License"
                        url = "http://www.opensource.org/licenses/mit-license.php"
                    }
                }
            }
        }
    }
}
