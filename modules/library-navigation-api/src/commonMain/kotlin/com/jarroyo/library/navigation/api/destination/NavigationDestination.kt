package com.jarroyo.library.navigation.api.destination

import kotlin.random.Random

abstract class NavigationDestination {
    abstract val route: String

    open val arguments: List<String>
        get() = emptyList()

    abstract class Result {
        abstract val id: Long
        var consumed: Boolean = false

        companion object {
            val uniqueId
                get() = Random.nextInt()
        }
    }

    companion object {
        @Suppress("LateinitUsage") lateinit var DEEP_LINK_SCHEME: String
    }
}
