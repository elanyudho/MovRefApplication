package com.elanyudho.core.data.remote.mapper

import com.elanyudho.core.abstraction.BaseMapper
import com.elanyudho.core.data.remote.response.ImagePeopleResponse
import com.elanyudho.core.domain.model.PeopleImage
import com.elanyudho.core.extension.api.Constants

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