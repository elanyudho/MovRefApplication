package com.elanyudho.core.domain.model

data class Review (
    val username: String,
    val rating: Int,
    val avatar: String,
    val comment: String,
    val created: String
)