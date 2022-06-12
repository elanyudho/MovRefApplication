package com.elanyudho.movrefapplication.data.remote.mapper

import com.elanyudho.core.abstraction.BaseMapper
import com.elanyudho.movrefapplication.data.remote.response.SearchPeopleResponse
import com.elanyudho.movrefapplication.domain.model.PeopleItem
import com.elanyudho.movrefapplication.utils.api.Constants

class SearchPeopleMapper: BaseMapper<SearchPeopleResponse, List<PeopleItem>> {

    override fun mapToDomain(raw: SearchPeopleResponse): List<PeopleItem> {
        return raw.results.map {
            val peopleMovie = ArrayList<String>()
            for (i in it.knownFor){
                peopleMovie.add(i.title)
            }

            PeopleItem(
                peopleId = it.id,
                peopleName = it.name,
                peopleImage = Constants.BASE_URL_IMAGE + it.profilePath,
                peopleMovie = peopleMovie,
                totalResults = raw.totalResults
            )
        }
    }

    override fun mapToRaw(domain: List<PeopleItem>): SearchPeopleResponse {
        return SearchPeopleResponse()
    }
}