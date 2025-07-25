package com.pidygb.mynasadailypics.core.network

import com.pidygb.mynasadailypics.core.model.*
import com.pidygb.mynasadailypics.core.network.model.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class PageRemoteDataSourceImpl(private val client: HttpClient) : PageRemoteDataSource {
    private val json = Json { ignoreUnknownKeys = true }
    
    override suspend fun getPage(): Page = client.get("host.json").body<NetworkPageResponse>().let { response ->
        Page(
            name = response.page.name,
            language = response.page.language,
            components = response.page.components.map { networkComponent ->
                Component(
                    type = networkComponent.type,
                    id = networkComponent.id,
                    data = mapComponentData(networkComponent.type, networkComponent.data)
                )
            },
            footer = response.page.footer?.let { networkFooter ->
                Footer(
                    id = networkFooter.id,
                    sections = networkFooter.sections.map { section ->
                        FooterSection(
                            title = section.title,
                            links = section.links.map { link ->
                                FooterLink(
                                    text = link.text,
                                    url = link.url
                                )
                            }
                        )
                    },
                    socials = networkFooter.socials.map { social ->
                        Social(
                            name = social.name,
                            url = social.url
                        )
                    },
                    copyright = networkFooter.copyright
                )
            }
        )
    }
    
    private fun mapComponentData(type: String, data: kotlinx.serialization.json.JsonElement): ComponentData {
        return when (type) {
            "teaser" -> {
                val teaserData = json.decodeFromJsonElement<NetworkTeaserData>(data)
                TeaserData(
                    imageUrl = teaserData.imageUrl,
                    headline = teaserData.headline,
                    description = teaserData.description,
                    cta = CTA(
                        text = teaserData.cta.text,
                        url = teaserData.cta.url
                    )
                )
            }
            "product_highlight" -> {
                val productData = json.decodeFromJsonElement<NetworkProductHighlightData>(data)
                ProductHighlightData(
                    imageUrl = productData.imageUrl,
                    productName = productData.productName,
                    cta = CTA(
                        text = productData.cta.text,
                        url = productData.cta.url
                    )
                )
            }
            "carousel" -> {
                val carouselData = json.decodeFromJsonElement<NetworkCarouselData>(data)
                CarouselData(
                    headline = carouselData.headline,
                    items = carouselData.items.map { item ->
                        CarouselItem(
                            id = item.id,
                            imageUrl = item.imageUrl,
                            title = item.title,
                            description = item.description,
                            cta = CTA(
                                text = item.cta.text,
                                url = item.cta.url
                            )
                        )
                    }
                )
            }
            "department_explorer" -> {
                val deptData = json.decodeFromJsonElement<NetworkDepartmentExplorerData>(data)
                DepartmentExplorerData(
                    headline = deptData.headline,
                    ctaWoman = CTA(
                        text = deptData.ctaWoman.text,
                        url = deptData.ctaWoman.url
                    ),
                    ctaMan = CTA(
                        text = deptData.ctaMan.text,
                        url = deptData.ctaMan.url
                    )
                )
            }
            else -> throw IllegalArgumentException("Unknown component type: $type")
        }
    }
}