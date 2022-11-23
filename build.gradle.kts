plugins {
    id("com.android.application") version "7.3.1" apply false
    id("com.android.library") version "7.3.1" apply false
    kotlin("android") version "1.7.20" apply false
    id("maven-publish")
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}

allprojects {
    group = "com.github.xinto"
    version = "1.2.0"

    afterEvaluate {
        if (plugins.hasPlugin("com.android.library")) {
            apply(plugin = "maven-publish")
            afterEvaluate {
                publishing {
                    publications {
                        create<MavenPublication>("release") {
                            from(components["release"])
                            groupId = project.group as String
                            artifactId = name
                            version = project.version as String
                        }
                    }
                }
            }
        }
    }
}