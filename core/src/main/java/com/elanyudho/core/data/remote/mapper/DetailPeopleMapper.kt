package com.elanyudho.core.data.remote.mapper

import com.elanyudho.core.abstraction.BaseMapper
import com.elanyudho.core.data.remote.response.DetailPeopleResponse
import com.elanyudho.core.domain.model.DetailPeople
import com.elanyudho.core.extension.api.Constants

class DetailPeopleMapper: BaseMapper<DetailPeopleResponse, DetailPeople> {

    override fun mapToDomain(raw: DetailPeopleResponse): DetailPeople {
        return DetailPeople(
            peopleId = raw.id,
            peopleName = raw.name,
            peopleImage = Constants.BASE_URL_IMAGE + raw.profilePath,
            peopleBiography = raw.biography,
            peopleBirthday = if (raw.placeOfBirth == null || raw.birthday == null) "Unknown" else raw.placeOfBirth + " on " + raw.birthday,
        )
    }

    override fun mapToRaw(domain: DetailPeople): DetailPeopleResponse {
        return DetailPeopleResponse()
    }
}