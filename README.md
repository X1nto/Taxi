# Taxi
A small navigation library for Jetpack Compose with state saving, backstack and animations support.

# Quick Setup
## Installation
Add JitPack repository to root build.gradle
```kotlin
allprojects {
    repositories {
        ...
        maven(url = "https://jitpack.io")
    }
}
```
Add Taxi dependency to app build.gradle
```kotlin
dependencies {
    implementation("com.github.X1nto:Taxi:1.0.0")
}
```

## Basic Usage
```kotlin
sealed interface AppDestination : Destination {
    
    @Parcelize
    object Home : AppDestination
    
    @Parcelize
    object Settings : AppDestination
    
}

@Composable
fun App() {
    val navigator = rememberBackstackNavigator<AppDestination>(initial = AppDestination.Home)
    
    BackHandler {
        //Check if the navigator can pop
        if (!navigator.pop()) {
            finish() //Finish the activity if the backstack was empty
        }
    }
    
    Taxi(
        navigator = navigator,
        transitionSpec = { fadeIn() with fadeOut() }
    ) { destination ->
        when (destination) {
            is AppDestination.Home -> {
                Home(onSettingsClick = {
                    navigator.push(AppDestination.Settings)
                })
            }
            is AppDestination.Settings -> {
                Settings(onBackClick = {
                    navigator.pop()
                })
            }
        }
    }
}
```

See the [sample app](/app) for better examples.

License
-------
```
Copyright (C) 2022 X1nto.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```