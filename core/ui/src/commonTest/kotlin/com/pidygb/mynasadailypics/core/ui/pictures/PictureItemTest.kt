package com.pidygb.mynasadailypics.core.ui.pictures

import androidx.compose.ui.test.*
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalTestApi::class)
class PictureItemTest {

    /**
     * Tests that the title text is displayed correctly.
     */
    @Test
    fun pictureItem_DisplaysTitleCorrectly() = runComposeUiTest {
        val title = "Picture Title"
        val date = "2025-07-20"
        val url = ""

        setContent {
            PictureItem(
                title = title,
                date = date,
                url = url,
                imageError = null,
                onPictureClick = {}
            )
        }

        onNodeWithText(title).assertIsDisplayed()
    }

    /**
     * Tests that the date text is displayed correctly.
     */
    @Test
    fun pictureItem_DisplaysDateCorrectly() = runComposeUiTest {
        val title = "Picture Title"
        val date = "2025-07-20"
        val url = ""

        setContent {
            PictureItem(
                title = title,
                date = date,
                url = url,
                imageError = null,
                onPictureClick = {}
            )
        }

        onNodeWithText(date).assertIsDisplayed()
    }

    /**
     * Tests that the image is displayed and has the correct content description.
     */
    @Test
    fun pictureItem_DisplaysImageWithCorrectContentDescription() = runComposeUiTest {
        val title = "Picture Title"
        val date = "2025-07-20"
        val url = ""

        setContent {
            PictureItem(
                title = title,
                date = date,
                url = url,
                imageError = null,
                onPictureClick = {}
            )
        }

        onNodeWithContentDescription(title).assertIsDisplayed()
    }

    /**
     * Tests that clicking on the row triggers the onPictureClick lambda.
     */
    @Test
    fun pictureItem_RowClickTriggersOnPictureClick() = runComposeUiTest {
        val title = "Picture Title"
        val date = "2025-07-20"
        val url = ""
        var clickedDate: String? = null

        setContent {
            PictureItem(
                title = title,
                date = date,
                url = url,
                imageError = null,
                onPictureClick = { clickedDate = it }
            )
        }

        onNode(hasClickAction()).performClick()
        assertEquals(date, clickedDate)
    }
}