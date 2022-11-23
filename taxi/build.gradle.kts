plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "com.xinto.taxi"
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
        kotlinCompilerExtensionVersion = "1.3.2"
    }

    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs +
                "-Xexplicit-api=strict" +
                "-opt-in=androidx.compose.animation.ExperimentalAnimationApi"
    }
}

dependencies {
    implementation(Dependencies.Compose.foundation)
    implementation(Dependencies.Lifecycle.viewmodelKtx)
    implementation(Dependencies.Lifecycle.viewmodelCompose)
}