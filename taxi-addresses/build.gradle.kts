plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "com.xinto.taxiaddresses"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33

        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs +
            "-Xexplicit-api=strict" +
            "-opt-in=androidx.compose.animation.ExperimentalAnimationApi"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.Compose.compilerVersion
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    api(project(":taxi"))
    implementation(Dependencies.Compose.foundation)
}