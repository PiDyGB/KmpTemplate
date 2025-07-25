package com.pidygb.mynasadailypics.core.model

data class Component(
    val type: String,
    val id: String,
    val data: ComponentData
)

sealed interface ComponentData

data class TeaserData(
    val imageUrl: String,
    val headline: String,
    val description: String,
    val cta: CTA
) : ComponentData

data class ProductHighlightData(
    val imageUrl: String,
    val productName: String,
    val cta: CTA
) : ComponentData

data class CarouselData(
    val headline: String,
    val items: List<CarouselItem>
) : ComponentData

data class DepartmentExplorerData(
    val headline: String,
    val ctaWoman: CTA,
    val ctaMan: CTA
) : ComponentData

data class CarouselItem(
    val id: String,
    val imageUrl: String,
    val title: String,
    val description: String,
    val cta: CTA
)

data class CTA(
    val text: String,
    val url: String
)