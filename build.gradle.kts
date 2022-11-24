plugins {
    id("com.android.application") version "7.3.1" apply false
    id("com.android.library") version "7.3.1" apply false
    kotlin("android") version "1.7.20" apply false
    id("maven-publish")
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}

subprojects {
    afterEvaluate {
        if (plugins.hasPlugin("com.android.library")) {
            apply(plugin = "maven-publish")
            afterEvaluate {
                publishing {
                    publications {
                        create<MavenPublication>("maven") {
                            from(components["release"])
                            groupId = "com.github.x1nto"
                            artifactId = name
                        }
                    }
                }
            }
        }
    }
}