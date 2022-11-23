package com.xinto.taxi

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * A navigator composable responsible for displaying the current destination.
 *
 * @param navigator The [Navigator] class for retrieving the current destination.
 * Consider passing the navigator retrieved from [rememberNavigator] or [rememberBackstackNavigator].
 *
 * @param transitionSpec Animations for smoothly navigating between destinations.
 * Can be configured to achieve different transitions based on the current and the next destination.
 *
 * @param content Content to display inside the navigator.
 * The passed parameter is the [Destination] of type [T].
 *
 * @see AnimatedContent
 * @see SaveableStateHolder
 */
@Composable
public fun <T : Destination> Taxi(
    navigator: Navigator<T>,
    transitionSpec: AnimatedContentScope<T>.() -> ContentTransform,
    modifier: Modifier = Modifier,
    content: @Composable (T) -> Unit,
) {
    val viewModel: NavigatorViewModel = viewModel()
    val saveableStateHolder = rememberSaveableStateHolder()
    val transition = updateTransition(targetState = navigator.currentDestination, label = "")
    LaunchedEffect(transition.isRunning) {
        if (navigator.shouldRemoveStoreOwner()) {
            if (transition.isRunning) {
                viewModel.scheduleForRemoval(transition.currentState)
            } else {
                viewModel.removeScheduled()
            }
        }
    }
    transition.AnimatedContent(
        modifier = modifier,
        transitionSpec = transitionSpec,
        contentAlignment = Alignment.Center
    ) { currentDestination ->
        CompositionLocalProvider(
            LocalViewModelStoreOwner provides viewModel.getViewModelStoreForDestination(currentDestination)
        ) {
            saveableStateHolder.SaveableStateProvider(currentDestination) {
                content(currentDestination)
            }
        }
    }
}