package dev.robert.moviescatalogue.domain.utils

import dev.robert.moviescatalogue.data.api.constants.Constants.IMAGE_BASE_URL
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.encodeToString


fun String.createImageUrl(): String {
    return IMAGE_BASE_URL + this
}


object JsonConverter {
    fun <T> toJsonString(obj: T,  kSerializer: KSerializer<T>): String {
        return Json.encodeToString(kSerializer, obj)
    }

    inline fun <reified T> fromJsonString(json: String): T {
        return Json.decodeFromString(json)
    }
}

