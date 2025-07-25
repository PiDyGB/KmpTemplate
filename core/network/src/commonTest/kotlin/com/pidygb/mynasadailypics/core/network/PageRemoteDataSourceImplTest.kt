package com.pidygb.mynasadailypics.core.network

import com.pidygb.mynasadailypics.core.model.*
import io.ktor.client.engine.mock.*
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PageRemoteDataSourceImplTest {

    private val mockJsonResponse = """
    {
        "page": {
            "name": "Prada Homepage",
            "language": "en-US",
            "components": [
                {
                    "type": "teaser",
                    "id": "teaser-fw-2025",
                    "data": {
                        "imageUrl": "https://picsum.photos/seed/promo1/1920/1080",
                        "headline": "Women's Fall/Winter 2025",
                        "description": "Discover the new collection, a study in contrasts and contemporary elegance.",
                        "cta": {
                            "text": "Explore the Collection",
                            "url": "prada://collection?id=fw2025&department=woman"
                        }
                    }
                },
                {
                    "type": "product_highlight",
                    "id": "product-highlight-bag",
                    "data": {
                        "imageUrl": "https://picsum.photos/seed/bag1/800/1000",
                        "productName": "Prada Re-Edition 2005",
                        "cta": {
                            "text": "Shop Now",
                            "url": "prada://product?sku=re-edition-2005"
                        }
                    }
                },
                {
                    "type": "carousel",
                    "id": "carousel-pradasphere",
                    "data": {
                        "headline": "Pradasphere News",
                        "items": [
                            {
                                "id": "news-1",
                                "imageUrl": "https://picsum.photos/seed/news1/600/400",
                                "title": "The Art of Craftsmanship",
                                "description": "A deep dive into the techniques behind our most iconic pieces.",
                                "cta": {
                                    "text": "Read More",
                                    "url": "prada://article?slug=art-of-craftsmanship"
                                }
                            }
                        ]
                    }
                },
                {
                    "type": "department_explorer",
                    "id": "dept-explorer-main",
                    "data": {
                        "headline": "Prada Explore",
                        "cta_woman": {
                            "text": "Shop Woman",
                            "url": "prada://products?department=woman"
                        },
                        "cta_man": {
                            "text": "Shop Man",
                            "url": "prada://products?department=man"
                        }
                    }
                }
            ],
            "footer": {
                "id": "main-footer",
                "sections": [
                    {
                        "title": "Company",
                        "links": [
                            {
                                "text": "Prada Group",
                                "url": "prada://static?page=group"
                            }
                        ]
                    }
                ],
                "socials": [
                    {
                        "name": "twitter",
                        "url": "https://twitter.com/prada"
                    }
                ],
                "copyright": "© PRADA 2025. All rights reserved."
            }
        }
    }
    """.trimIndent()

    @Test
    fun `getPage returns page with all component types when the call is successful`() = runTest {
        val mockEngine = MockEngine {
            respond(
                content = mockJsonResponse,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
        val dataSource = PageRemoteDataSourceImpl(client)
        val page = dataSource.getPage()
        
        // Test page basic properties
        assertEquals("Prada Homepage", page.name)
        assertEquals("en-US", page.language)
        assertEquals(4, page.components.size)
        
        // Test teaser component
        val teaserComponent = page.components[0]
        assertEquals("teaser", teaserComponent.type)
        assertEquals("teaser-fw-2025", teaserComponent.id)
        assertTrue(teaserComponent.data is TeaserData)
        val teaserData = teaserComponent.data as TeaserData
        assertEquals("Women's Fall/Winter 2025", teaserData.headline)
        assertEquals("Explore the Collection", teaserData.cta.text)
        
        // Test product highlight component
        val productComponent = page.components[1]
        assertEquals("product_highlight", productComponent.type)
        assertEquals("product-highlight-bag", productComponent.id)
        assertTrue(productComponent.data is ProductHighlightData)
        val productData = productComponent.data as ProductHighlightData
        assertEquals("Prada Re-Edition 2005", productData.productName)
        assertEquals("Shop Now", productData.cta.text)
        
        // Test carousel component
        val carouselComponent = page.components[2]
        assertEquals("carousel", carouselComponent.type)
        assertEquals("carousel-pradasphere", carouselComponent.id)
        assertTrue(carouselComponent.data is CarouselData)
        val carouselData = carouselComponent.data as CarouselData
        assertEquals("Pradasphere News", carouselData.headline)
        assertEquals(1, carouselData.items.size)
        assertEquals("The Art of Craftsmanship", carouselData.items[0].title)
        
        // Test department explorer component
        val deptComponent = page.components[3]
        assertEquals("department_explorer", deptComponent.type)
        assertEquals("dept-explorer-main", deptComponent.id)
        assertTrue(deptComponent.data is DepartmentExplorerData)
        val deptData = deptComponent.data as DepartmentExplorerData
        assertEquals("Prada Explore", deptData.headline)
        assertEquals("Shop Woman", deptData.ctaWoman.text)
        assertEquals("Shop Man", deptData.ctaMan.text)
        
        // Test footer
        assertNotNull(page.footer)
        val footer = page.footer!!
        assertEquals("main-footer", footer.id)
        assertEquals(1, footer.sections.size)
        assertEquals("Company", footer.sections[0].title)
        assertEquals(1, footer.sections[0].links.size)
        assertEquals("Prada Group", footer.sections[0].links[0].text)
        assertEquals(1, footer.socials.size)
        assertEquals("twitter", footer.socials[0].name)
        assertEquals("© PRADA 2025. All rights reserved.", footer.copyright)
    }

    @Test
    fun `getPage throws an exception when the call is unsuccessful`() = runTest {
        val mockEngine = MockEngine {
            respondError(HttpStatusCode.InternalServerError)
        }
        val client = HttpClient(mockEngine)
        val dataSource = PageRemoteDataSourceImpl(client)
        assertFails {
            dataSource.getPage()
        }
    }
}