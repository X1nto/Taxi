package com.xinto.taxi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList

/**
 * Create and remember [RegularNavigator] based on the provided [initial] [Destination]
 * to control the current destination inside the composable scope.
 *
 * Note: regular navigator does not store previous destinations, making it useless
 * for stack-based navigation.
 */
@Composable
public inline fun <reified T : Destination> rememberNavigator(
    initial: T
): RegularNavigator<T> {
    return remember { RegularNavigator(initial) }
}

/**
 * Create and remember [BackstackNavigator] based on the provided [initial] [Destination]
 * to control the current destination inside the composable scope.
 *
 * Note: backstack navigator allows you to push new and pop to the previous destinations by
 * saving all destinations in a [SnapshotStateList].
 */
@Composable
public inline fun <reified T : Destination> rememberBackstackNavigator(
    initial: T
): BackstackNavigator<T> {
    return remember { BackstackNavigator(initial) }
}

public interface Navigator<T : Destination> {
    public val currentDestination: T
}

public class RegularNavigator<T : Destination>(initial: T) : Navigator<T> {

    private val item = mutableStateOf(initial)

    override val currentDestination: T
        get() = item.value

    /**
     * Replace the current [destination] with a new one, completely
     * destroying it.
     */
    public fun replace(destination: T) {
        item.value = destination
    }

}

public class BackstackNavigator<T : Destination>(initial: T) : Navigator<T> {

    private val items = mutableStateListOf(initial)

    public override val currentDestination: T
        get() = items.last()

    /**
     * Replace the current [destination] by adding a new one to the backstack.
     */
    public fun push(destination: T) {
        items.add(destination)
    }

    /**
     * Pop to the previous destination.
     * @return true if the current destination got removed,
     * false if the backstack was empty.
     */
    public fun pop(): Boolean {
        if (items.size <= 1) {
            return false
        }

        return items.removeLastOrNull() != null
    }

    /**
     * Replace the current [destination] in the backstack with a new one, completely
     * destroying it.
     *
     * Note: this does not alter the previous destinations.
     */
    public fun replace(destination: T) {
        items[items.lastIndex] = destination
    }

}