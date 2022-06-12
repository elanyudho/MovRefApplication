package com.elanyudho.movrefapplication.data.remote.mapper

import com.elanyudho.core.abstraction.BaseMapper
import com.elanyudho.movrefapplication.data.remote.response.ImagePeopleResponse
import com.elanyudho.movrefapplication.domain.model.PeopleImage
import com.elanyudho.movrefapplication.utils.api.Constants

class ImagePeopleMapper: BaseMapper<ImagePeopleResponse, List<PeopleImage>> {

    override fun mapToDomain(raw: ImagePeopleResponse): List<PeopleImage> {
        return raw.profiles.map {
            PeopleImage(
                peopleId = raw.id,
                peopleImage = Constants.BASE_URL_IMAGE + it.filePath,
            )
        }
    }

    override fun mapToRaw(domain: List<PeopleImage>): ImagePeopleResponse {
        return ImagePeopleResponse()
    }
}