package com.pidygb.mynasadailypics.core.database

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.pidygb.mynasadailypics.core.model.Page
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext


fun PageDatabaseQueries.selectPageByName(
    name: String, 
    context: CoroutineContext
): Flow<Page?> =
    selectPageByName(name) { pageName, language, components, footer ->
        Page(
            name = pageName,
            language = language,
            components = components,
            footer = footer
        )
    }.asFlow().mapToList(context).map { it.firstOrNull() }

fun PageDatabaseQueries.resetAllPagesEntities(
    pages: List<Page>
) {
    transaction {
        pages.forEach { page ->
            insertPageEntity(
                name = page.name,
                language = page.language,
                components = page.components,
                footer = page.footer
            )
        }
    }
}