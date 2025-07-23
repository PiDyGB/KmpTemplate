package com.pidygb.mynasadailypics.mynasadailypics

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}