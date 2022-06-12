package com.elanyudho.movrefapplication.data.remote.mapper

import com.elanyudho.core.abstraction.BaseMapper
import com.elanyudho.movrefapplication.data.remote.response.GenreResponse
import com.elanyudho.movrefapplication.domain.model.Genre

class GenreMapper: BaseMapper<GenreResponse, List<Genre>> {

    override fun mapToDomain(raw: GenreResponse): List<Genre> {
        return raw.genres.map {
            Genre(
                id = it.id,
                genre = it.name,
            )
        }
    }

    override fun mapToRaw(domain: List<Genre>): GenreResponse {
        return GenreResponse()
    }
}