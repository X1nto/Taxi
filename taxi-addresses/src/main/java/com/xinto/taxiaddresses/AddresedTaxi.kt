package com.xinto.taxiaddresses

import androidx.compose.animation.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.xinto.taxi.Destination
import com.xinto.taxi.Navigator
import com.xinto.taxi.Taxi

@Composable
public fun <T : Destination> AddressedTaxi(
    navigator: Navigator<T>,
    modifier: Modifier = Modifier,
    transitionSpec: AnimatedContentScope<T>.() -> ContentTransform = { fadeIn() with fadeOut() },
    content: AddressedTaxiScope.() -> Unit,
) {
    val updatedContent by rememberUpdatedState(content)
    val addressedTaxiAddressesProvider by remember {
        derivedStateOf {
            AddressedTaxiScope().apply(updatedContent)
        }
    }
    Taxi(
        navigator = navigator,
        modifier = modifier,
        transitionSpec = transitionSpec,
    ) {
        addressedTaxiAddressesProvider.addresses[getDestinationName<Destination>(it)]?.invoke(it)
    }
}

public class AddressedTaxiScope internal constructor() {

    @PublishedApi
    internal val addresses: MutableMap<String, @Composable (Destination) -> Unit> = mutableMapOf<String, @Composable (Destination) -> Unit>()

    public inline fun <reified T : Destination> address(
        noinline destination: @Composable (T) -> Unit
    ) {
        addresses[getDestinationName<T>()] = @Composable {
            destination(it as T)
        }
    }
}

@PublishedApi
internal inline fun <reified T : Destination> getDestinationName(): String {
    return T::class.simpleName ?: throw IllegalStateException()
}

@PublishedApi
internal inline fun <reified T : Destination> getDestinationName(destination: T): String {
    return destination::class.simpleName ?: throw IllegalStateException()
}