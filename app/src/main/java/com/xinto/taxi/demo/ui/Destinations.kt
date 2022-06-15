package com.xinto.taxi.demo.ui

import com.xinto.taxi.Destination
import kotlinx.parcelize.Parcelize

@Parcelize
data class BackstackShowcase(val index: Int) : Destination

sealed interface RegularDestination : Destination {

    @Parcelize
    object ScreenOne : RegularDestination

    @Parcelize
    object ScreenTwo : RegularDestination

}