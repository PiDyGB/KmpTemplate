package com.pidygb.mynasadailypics.mynasadailypics

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform