package com.pidygb.template.core.model

data class Todo(
    val id: Int,
    val text: String,
    val completed: Boolean,
    val userId: Int
)