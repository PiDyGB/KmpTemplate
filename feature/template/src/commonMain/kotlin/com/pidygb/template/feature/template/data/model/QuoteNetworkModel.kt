package com.pidygb.template.feature.template.data.model

import kotlinx.serialization.Serializable

@Serializable
data class QuoteNetworkModel(
    val id: Int,
    val quote: String,
    val author: String
)
