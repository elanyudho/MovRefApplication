package com.elanyudho.movrefapplication.data.remote.mapper

import com.elanyudho.core.abstraction.BaseMapper
import com.elanyudho.movrefapplication.data.remote.response.DetailPeopleResponse
import com.elanyudho.movrefapplication.domain.model.DetailPeople
import com.elanyudho.movrefapplication.utils.api.Constants

class DetailPeopleMapper: BaseMapper<DetailPeopleResponse, DetailPeople> {

    override fun mapToDomain(raw: DetailPeopleResponse): DetailPeople {
        return DetailPeople(
            peopleId = raw.id,
            peopleName = raw.name,
            peopleImage = Constants.BASE_URL_IMAGE + raw.profilePath,
            peopleBiography = raw.biography,
            peopleBirthday = raw.placeOfBirth + " on " + raw.birthday,
        )
    }

    override fun mapToRaw(domain: DetailPeople): DetailPeopleResponse {
        return DetailPeopleResponse()
    }
}