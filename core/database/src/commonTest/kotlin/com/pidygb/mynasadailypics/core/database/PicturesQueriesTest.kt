package com.pidygb.mynasadailypics.core.database

import app.cash.sqldelight.db.SqlDriver
import com.pidygb.mynasadailypics.core.model.Picture
import com.pidygb.mynasadailypics.core.testing.createDriver
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class PicturesQueriesTest {

    private lateinit var driver: SqlDriver
    private lateinit var queries: PicturesDatabaseQueries

    @BeforeTest
    fun setup() {
        driver = createDriver(PicturesDatabase.Schema)
        queries = PicturesDatabase(driver).picturesDatabaseQueries
    }

    @AfterTest
    fun tearDown() {
        driver.close()
    }


    @Test
    fun `selectAllPictures initially returns empty list`() = runTest {
        val pictures = queries.selectAllPictures(coroutineContext).first()
        assertTrue(pictures.isEmpty(), "Database should be empty initially")
    }

    @Test
    fun `resetAllPicturesEntities correctly populates an empty database`() = runTest {
        val samplePictures = listOf(
            Picture(
                date = "2023-01-01",
                title = "Pic 1",
                explanation = "Expl 1",
                url = "url1",
                hdUrl = "hdurl1",
                mediaType = "image",
                serviceVersion = "v1"
            ),
            Picture(
                date = "2023-01-02",
                title = "Pic 2",
                explanation = "Expl 2",
                url = "url2",
                hdUrl = "hdurl2",
                mediaType = "image",
                serviceVersion = "v1"
            )
        )

        queries.resetAllPicturesEntities(samplePictures)

        val picturesFromDb = queries.selectAllPictures(coroutineContext).first()

        assertEquals(samplePictures.size, picturesFromDb.size)
        assertEquals(
            samplePictures.toSet(),
            picturesFromDb.toSet(),
            "Database content should match the inserted pictures"
        )
    }

    @Test
    fun `resetAllPicturesEntities deletes old data and inserts new data`() = runTest {
        // Arrange: Insert initial data
        val initialPicture = Picture(
            date = "2022-12-31",
            title = "Old Pic",
            explanation = "Old Expl",
            url = "old_url",
            hdUrl = "old_hdurl",
            mediaType = "image",
            serviceVersion = "v1"
        )
        queries.insertPictureEntity(
            date = initialPicture.date,
            explanation = initialPicture.explanation,
            hdUrl = initialPicture.hdUrl,
            mediaType = initialPicture.mediaType,
            serviceVersion = initialPicture.serviceVersion,
            title = initialPicture.title,
            url = initialPicture.url
        )
        assertEquals(1, queries.selectAllPictures(coroutineContext).first().size, "Initial picture should be inserted")

        // Act: Call resetAllPicturesEntities with new data
        val newPictures = listOf(
            Picture(
                date = "2023-01-01",
                title = "New Pic 1",
                explanation = "New Expl 1",
                url = "new_url1",
                hdUrl = "new_hdurl1",
                mediaType = "image",
                serviceVersion = "v1"
            ),
            Picture(
                date = "2023-01-02",
                title = "New Pic 2",
                explanation = "New Expl 2",
                url = "new_url2",
                hdUrl = "new_hdurl2",
                mediaType = "image",
                serviceVersion = "v1"
            )
        )
        queries.resetAllPicturesEntities(newPictures)

        // Assert: Verify the database state reflects the new data
        val picturesFromDb = queries.selectAllPictures(coroutineContext).first()
        assertEquals(newPictures.size, picturesFromDb.size, "Database should contain the new number of pictures")
        assertEquals(
            newPictures.toSet(),
            picturesFromDb.toSet(),
            "Database content should be replaced with new pictures"
        )
    }

    @Test
    fun `selectAllPictures returns all inserted pictures`() = runTest {
        val samplePictures = listOf(
            Picture(
                date = "2023-01-01",
                title = "Pic 1",
                explanation = "Expl 1",
                url = "url1",
                hdUrl = "hdurl1",
                mediaType = "image",
                serviceVersion = "v1"
            ),
            Picture(
                date = "2023-01-02",
                title = "Pic 2",
                explanation = "Expl 2",
                url = "url2",
                hdUrl = "hdurl2",
                mediaType = "image",
                serviceVersion = "v1"
            )
        )

        // Arrange: Insert pictures manually
        samplePictures.forEach {
            queries.insertPictureEntity(
                date = it.date,
                explanation = it.explanation,
                hdUrl = it.hdUrl,
                mediaType = it.mediaType,
                serviceVersion = it.serviceVersion,
                title = it.title,
                url = it.url
            )
        }

        // Act
        val picturesFromDb = queries.selectAllPictures(coroutineContext).first()

        // Assert
        assertEquals(samplePictures.size, picturesFromDb.size)
        assertEquals(
            samplePictures.toSet(),
            picturesFromDb.toSet(),
            "selectAllPictures should return all inserted pictures"
        )
    }
}