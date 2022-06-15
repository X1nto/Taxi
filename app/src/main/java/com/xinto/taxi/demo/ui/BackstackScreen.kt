package com.xinto.taxi.demo.ui

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.xinto.taxi.Taxi
import com.xinto.taxi.rememberBackstackNavigator

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun BackstackScreen() {
    val navigator = rememberBackstackNavigator(initial = BackstackShowcase(0))
    val currentIndex = navigator.currentDestination.index
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navigator.push(BackstackShowcase(currentIndex + 1))
            }) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add a screen"
                )
            }
        },
        topBar = {
            SmallTopAppBar(
                title = {
                    Text("Screen $currentIndex")
                },
                navigationIcon = {
                    if (currentIndex > 0) {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = "back"
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Taxi(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            navigator = navigator,
            transitionSpec = {
                if (targetState.index > initialState.index) {
                    slideIntoContainer(
                        towards = AnimatedContentScope.SlideDirection.Start,
                        initialOffset = { it / 3 }
                    ) + fadeIn() with slideOutOfContainer(
                        towards = AnimatedContentScope.SlideDirection.Start,
                        targetOffset = { it / 3 }
                    )
                } else {
                    slideIntoContainer(
                        towards = AnimatedContentScope.SlideDirection.End,
                        initialOffset = { it / 3 }
                    ) + fadeIn() with slideOutOfContainer(
                        towards = AnimatedContentScope.SlideDirection.End,
                        targetOffset = { it / 3 }
                    )
                }
            }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Screen ${it.index}")
            }
        }
    }
}