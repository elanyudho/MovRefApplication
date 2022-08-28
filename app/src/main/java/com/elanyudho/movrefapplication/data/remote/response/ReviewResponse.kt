package com.elanyudho.movrefapplication.data.remote.response


import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("page")
    val page: Int = 0,
    @SerializedName("results")
    val results: List<Result> = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    @SerializedName("total_results")
    val totalResults: Int = 0
) {
    data class Result(
        @SerializedName("author")
        val author: String = "",
        @SerializedName("author_details")
        val authorDetails: AuthorDetails = AuthorDetails(),
        @SerializedName("content")
        val content: String = "",
        @SerializedName("created_at")
        val createdAt: String = "",
        @SerializedName("id")
        val id: String = "",
        @SerializedName("updated_at")
        val updatedAt: String = "",
        @SerializedName("url")
        val url: String = ""
    ) {
        data class AuthorDetails(
            @SerializedName("avatar_path")
            val avatarPath: String = "",
            @SerializedName("name")
            val name: String = "",
            @SerializedName("rating")
            val rating: Int = -1,
            @SerializedName("username")
            val username: String = ""
        )
    }
}