object Dependencies {

    object Core {
        const val coreKtx = "androidx.core:core-ktx:1.9.0"
    }

    object Activity {
        const val activityCompose = "androidx.activity:activity-compose:1.6.1"
    }

    object Compose {
        private const val version = "1.3.1"
        const val compilerVersion = "1.3.2"

        const val foundation = "androidx.compose.foundation:foundation:$version"
        const val material3 = "androidx.compose.material3:material3:1.0.1"
    }

    object Lifecycle {
        private const val version = "2.5.1"

        const val viewmodelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        const val viewmodelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$version"
    }

}