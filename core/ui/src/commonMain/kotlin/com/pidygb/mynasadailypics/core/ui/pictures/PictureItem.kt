package com.pidygb.mynasadailypics.core.ui.pictures

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import coil3.ColorImage
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import org.jetbrains.compose.ui.tooling.preview.Preview

const val PICTURE_ITEM_IMAGE_TEST_TAG = "picture_item_image"
const val PICTURE_ITEM_TEST_TAG = "picture_item_test_tag"

@Composable
fun PictureItem(
    modifier: Modifier = Modifier,
    title: String,
    date: String,
    url: String,
    imageError: Painter?,
    onPictureClick: (date: String) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .clickable { onPictureClick(date) }
            .padding(horizontal = 16.dp)
            .testTag(PICTURE_ITEM_TEST_TAG)
    ) {
        Column(Modifier.weight(1f).wrapContentHeight()) {
            Text(
                title,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                date,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        AsyncImage(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(16f / 9f)
                .testTag(PICTURE_ITEM_IMAGE_TEST_TAG),
            model = url,
            contentScale = ContentScale.Crop,
            contentDescription = title,
            error = imageError,
        )
    }
}

@OptIn(ExperimentalCoilApi::class)
@Preview()
@Composable
fun PictureSurfacePreview() {
    MaterialTheme {
        CompositionLocalProvider(
            LocalAsyncImagePreviewHandler provides AsyncImagePreviewHandler {
                ColorImage(Color.Red.toArgb())
            }
        ) {
            PictureItem(
                title = "habitant",
                date = "graecis",
                url = "https://picsum.photos/130/64",
                onPictureClick = {},
                imageError = null
            )
        }
    }
}


