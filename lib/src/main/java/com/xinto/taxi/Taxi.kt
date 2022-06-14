package com.xinto.taxi

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ContentTransform
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * A navigator composable responsible for displaying the current destination.
 *
 * @param navigator The [Navigator] class for retrieving the current destination.
 * Consider passing the navigator retrieved from [rememberNavigator] or [rememberBackstackNavigator].
 *
 * @param transitionSpec Animations for smoothly navigating between destinations.
 * Can be configured to achieve different transitions based on the current and the next destination.
 *
 * @param onBackPress An action to perform when the device back button is pressed.
 * Uses [BackHandler] under-the-hood.
 * Empty by default.
 *
 * @param backEnabled Whether to enable the back button detection. False by default.
 */
@Composable
public fun <T : Destination> Taxi(
    navigator: Navigator<T>,
    transitionSpec: AnimatedContentScope<T>.() -> ContentTransform,
    modifier: Modifier = Modifier,
    onBackPress: (T) -> Unit = {},
    backEnabled: Boolean = false,
    content: @Composable (T) -> Unit,
) {
    val saveableStateHolder = rememberSaveableStateHolder()

    BackHandler(enabled = backEnabled) {
        onBackPress(navigator.currentDestination)
    }

    AnimatedContent(
        modifier = modifier,
        targetState = navigator.currentDestination,
        transitionSpec = transitionSpec,
        contentAlignment = Alignment.Center
    ) { currentDestination ->
        saveableStateHolder.SaveableStateProvider(currentDestination) {
            content(currentDestination)
        }
    }
}