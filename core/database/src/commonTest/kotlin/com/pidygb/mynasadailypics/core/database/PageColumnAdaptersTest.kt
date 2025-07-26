package com.pidygb.mynasadailypics.core.database

import com.pidygb.mynasadailypics.core.model.*
import kotlinx.serialization.json.Json
import kotlin.test.*

class PageColumnAdaptersTest {

    private val json = Json { ignoreUnknownKeys = true }
    private lateinit var componentListAdapter: ComponentListAdapter
    private lateinit var footerAdapter: FooterAdapter

    @BeforeTest
    fun setup() {
        componentListAdapter = ComponentListAdapter(json)
        footerAdapter = FooterAdapter(json)
    }

    @Test
    fun `ComponentListAdapter encodes components correctly`() {
        val teaser = Component(
            type = "teaser",
            id = "teaser-1",
            data = TeaserData(
                imageUrl = "https://example.com/image.jpg",
                headline = "Test Headline",
                description = "Test Description",
                cta = CTA(text = "Click here", url = "https://example.com")
            )
        )

        val productHighlight = Component(
            type = "product_highlight",
            id = "product-1",
            data = ProductHighlightData(
                imageUrl = "https://example.com/product.jpg",
                productName = "Test Product",
                cta = CTA(text = "Buy now", url = "https://example.com/buy")
            )
        )

        val components = listOf(teaser, productHighlight)
        val encoded = componentListAdapter.encode(components)

        // Verify the encoded string contains expected data
        assertTrue(encoded.contains("teaser-1"))
        assertTrue(encoded.contains("product-1"))
        assertTrue(encoded.contains("Test Headline"))
        assertTrue(encoded.contains("Test Product"))
    }

    @Test
    fun `ComponentListAdapter decodes components correctly`() {
        val json = """
        [
            {
                "type": "teaser",
                "id": "teaser-1",
                "data": {
                    "imageUrl": "https://example.com/image.jpg",
                    "headline": "Test Headline",
                    "description": "Test Description",
                    "cta": {
                        "text": "Click here",
                        "url": "https://example.com"
                    }
                }
            },
            {
                "type": "product_highlight",
                "id": "product-1",
                "data": {
                    "imageUrl": "https://example.com/product.jpg",
                    "productName": "Test Product",
                    "cta": {
                        "text": "Buy now",
                        "url": "https://example.com/buy"
                    }
                }
            }
        ]
        """.trimIndent()

        val components = componentListAdapter.decode(json)

        assertEquals(2, components.size)
        assertEquals("teaser", components[0].type)
        assertEquals("teaser-1", components[0].id)
        assertTrue(components[0].data is TeaserData)

        val teaserData = components[0].data as TeaserData
        assertEquals("Test Headline", teaserData.headline)
        assertEquals("Click here", teaserData.cta.text)

        assertEquals("product_highlight", components[1].type)
        assertEquals("product-1", components[1].id)
        assertTrue(components[1].data is ProductHighlightData)

        val productData = components[1].data as ProductHighlightData
        assertEquals("Test Product", productData.productName)
    }

    @Test
    fun `ComponentListAdapter handles empty list correctly`() {
        val emptyList = emptyList<Component>()
        val encoded = componentListAdapter.encode(emptyList)
        val decoded = componentListAdapter.decode(encoded)

        assertTrue(decoded.isEmpty())
    }

    @Test
    fun `ComponentListAdapter handles empty string correctly`() {
        val decoded = componentListAdapter.decode("")
        assertTrue(decoded.isEmpty())
    }

    @Test
    fun `FooterAdapter encodes footer correctly`() {
        val footer = Footer(
            id = "footer-1",
            sections = listOf(
                FooterSection(
                    title = "Company",
                    links = listOf(
                        FooterLink(text = "About Us", url = "https://example.com/about")
                    )
                )
            ),
            socials = listOf(
                Social(name = "twitter", url = "https://twitter.com/example")
            ),
            copyright = "© 2025 Example Inc."
        )

        val encoded = footerAdapter.encode(footer)

        assertTrue(encoded.contains("footer-1"))
        assertTrue(encoded.contains("Company"))
        assertTrue(encoded.contains("About Us"))
        assertTrue(encoded.contains("twitter"))
        assertTrue(encoded.contains("2025 Example Inc."))
    }

    @Test
    fun `FooterAdapter decodes footer correctly`() {
        val json = """
        {
            "id": "footer-1",
            "sections": [
                {
                    "title": "Company",
                    "links": [
                        {
                            "text": "About Us",
                            "url": "https://example.com/about"
                        }
                    ]
                }
            ],
            "socials": [
                {
                    "name": "twitter",
                    "url": "https://twitter.com/example"
                }
            ],
            "copyright": "© 2025 Example Inc."
        }
        """.trimIndent()

        val footer = footerAdapter.decode(json)

        assertEquals("footer-1", footer.id)
        assertEquals(1, footer.sections.size)
        assertEquals("Company", footer.sections[0].title)
        assertEquals(1, footer.sections[0].links.size)
        assertEquals("About Us", footer.sections[0].links[0].text)
        assertEquals(1, footer.socials.size)
        assertEquals("twitter", footer.socials[0].name)
        assertEquals("© 2025 Example Inc.", footer.copyright)
    }

    @Test
    fun `FooterAdapter throws exception for invalid JSON`() {
        val invalidJson = "{invalid_json"
        assertFailsWith<Exception> {
            footerAdapter.decode(invalidJson)
        }
    }
}
