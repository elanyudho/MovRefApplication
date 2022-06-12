package com.elanyudho.movrefapplication.data.remote.mapper

import com.elanyudho.core.abstraction.BaseMapper
import com.elanyudho.movrefapplication.data.remote.response.CreditsPeopleResponse
import com.elanyudho.movrefapplication.domain.model.CreditsPeople
import com.elanyudho.movrefapplication.utils.api.Constants

class CreditsPeopleMapper: BaseMapper<CreditsPeopleResponse, List<CreditsPeople>> {

    override fun mapToDomain(raw: CreditsPeopleResponse): List<CreditsPeople> {
        return raw.cast.map {
            CreditsPeople(
                id = raw.id,
                movieName = it.title,
                movieImage = Constants.BASE_URL_IMAGE + it.posterPath,
                character = it.character,
                movieId = it.id
            )
        }
    }

    override fun mapToRaw(domain: List<CreditsPeople>): CreditsPeopleResponse {
        return CreditsPeopleResponse()
    }
}