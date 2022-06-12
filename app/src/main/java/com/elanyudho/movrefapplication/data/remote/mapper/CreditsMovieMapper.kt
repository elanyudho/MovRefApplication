package com.elanyudho.movrefapplication.data.remote.mapper

import com.elanyudho.core.abstraction.BaseMapper
import com.elanyudho.movrefapplication.data.remote.response.CreditsMovieResponse
import com.elanyudho.movrefapplication.domain.model.CreditsMovie
import com.elanyudho.movrefapplication.utils.api.Constants

class CreditsMovieMapper: BaseMapper<CreditsMovieResponse, List<CreditsMovie>> {

    override fun mapToDomain(raw: CreditsMovieResponse): List<CreditsMovie> {
        return raw.cast.map {
            CreditsMovie(
                id = raw.id,
                peopleId = it.id,
                peopleName = it.name,
                peopleImage = Constants.BASE_URL_IMAGE + it.profilePath,
                character = it.character
            )
        }
    }

    override fun mapToRaw(domain: List<CreditsMovie>): CreditsMovieResponse {
        return CreditsMovieResponse()
    }
}