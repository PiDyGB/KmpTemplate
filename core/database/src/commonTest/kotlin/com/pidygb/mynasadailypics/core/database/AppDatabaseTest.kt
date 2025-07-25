package com.pidygb.mynasadailypics.core.database

import app.cash.sqldelight.db.SqlDriver
import com.pidygb.mynasadailypics.core.testing.createDriver
import kotlin.test.*

class AppDatabaseTest {

    private lateinit var driver: SqlDriver
    private lateinit var database: PicturesDatabase
    private lateinit var queries: PicturesDatabaseQueries

    @BeforeTest
    fun setup() {
        driver = createDriver(PicturesDatabase.Schema)
        database = PicturesDatabase(driver)
        queries = database.picturesDatabaseQueries
    }

    @AfterTest
    fun tearDown() {
        driver.close()
    }

    @Test
    fun `insertPicture and selectAllPictures work correctly`() {
        assertTrue(queries.selectAllPicturesEntities().executeAsList().isEmpty())

        queries.insertPictureEntity(
            date = "2023-01-01",
            explanation = "Explanation 1",
            hdUrl = "hdurl1.com",
            mediaType = "image",
            serviceVersion = "v1",
            title = "Title 1",
            url = "url1.com"
        )

        var pictures = queries.selectAllPicturesEntities().executeAsList()
        assertEquals(1, pictures.size)
        assertEquals("2023-01-01", pictures[0].date)
        assertEquals("Title 1", pictures[0].title)

        queries.insertPictureEntity(
            date = "2023-01-02",
            explanation = "Explanation 2",
            hdUrl = "hdurl2.com",
            mediaType = "image",
            serviceVersion = "v1",
            title = "Title 2",
            url = "url2.com"
        )

        pictures = queries.selectAllPicturesEntities().executeAsList()
        assertEquals(2, pictures.size)
        // Pictures should be ordered by date descending
        assertEquals("2023-01-02", pictures[0].date)
        assertEquals("2023-01-01", pictures[1].date)
    }

    @Test
    fun `insertPicture replaces existing picture with same date`() {
        queries.insertPictureEntity(
            date = "2023-01-01",
            explanation = "Initial Explanation",
            hdUrl = "initialhdurl.com",
            mediaType = "image",
            serviceVersion = "v1",
            title = "Initial Title",
            url = "initialurl.com"
        )

        var pictures = queries.selectAllPicturesEntities().executeAsList()
        assertEquals(1, pictures.size)
        assertEquals("Initial Title", pictures[0].title)

        queries.insertPictureEntity(
            date = "2023-01-01",
            explanation = "Updated Explanation",
            hdUrl = "updatedhdurl.com",
            mediaType = "image",
            serviceVersion = "v1",
            title = "Updated Title",
            url = "updatedurl.com"
        )

        pictures = queries.selectAllPicturesEntities().executeAsList()
        assertEquals(1, pictures.size)
        assertEquals("Updated Title", pictures[0].title)
        assertEquals("Updated Explanation", pictures[0].explanation)
    }

    @Test
    fun `deleteAllPictures removes all pictures`() {
        queries.insertPictureEntity("2023-01-01", "E1", "h1", "image", "v1", "T1", "u1")
        queries.insertPictureEntity("2023-01-02", "E2", "h2", "image", "v1", "T2", "u2")

        var pictures = queries.selectAllPicturesEntities().executeAsList()
        assertEquals(2, pictures.size)

        queries.deleteAllPicturesEntities()
        pictures = queries.selectAllPicturesEntities().executeAsList()
        assertTrue(pictures.isEmpty())
    }
}
