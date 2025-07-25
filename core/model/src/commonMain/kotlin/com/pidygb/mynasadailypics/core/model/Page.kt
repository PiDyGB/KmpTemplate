package com.pidygb.mynasadailypics.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Page(
    val name: String,
    val language: String,
    val components: List<Component>,
    val footer: Footer? = null
)