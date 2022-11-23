package com.xinto.taxi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

internal class NavigatorViewModel : ViewModel() {

    private val viewModelStores = mutableMapOf<Destination, ViewModelStore>()

    private var shouldBeRemoved: Destination? = null

    override fun onCleared() {
        viewModelStores.values.forEach {
            it.clear()
        }
        viewModelStores.clear()
    }

    fun scheduleForRemoval(destination: Destination) {
        shouldBeRemoved = destination
    }

    fun removeScheduled() {
        if (shouldBeRemoved != null) {
            viewModelStores.remove(shouldBeRemoved).also {
                it?.clear()
            }
        }
    }

    fun getViewModelStoreForDestination(destination: Destination): ViewModelStoreOwner {
        return ViewModelStoreOwner {
            viewModelStores.getOrPut(destination) {
                ViewModelStore()
            }
        }
    }

}