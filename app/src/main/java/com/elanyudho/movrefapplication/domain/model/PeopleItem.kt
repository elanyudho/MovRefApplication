package com.elanyudho.movrefapplication.domain.model

data class PeopleItem(
    val peopleId: Int?,
    val peopleName: String?,
    val peopleMovie: List<String>?,
    val peopleImage: String?,
    val totalResults: Int?
)