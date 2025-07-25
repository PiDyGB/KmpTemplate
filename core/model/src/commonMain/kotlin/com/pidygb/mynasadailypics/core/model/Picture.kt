package com.pidygb.mynasadailypics.core.model


data class Picture(
    val date: String,
    val explanation: String?,
    val hdUrl: String?,
    val mediaType: String?,
    val serviceVersion: String?,
    val title: String,
    val url: String
)