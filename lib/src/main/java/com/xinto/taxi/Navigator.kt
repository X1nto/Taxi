package com.xinto.taxi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList

/**
 * Create and remember [RegularNavigator] based on the provided [initial] [Destination]
 * to control the current destination inside the composable scope.
 *
 * Note: regular navigator does not store previous destinations, making it useless
 * for stack-based navigation.
 */
@Composable
public fun <T : Destination> rememberNavigator(
    initial: T
): RegularNavigator<T> {
    return rememberSaveable(
        saver = RegularNavigator.Saver()
    ) {
        RegularNavigator(initial)
    }
}

/**
 * Create and remember [BackstackNavigator] based on the provided [initial] [Destination]
 * to control the current destination inside the composable scope.
 *
 * Note: backstack navigator allows you to push new and pop to the previous destinations by
 * saving all destinations in a [SnapshotStateList].
 */
@Composable
public fun <T : Destination> rememberBackstackNavigator(
    initial: T
): BackstackNavigator<T> {
    return rememberSaveable(
        saver = BackstackNavigator.Saver()
    ) {
        BackstackNavigator(initial)
    }
}

public interface Navigator<T : Destination> {
    public val currentDestination: T

    public fun shouldRemoveStoreOwner(): Boolean
}

public class RegularNavigator<T : Destination> internal constructor(initial: T) : Navigator<T> {

    private val item = mutableStateOf(initial)

    override val currentDestination: T
        get() = item.value

    override fun shouldRemoveStoreOwner(): Boolean {
        return true
    }

    /**
     * Replace the current [destination] with a new one, completely
     * destroying it.
     */
    public fun replace(destination: T) {
        item.value = destination
    }

    public companion object {
        public fun <T : Destination> Saver(): Saver<RegularNavigator<T>, T> {
            return Saver(
                save = {
                    it.item.value
                },
                restore = { destination ->
                    RegularNavigator(destination)
                }
            )
        }
    }
}

public class BackstackNavigator<T : Destination> internal constructor(initial: T) : Navigator<T> {

    private val items = mutableStateListOf(initial)
    private var shouldRemoveStoreOwners = false

    public override val currentDestination: T
        get() = items.last()

    /**
     * Replace the current [destination] by adding a new one to the backstack.
     */
    public fun push(destination: T) {
        items.add(destination)
        shouldRemoveStoreOwners = false
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

        shouldRemoveStoreOwners = true
        items.removeLast()
        return true
    }

    override fun shouldRemoveStoreOwner(): Boolean {
        return shouldRemoveStoreOwners
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

    public companion object {
        public fun <T : Destination> Saver(): Saver<BackstackNavigator<T>, Any> {
            return listSaver(
                save = {
                    it.items.toList()
                },
                restore = { destinations ->
                    val navigator = BackstackNavigator(destinations.first())
                    for (i in 1 until destinations.size) {
                        navigator.push(destinations[i])
                    }
                    navigator
                }
            )
        }
    }
}