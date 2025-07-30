package com.pidygb.template.core.testing

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema

expect fun createDriver(schema: SqlSchema<QueryResult.Value<Unit>>): SqlDriver