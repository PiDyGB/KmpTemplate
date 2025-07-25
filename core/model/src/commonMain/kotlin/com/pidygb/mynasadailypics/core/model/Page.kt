package com.pidygb.mynasadailypics.core.model

data class Page(
    val name: String,
    val language: String,
    val components: List<Component>,
    val footer: Footer? = null
)