package com.pidygb.mynasadailypics.core.data

import com.pidygb.mynasadailypics.core.database.PicturesDatabase
import com.pidygb.mynasadailypics.core.database.resetAllPicturesEntities
import com.pidygb.mynasadailypics.core.database.selectAllPictures
import com.pidygb.mynasadailypics.core.model.Picture
import com.pidygb.mynasadailypics.core.network.PicturesRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

class PicturesRepositoryImpl(
    database: PicturesDatabase,
    private val remoteDataSource: PicturesRemoteDataSource,
    context: CoroutineContext
) : PicturesRepository {

    private val queries = database.picturesDatabaseQueries

    override val pictures: Flow<List<Picture>> = queries.selectAllPictures(context)

    override suspend fun getPictures() {
        queries.resetAllPicturesEntities(remoteDataSource.getPictures())
    }

}