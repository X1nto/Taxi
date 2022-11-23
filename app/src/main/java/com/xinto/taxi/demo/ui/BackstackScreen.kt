package com.xinto.taxi.demo.ui

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.xinto.taxi.Taxi
import com.xinto.taxi.demo.ui.viewmodel.BackstackViewModel
import com.xinto.taxi.rememberBackstackNavigator

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun BackstackScreen() {
    var currentIndex by rememberSaveable { mutableStateOf(0) }
    val navigator =
        rememberBackstackNavigator<BackstackDestination>(initial = BackstackDestination.Count(0))
    Scaffold(
        floatingActionButton = {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                FloatingActionButton(onClick = {
                    navigator.replace(BackstackDestination.Replaced)
                }) {
                    Icon(
                        imageVector = Icons.Rounded.Refresh,
                        contentDescription = "Replace latest screen"
                    )
                }
                FloatingActionButton(onClick = {
                    val newIndex = currentIndex + 1
                    currentIndex++
                    navigator.push(BackstackDestination.Count(newIndex))
                }) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "Add a screen"
                    )
                }
            }

        },
        topBar = {
            SmallTopAppBar(
                title = {
                    when (navigator.currentDestination) {
                        is BackstackDestination.Count -> {
                            Text("Screen $currentIndex")
                        }
                        is BackstackDestination.Replaced -> {
                            Text("Replaced screen")
                        }
                    }
                },
                navigationIcon = {
                    if (currentIndex > 0) {
                        IconButton(onClick = {
                            if (navigator.currentDestination is BackstackDestination.Count) {
                                currentIndex--
                            }
                            navigator.pop()
                        }) {
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
                val targetCountState = targetState as? BackstackDestination.Count
                val initialCountState = initialState as? BackstackDestination.Count
                if (targetCountState != null && initialCountState != null) {
                    if (targetCountState.index > initialCountState.index) {
                        slideIntoContainer(
                            towards = AnimatedContentScope.SlideDirection.Start,
                        ) with slideOutOfContainer(
                            towards = AnimatedContentScope.SlideDirection.Start,
                            targetOffset = { it / 3 }
                        ) + fadeOut()
                    } else {
                        slideIntoContainer(
                            towards = AnimatedContentScope.SlideDirection.End,
                        ) with slideOutOfContainer(
                            towards = AnimatedContentScope.SlideDirection.End,
                            targetOffset = { it / 3 }
                        ) + fadeOut()
                    }
                } else {
                    fadeIn() + scaleIn() with fadeOut() + scaleOut()
                }
            }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                when (it) {
                    is BackstackDestination.Count -> {
                        val viewModel: BackstackViewModel = viewModel(factory = viewModelFactory {
                            initializer { BackstackViewModel(it.index) }
                        })
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = "Screen ${it.index}")
                            Text(text = "hash: ${viewModel.hashedId}")
                        }
                    }
                    is BackstackDestination.Replaced -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Hello this is a replaced screen")
                        }
                    }
                }
            }
        }
    }
}