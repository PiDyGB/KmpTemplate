package com.pidygb.mynasadailypics.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Component(
    val type: String,
    val id: String,
    val data: ComponentData
)

@Serializable
sealed interface ComponentData

@Serializable
data class TeaserData(
    val imageUrl: String,
    val headline: String,
    val description: String,
    val cta: CTA
) : ComponentData

@Serializable
data class ProductHighlightData(
    val imageUrl: String,
    val productName: String,
    val cta: CTA
) : ComponentData

@Serializable
data class CarouselData(
    val headline: String,
    val items: List<CarouselItem>
) : ComponentData

@Serializable
data class DepartmentExplorerData(
    val headline: String,
    val ctaWoman: CTA,
    val ctaMan: CTA
) : ComponentData

@Serializable
data class CarouselItem(
    val id: String,
    val imageUrl: String,
    val title: String,
    val description: String,
    val cta: CTA
)

@Serializable
data class CTA(
    val text: String,
    val url: String
)