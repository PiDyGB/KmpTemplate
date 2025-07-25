package com.pidygb.mynasadailypics.core.data

import app.cash.sqldelight.db.SqlDriver
import com.pidygb.mynasadailypics.core.database.PicturesDatabase
import com.pidygb.mynasadailypics.core.database.resetAllPicturesEntities
import com.pidygb.mynasadailypics.core.model.Picture
import com.pidygb.mynasadailypics.core.network.PicturesRemoteDataSource
import com.pidygb.mynasadailypics.core.testing.createDriver
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class PicturesRepositoryErrorTest {

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

    @Test
    fun `getPictures should propagate remote data source errors`() = runTest {
        // Arrange
        val mockRemoteDataSource = mock<PicturesRemoteDataSource> {
            everySuspend { getPictures() } throws RuntimeException("Network failure")
        }
        val repository = PicturesRepositoryImpl(
            database = database,
            remoteDataSource = mockRemoteDataSource,
            context = this.coroutineContext
        )

        // Act & Assert
        val exception = assertFailsWith<RuntimeException> {
            repository.getPictures()
        }

        assertEquals("Network failure", exception.message)
    }

    @Test
    fun `getPictures should reset database even with empty pictures list`() = runTest {
        // Arrange
        val emptyPictures = emptyList<Picture>()
        val mockRemoteDataSource = mock<PicturesRemoteDataSource> {
            everySuspend { getPictures() } returns emptyPictures
        }
        val repository = PicturesRepositoryImpl(
            database = database,
            remoteDataSource = mockRemoteDataSource,
            context = this.coroutineContext
        )

        // Populate the database first with some data
        val initialPictures = createMockPictures()
        database.picturesDatabaseQueries.resetAllPicturesEntities(initialPictures)

        // Act
        repository.getPictures()

        // Assert
        // Verify that the repository cleared the database and set it with an empty list
        val storedPictures = repository.pictures.first()
        assertEquals(0, storedPictures.size, "Database should contain empty list after reset with empty pictures")
    }

    @Test
    fun `getPictures should update database with pictures from remote source`() = runTest {
        // Arrange
        val mockPictures = createMockPictures()
        val mockRemoteDataSource = mock<PicturesRemoteDataSource> {
            everySuspend { getPictures() } returns mockPictures
        }
        val repository = PicturesRepositoryImpl(
            database = database,
            remoteDataSource = mockRemoteDataSource,
            context = this.coroutineContext
        )

        // Act
        repository.getPictures()

        // Assert
        val storedPictures = repository.pictures.first()
        assertEquals(mockPictures.size, storedPictures.size)
        assertEquals(mockPictures[0].title, storedPictures[0].title)
        assertEquals(mockPictures[1].title, storedPictures[1].title)
        assertEquals(mockPictures[2].title, storedPictures[2].title)
    }

    @Test
    fun `getPictures should handle pictures with null fields`() = runTest {
        // Arrange
        val incompleteData = listOf(
            Picture(
                date = "2025-07-15",
                explanation = null,  // Missing explanation
                hdUrl = null,  // Missing HD URL
                mediaType = "image",
                serviceVersion = "v1",
                title = "Incomplete Data Picture",
                url = "https://example.com/image.jpg"
            )
        )
        val mockRemoteDataSource = mock<PicturesRemoteDataSource> {
            everySuspend { getPictures() } returns incompleteData
        }
        val repository = PicturesRepositoryImpl(
            database = database,
            remoteDataSource = mockRemoteDataSource,
            context = this.coroutineContext
        )

        // Act
        repository.getPictures()

        // Assert
        val storedPictures = repository.pictures.first()
        assertEquals(1, storedPictures.size)
        assertEquals("Incomplete Data Picture", storedPictures[0].title)
        assertEquals(null, storedPictures[0].explanation)
        assertEquals(null, storedPictures[0].hdUrl)
    }
}