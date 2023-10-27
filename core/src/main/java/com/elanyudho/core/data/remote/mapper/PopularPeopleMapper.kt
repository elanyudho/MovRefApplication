package com.elanyudho.core.data.remote.mapper

import com.elanyudho.core.abstraction.BaseMapper
import com.elanyudho.core.data.remote.response.PopularPeopleResponse
import com.elanyudho.core.domain.model.PeopleItem
import com.elanyudho.core.extension.api.Constants.BASE_URL_IMAGE

class PopularPeopleMapper: BaseMapper<PopularPeopleResponse, List<PeopleItem>> {

    override fun mapToDomain(raw: PopularPeopleResponse): List<PeopleItem> {
        return raw.results.map {
            val peopleMovie = ArrayList<String>()
            for (i in it.knownFor){
                peopleMovie.add(i.title)
            }

            PeopleItem(
                peopleId = it.id,
                peopleName = it.name,
                peopleMovie = peopleMovie,
                peopleImage = BASE_URL_IMAGE + it.profilePath,
                totalResults = raw.totalResults
            )
        }
    }

    override fun mapToRaw(domain: List<PeopleItem>): PopularPeopleResponse {
        return PopularPeopleResponse()
    }
}