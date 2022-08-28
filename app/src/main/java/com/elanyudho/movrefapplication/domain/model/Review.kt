package com.elanyudho.movrefapplication.domain.model

data class Review (
    val username: String,
    val rating: Int,
    val avatar: String,
    val comment: String,
    val created: String
)