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
    implementation("com.github.X1nto:Taxi:1.2.0")
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
fun BackstackApp() {
    val navigator = rememberBackstackNavigator<AppDestination>(initial = AppDestination.Home)
    
    BackHandler {
        //Check if the navigator can pop
        if (!navigator.pop()) {
            finish() //Finish the activity if the backstack was empty
        }
    }
    
    Taxi(
        modifier = Modifier.filLMaxSize(),
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

@Composable
fun NavbarApp() {
    val navigator = rememberNavigator<AppDestination>(initial = AppDestination.Home)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(modifier = Modifier.fillMaxWidth()) {
                NavigationBarItem(
                    selected = navigator.currentDestination is AppDestination.Home,
                    onClick = { 
                        navigator.replace(AppDestination.Home)
                    },
                    label = {
                        Text("Home")
                    }
                )
                NavigationBarItem(
                    selected = navigator.currentDestination is AppDestination.Settings,
                    onClick = { 
                        navigator.replace(AppDestination.Settings)
                    },
                    label = {
                        Text("Settings")
                    }
                )
            }
        }
    ) { paddingValues ->
        Taxi(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            navigator = navigator,
            transitionSpec = { fadeIn() with fadeOut() }
        ) { destination ->
            when (destination) {
                is AppDestination.Home -> {
                    Home()
                }
                is AppDestination.Settings -> {
                    Settings()
                }
            }
        }
    }
}
```

## Usage with Addresses
Taxi addresses simplify your code by providing a neat DSL for registering new destinations.
```kt
AddressedTaxi(
    modifier = Modifier.fillMaxSize().padding(paddingValues),
    navigator = navigator,
    transitionSpec = { fadeIn() with fadeOut() }
) {
    address<AppDestination.Home> {
        Home()
    }
    address<AppDestination.Counter> {
        Counter(it.count)
    }
}
```

Check out the [sample app](/app) for better examples.

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