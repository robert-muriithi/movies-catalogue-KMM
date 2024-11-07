package dev.robert.moviescatalogue.domain.utils

import dev.robert.moviescatalogue.data.constants.Constants.IMAGE_BASE_URL
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json


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

fun formatDate(isoString: String?): String {
    try {
        val instant = isoString?.let { Instant.parse(it) }
        val dateTime = instant?.toLocalDateTime(TimeZone.UTC)
        val day = dateTime?.dayOfMonth
        val month = dateTime?.month?.let { month -> month.name.lowercase().replaceFirstChar { it.uppercase() } }
        val year = dateTime?.year
        return "$day $month, $year"
    } catch (e: Exception) {
        println("Error parsing date: ${e.message}")
        return ""
    }
}

