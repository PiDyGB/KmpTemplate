package com.pidygb.mynasadailypics.core.database

import app.cash.sqldelight.db.SqlDriver
import com.pidygb.mynasadailypics.core.model.*
import com.pidygb.mynasadailypics.core.testing.createDriver
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.*

class PageQueriesTest {

    private lateinit var driver: SqlDriver
    private lateinit var database: PageDatabase
    private lateinit var queries: PageDatabaseQueries
    private val testDispatcher = StandardTestDispatcher()
    private val json = Json { ignoreUnknownKeys = true }

    @BeforeTest
    fun setup() {
        driver = createDriver(PageDatabase.Schema)
        database = PageDatabase(
            driver = driver,
            PageEntityAdapter = PageEntity.Adapter(
                componentsAdapter = ComponentListAdapter(json),
                footerAdapter = FooterAdapter(json)
            )
        )
        queries = database.pageDatabaseQueries
    }

    @AfterTest
    fun tearDown() {
        driver.close()
    }

    @Test
    fun `selectPageByName returns null when page doesn't exist`() = runTest(testDispatcher) {
        val pageFlow = queries.selectPageByName("non-existent-page", testDispatcher)
        val page = pageFlow.first()
        assertNull(page)
    }

    @Test
    fun `selectPageByName returns page when it exists`() = runTest(testDispatcher) {
        // Insert test page
        val testPage = createTestPage("test-page")
        queries.insertPageEntity(
            name = testPage.name,
            language = testPage.language,
            components = testPage.components,
            footer = testPage.footer
        )

        // Test querying by name
        val pageFlow = queries.selectPageByName("test-page", testDispatcher)
        val page = pageFlow.first()

        assertNotNull(page)
        assertEquals("test-page", page.name)
        assertEquals("en-US", page.language)
        assertEquals(2, page.components.size)
        assertNotNull(page.footer)
        assertEquals("test-footer", page.footer?.id)
    }

    @Test
    fun `selectPageByName returns updated page after insertion`() = runTest(testDispatcher) {
        // Insert initial page
        val initialPage = createTestPage("update-test")
        queries.insertPageEntity(
            name = initialPage.name,
            language = initialPage.language,
            components = initialPage.components,
            footer = initialPage.footer
        )

        // Verify initial page
        var pageFlow = queries.selectPageByName("update-test", testDispatcher)
        var page = pageFlow.first()
        assertEquals("en-US", page?.language)
        assertEquals(2, page?.components?.size)

        // Create updated page with different values
        val updatedPage = Page(
            name = "update-test",
            language = "it-IT",
            components = listOf(
                Component(
                    type = "carousel",
                    id = "updated-component",
                    data = CarouselData(
                        headline = "Updated Headline",
                        items = listOf(
                            CarouselItem(
                                id = "item1",
                                imageUrl = "https://example.com/updated.jpg",
                                title = "Updated Title",
                                description = "Updated Description",
                                cta = CTA(text = "Updated CTA", url = "https://example.com/updated")
                            )
                        )
                    )
                )
            ),
            footer = Footer(
                id = "updated-footer",
                sections = emptyList(),
                socials = emptyList(),
                copyright = "Updated Copyright"
            )
        )

        // Insert updated page
        queries.insertPageEntity(
            name = updatedPage.name,
            language = updatedPage.language,
            components = updatedPage.components,
            footer = updatedPage.footer
        )

        // Verify page was updated
        pageFlow = queries.selectPageByName("update-test", testDispatcher)
        page = pageFlow.first()
        assertNotNull(page)
        assertEquals("it-IT", page.language)
        assertEquals(1, page.components.size)
        assertEquals("carousel", page.components[0].type)
        assertEquals("updated-component", page.components[0].id)
        assertTrue(page.components[0].data is CarouselData)
        assertEquals("updated-footer", page.footer?.id)
        assertEquals("Updated Copyright", page.footer?.copyright)
    }

