package com.pidygb.template.core.database

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext
/*
fun TemplateDatabaseQueries.selectAllTemplate(context: CoroutineContext): Flow<List<String>> =
    selectAllTemplateEntities { date->
    }.asFlow().mapToList(context)
*/