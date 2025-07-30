package com.pidygb.template.core.testing

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import java.util.*

actual fun createDriver(schema: SqlSchema<QueryResult.Value<Unit>>): SqlDriver =
    JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY, Properties(), schema)