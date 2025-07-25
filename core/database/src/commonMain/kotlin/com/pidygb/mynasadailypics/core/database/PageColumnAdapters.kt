package com.pidygb.mynasadailypics.core.database

import app.cash.sqldelight.ColumnAdapter
import com.pidygb.mynasadailypics.core.model.Component
import com.pidygb.mynasadailypics.core.model.Footer
import kotlinx.serialization.json.Json

class ComponentListAdapter(private val json: Json) : ColumnAdapter<List<Component>, String> {
    override fun decode(databaseValue: String): List<Component> {
        return if (databaseValue.isEmpty()) {
            emptyList()
        } else {
            json.decodeFromString(databaseValue)
        }
    }

    override fun encode(value: List<Component>): String {
        return json.encodeToString(value)
    }
}

class FooterAdapter(private val json: Json) : ColumnAdapter<Footer, String> {
    override fun decode(databaseValue: String): Footer {
        return json.decodeFromString(databaseValue)
    }

    override fun encode(value: Footer): String {
        return json.encodeToString(value)
    }
}