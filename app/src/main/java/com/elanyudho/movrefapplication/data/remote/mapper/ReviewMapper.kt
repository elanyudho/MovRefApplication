package com.elanyudho.movrefapplication.data.remote.mapper

import com.elanyudho.core.abstraction.BaseMapper
import com.elanyudho.movrefapplication.data.remote.response.ReviewResponse
import com.elanyudho.movrefapplication.domain.model.Review
import com.elanyudho.movrefapplication.utils.api.Constants

class ReviewMapper: BaseMapper<ReviewResponse, List<Review>> {

    override fun mapToDomain(raw: ReviewResponse): List<Review> {
        return raw.results.map {
            Review(
                username = it.authorDetails.username,
                avatar = Constants.BASE_URL_IMAGE + it.authorDetails.avatarPath,
                rating = it.authorDetails.rating,
                created = it.createdAt,
                comment = it.content
            )
        }
    }

    override fun mapToRaw(domain: List<Review>): ReviewResponse {
        return ReviewResponse()
    }
}