    @Test
    fun `resetAllPagesEntities correctly inserts multiple pages`() = runTest(testDispatcher) {
        // Create test pages
        val page1 = createTestPage("page1")
        val page2 = createTestPage("page2")
        val page3 = createTestPage("page3")

        // Reset with multiple pages
        queries.resetAllPagesEntities(listOf(page1, page2, page3))

        // Verify all pages were inserted
        val page1Flow = queries.selectPageByName("page1", testDispatcher)
        val page2Flow = queries.selectPageByName("page2", testDispatcher)
        val page3Flow = queries.selectPageByName("page3", testDispatcher)

        val resultPage1 = page1Flow.first()
        val resultPage2 = page2Flow.first()
        val resultPage3 = page3Flow.first()

        assertNotNull(resultPage1)
        assertNotNull(resultPage2)
        assertNotNull(resultPage3)

        assertEquals("page1", resultPage1.name)
        assertEquals("page2", resultPage2.name)
        assertEquals("page3", resultPage3.name)
    }

    @Test
    fun `resetAllPagesEntities replaces existing pages`() = runTest(testDispatcher) {
        // Insert initial page
        val initialPage = createTestPage("existing-page")
        queries.insertPageEntity(
            name = initialPage.name,
            language = initialPage.language,
            components = initialPage.components,
            footer = initialPage.footer
        )

        // Create updated page with different values
        val updatedPage = Page(
            name = "existing-page",
            language = "fr-FR",
            components = listOf(
                Component(
                    type = "department_explorer",
                    id = "dept-explorer",
                    data = DepartmentExplorerData(
                        headline = "Department Explorer",
                        ctaWoman = CTA(text = "Women", url = "https://example.com/women"),
                        ctaMan = CTA(text = "Men", url = "https://example.com/men")
                    )
                )
            ),
            footer = null
        )

        // Reset with the updated page
        queries.resetAllPagesEntities(listOf(updatedPage))

        // Verify page was updated
        val pageFlow = queries.selectPageByName("existing-page", testDispatcher)
        val page = pageFlow.first()

        assertNotNull(page)
        assertEquals("fr-FR", page.language)
        assertEquals(1, page.components.size)
        assertEquals("department_explorer", page.components[0].type)
        assertTrue(page.components[0].data is DepartmentExplorerData)
        assertNull(page.footer)
    }

    @Test
    fun `resetAllPagesEntities with empty list does nothing`() = runTest(testDispatcher) {
        // Insert initial page
        val initialPage = createTestPage("test-page")
        queries.insertPageEntity(
            name = initialPage.name,
            language = initialPage.language,
            components = initialPage.components,
            footer = initialPage.footer
        )

        // Reset with empty list
        queries.resetAllPagesEntities(emptyList())

        // Original page should still exist
        val pageFlow = queries.selectPageByName("test-page", testDispatcher)
        val page = pageFlow.first()

        assertNotNull(page)
        assertEquals("test-page", page.name)
    }

    // Helper method to create a test page
    private fun createTestPage(name: String): Page {
        return Page(
            name = name,
            language = "en-US",
            components = listOf(
                Component(
                    type = "teaser",
                    id = "teaser-$name",
                    data = TeaserData(
                        imageUrl = "https://example.com/$name.jpg",
                        headline = "Headline for $name",
                        description = "Description for $name",
                        cta = CTA(text = "Learn more", url = "https://example.com/$name")
                    )
                ),
                Component(
                    type = "product_highlight",
                    id = "product-$name",
                    data = ProductHighlightData(
                        imageUrl = "https://example.com/product-$name.jpg",
                        productName = "Product for $name",
                        cta = CTA(text = "Buy now", url = "https://example.com/buy/$name")
                    )
                )
            ),
            footer = Footer(
                id = "test-footer",
                sections = listOf(
                    FooterSection(
                        title = "Links",
                        links = listOf(
                            FooterLink(text = "Link 1", url = "https://example.com/link1")
                        )
                    )
                ),
                socials = listOf(
                    Social(name = "instagram", url = "https://instagram.com/example")
                ),
                copyright = "© 2025 Test Inc."
            )
        )
    }
}
