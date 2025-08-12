package com.pidygb.template.core.network

import kotlinx.serialization.Serializable

@Serializable
data class TodoNetworkModel(
    val id: Int,
    val todo: String,
    val completed: Boolean,
    val userId: Int
)