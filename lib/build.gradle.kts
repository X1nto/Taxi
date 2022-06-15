plugins {
    id("com.android.library")
    kotlin("android")
    `maven-publish`
}

val composeVersion = "1.2.0-beta03"

group = "com.github.xinto"
version = "1.0.0"

android {
    compileSdk = 32

    defaultConfig {
        minSdk = 21
        targetSdk = 32
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
        kotlinCompilerExtensionVersion = composeVersion
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
    implementation("androidx.compose.foundation:foundation:$composeVersion")
}