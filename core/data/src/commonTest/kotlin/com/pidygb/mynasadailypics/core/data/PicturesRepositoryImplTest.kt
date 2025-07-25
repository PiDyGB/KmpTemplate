package com.pidygb.mynasadailypics.core.data

import app.cash.sqldelight.db.SqlDriver
import com.pidygb.mynasadailypics.core.database.PicturesDatabase
import com.pidygb.mynasadailypics.core.network.PicturesRemoteDataSource
import com.pidygb.mynasadailypics.core.testing.createDriver
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class PicturesRepositoryImplTest {

    private lateinit var database: PicturesDatabase
    private lateinit var driver: SqlDriver

    @BeforeTest
    fun setUp() {
        driver = createDriver(PicturesDatabase.Schema)
        database = PicturesDatabase(driver)
    }

    @AfterTest
    fun tearDown() {
        driver.close()
    }

    /**
     * This test verifies that when getPictures() is called, the repository:
     * 1. Deletes all existing data from the database.
     * 2. Fetches new data from the remote source.
     * 3. Insert the new data into the database.
     */
    @Test
    fun `getPictures should clear existing data and insert new data from remote`() = runTest {
        // Arrange
        // 1. Set up queries reference for convenience
        val queries = database.picturesDatabaseQueries

        // 2. Pre-populate the database with stale data to ensure it gets cleared.
        queries.insertPictureEntity("stale-date", "stale", "stale", "stale", "stale", "stale", "stale")
        assertEquals(
            1,
            queries.selectAllPicturesEntities().executeAsList().size,
            "Pre-condition failed: stale data not inserted."
        )

        // 3. Mock the remote data source to return a specific list of new pictures.
        val newPictures = createMockPictures()
        val mockRemoteDataSource = mock<PicturesRemoteDataSource> {
            everySuspend { getPictures() } returns newPictures
        }

        // 4. Create the repository with the real database and mock remote source.
        val repository = PicturesRepositoryImpl(
            database = database,
            remoteDataSource = mockRemoteDataSource,
            context = this.coroutineContext
        )

        // Act
        repository.getPictures()

        // Assert
        // 5. Verify that the database now contains only the new data.
        val picturesInDb = queries.selectAllPicturesEntities().executeAsList()

        assertEquals(newPictures.size, picturesInDb.size, "Database should contain the correct number of new pictures.")

        val remoteDates = newPictures.map { it.date }.sorted()
        val dbDates = picturesInDb.map { it.date }.sorted()
        assertEquals(remoteDates, dbDates, "The dates of pictures in the DB should match the remote data.")

        assertTrue(picturesInDb.none { it.date == "stale-date" }, "Stale data should have been deleted.")
    }

    @Test
    fun `pictures property should return Flow from database queries`() = runTest {
        // Arrange
        val queries = database.picturesDatabaseQueries
        val mockPictures = createMockPictures()

        // Insert data directly into the in-memory database.
        mockPictures.forEach {
            queries.insertPictureEntity(
                it.date,
                it.explanation,
                it.hdUrl,
                it.mediaType,
                it.serviceVersion,
                it.title,
                it.url
            )
        }

        val repository = PicturesRepositoryImpl(
            database = database,
            remoteDataSource = mock(), // Not used in this test
            context = this.coroutineContext
        )

        // Act
        // Collect the first emission from the Flow.
        val result = repository.pictures.first()

        // Assert
        assertEquals(mockPictures.size, result.size)
        assertEquals(mockPictures.map { it.date }.sorted(), result.map { it.date }.sorted())
    }

    @Test
    fun `pictures property should return empty flow when database is empty`() = runTest {
        // Arrange
        val repository = PicturesRepositoryImpl(
            database = database,
            remoteDataSource = mock(), // Not used in this test
            context = this.coroutineContext
        )

        // Act
        val result = repository.pictures.first()

        // Assert
        assertTrue(result.isEmpty(), "The flow should emit an empty list for an empty database.")
    }
}