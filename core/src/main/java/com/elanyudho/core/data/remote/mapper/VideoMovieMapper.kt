package com.elanyudho.core.data.remote.mapper

import com.elanyudho.core.abstraction.BaseMapper
import com.elanyudho.core.data.remote.response.VideoResponse
import com.elanyudho.core.domain.model.Video

class VideoMovieMapper: BaseMapper<VideoResponse, List<Video>> {

    override fun mapToDomain(raw: VideoResponse): List<Video> {
        return raw.results.map {
            Video(
                id = it.id,
                name = it.name,
                site = it.site,
                publishedDate = it.publishedAt,
                url = it.key,
                type = it.type
            )
        }.filter { it.type == "Trailer" && it.site == "YouTube" }
    }

    override fun mapToRaw(domain: List<Video>): VideoResponse {
        return VideoResponse()
    }
}