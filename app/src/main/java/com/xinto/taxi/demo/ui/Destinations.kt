package com.xinto.taxi.demo.ui

import com.xinto.taxi.Destination
import kotlinx.parcelize.Parcelize

sealed interface BackstackDestination : Destination {

    @Parcelize
    data class Count(val index: Int) : BackstackDestination

    @Parcelize
    object Replaced : BackstackDestination
}

sealed interface RegularDestination : Destination {

    @Parcelize
    object ScreenOne : RegularDestination

    @Parcelize
    object ScreenTwo : RegularDestination

}