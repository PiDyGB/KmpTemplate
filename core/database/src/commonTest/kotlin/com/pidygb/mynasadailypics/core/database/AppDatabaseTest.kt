package com.pidygb.mynasadailypics.core.database

import app.cash.sqldelight.db.SqlDriver
import com.pidygb.mynasadailypics.core.model.Footer
import com.pidygb.mynasadailypics.core.model.FooterSection
import com.pidygb.mynasadailypics.core.model.Social
import com.pidygb.mynasadailypics.core.testing.createDriver
import kotlinx.serialization.json.Json
import kotlin.test.*

class AppDatabaseTest {

    private lateinit var driver: SqlDriver
    private lateinit var database: PageDatabase
    private lateinit var queries: PageDatabaseQueries

    @BeforeTest
    fun setup() {
        driver = createDriver(PageDatabase.Schema)
        val json = Json { ignoreUnknownKeys = true }
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
    fun `insertPage and selectPageByName work correctly`() {
        // Test that no pages exist initially
        val initialPage = queries.selectPageByName("test-page").executeAsOneOrNull()
        assertNull(initialPage)

        // Insert a page
        queries.insertPageEntity(
            name = "test-page",
            language = "en",
            components = emptyList(),
            footer = null
        )

        // Verify the page was inserted
        val insertedPage = queries.selectPageByName("test-page").executeAsOneOrNull()
        assertNotNull(insertedPage)
        assertEquals("test-page", insertedPage.name)
        assertEquals("en", insertedPage.language)
        assertTrue(insertedPage.components.isEmpty())
        assertNull(insertedPage.footer)
    }

    @Test
    fun `insertPage replaces existing page with same name`() {
        // Insert initial page
        queries.insertPageEntity(
            name = "test-page",
            language = "en",
            components = emptyList(),
            footer = null
        )

        var page = queries.selectPageByName("test-page").executeAsOneOrNull()
        assertNotNull(page)
        assertEquals("en", page.language)

        // Replace with updated page
        val testFooter = Footer(
            id = "footer1",
            sections = emptyList(),
            socials = emptyList(),
            copyright = "2023"
        )
        queries.insertPageEntity(
            name = "test-page",
            language = "fr",
            components = emptyList(),
            footer = testFooter
        )

        page = queries.selectPageByName("test-page").executeAsOneOrNull()
        assertNotNull(page)
        assertEquals("fr", page.language)
        assertTrue(page.components.isEmpty())
        assertNotNull(page.footer)
        assertEquals("footer1", page.footer?.id)
    }
}
