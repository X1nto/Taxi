plugins {
    id("com.android.library")
    kotlin("android")
    `maven-publish`
}

group = "com.github.xinto"
version = "1.1.0"

android {
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }

    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs +
                "-Xexplicit-api=strict" +
                "-opt-in=androidx.compose.animation.ExperimentalAnimationApi"
    }
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components.getByName("release"))
                groupId = project.group as String
                artifactId = "taxi"
                version = project.version as String
            }
        }
    }
}

dependencies {
    implementation("androidx.compose.foundation:foundation:1.2.1")
}