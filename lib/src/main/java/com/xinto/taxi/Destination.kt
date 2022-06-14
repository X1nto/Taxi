package com.xinto.taxi

import android.os.Parcelable

/**
 * A base destination interface for declaring app-specific destinations.
 *
 * The interface derives from [Parcelable], which makes it easy to restore the navigation
 * state when the composable is recomposed.
 *
 * Please consider pairing this with [KotlinX Parcelize](https://developer.android.com/kotlin/parcelize),
 * unless you want to handle parcelization yourself.
 */
public interface Destination : Parcelable