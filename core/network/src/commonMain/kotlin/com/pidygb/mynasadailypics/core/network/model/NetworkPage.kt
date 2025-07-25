package com.pidygb.mynasadailypics.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class NetworkPageResponse(
    val page: NetworkPage
)

@Serializable
data class NetworkPage(
    val name: String,
    val language: String,
    val components: List<NetworkComponent>,
    val footer: NetworkFooter? = null
)

@Serializable
data class NetworkComponent(
    val type: String,
    val id: String,
    val data: JsonElement
)

@Serializable
data class NetworkTeaserData(
    val imageUrl: String,
    val headline: String,
    val description: String,
    val cta: NetworkCTA
)

@Serializable
data class NetworkProductHighlightData(
    val imageUrl: String,
    val productName: String,
    val cta: NetworkCTA
)

@Serializable
data class NetworkCarouselData(
    val headline: String,
    val items: List<NetworkCarouselItem>
)

@Serializable
data class NetworkDepartmentExplorerData(
    val headline: String,
    @SerialName("cta_woman") val ctaWoman: NetworkCTA,
    @SerialName("cta_man") val ctaMan: NetworkCTA
)

@Serializable
data class NetworkCarouselItem(
    val id: String,
    val imageUrl: String,
    val title: String,
    val description: String,
    val cta: NetworkCTA
)

@Serializable
data class NetworkFooter(
    val id: String,
    val sections: List<NetworkFooterSection>,
    val socials: List<NetworkSocial>,
    val copyright: String
)

@Serializable
data class NetworkFooterSection(
    val title: String,
    val links: List<NetworkFooterLink>
)

@Serializable
data class NetworkFooterLink(
    val text: String,
    val url: String
)

@Serializable
data class NetworkSocial(
    val name: String,
    val url: String
)

@Serializable
data class NetworkCTA(
    val text: String,
    val url: String
)