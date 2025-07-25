package com.pidygb.mynasadailypics.core.data

import com.pidygb.mynasadailypics.core.model.Picture


/**
 * Creates a standard set of mock pictures for testing
 */
fun createMockPictures(): List<Picture> = listOf(
    Picture(
        date = "2025-07-15",
        explanation = "A beautiful galaxy observed by NASA",
        hdUrl = "https://example.com/hd.jpg",
        mediaType = "image",
        serviceVersion = "v1",
        title = "Amazing Galaxy",
        url = "https://example.com/image.jpg"
    ),
    Picture(
        date = "2025-07-14",
        explanation = "The supernova explosion captured in detail",
        hdUrl = "https://example.com/supernova-hd.jpg",
        mediaType = "image",
        serviceVersion = "v1",
        title = "Supernova Explosion",
        url = "https://example.com/supernova.jpg"
    ),
    Picture(
        date = "2025-07-13",
        explanation = null,
        hdUrl = null,
        mediaType = "video",
        serviceVersion = "v1",
        title = "Space Station Flyby",
        url = "https://example.com/video.mp4"
    )
)
