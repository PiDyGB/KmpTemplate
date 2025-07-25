package com.pidygb.mynasadailypics.core.data

import com.pidygb.mynasadailypics.core.model.Picture
import kotlinx.coroutines.flow.Flow

interface PicturesRepository {
    val pictures: Flow<List<Picture>>
    suspend fun getPictures()
}