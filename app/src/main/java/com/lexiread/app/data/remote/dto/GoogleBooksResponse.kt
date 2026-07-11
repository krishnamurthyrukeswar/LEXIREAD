package com.lexiread.app.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * DTOs for Google Books API responses.
 */
data class GoogleBooksResponse(
    @SerializedName("totalItems") val totalItems: Int = 0,
    @SerializedName("items") val items: List<BookItem>? = null
)

data class BookItem(
    @SerializedName("id") val id: String = "",
    @SerializedName("volumeInfo") val volumeInfo: VolumeInfo? = null
)

data class VolumeInfo(
    @SerializedName("title") val title: String? = null,
    @SerializedName("authors") val authors: List<String>? = null,
    @SerializedName("publisher") val publisher: String? = null,
    @SerializedName("publishedDate") val publishedDate: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("pageCount") val pageCount: Int? = null,
    @SerializedName("language") val language: String? = null,
    @SerializedName("imageLinks") val imageLinks: ImageLinks? = null,
    @SerializedName("industryIdentifiers") val industryIdentifiers: List<IndustryIdentifier>? = null
)

data class ImageLinks(
    @SerializedName("smallThumbnail") val smallThumbnail: String? = null,
    @SerializedName("thumbnail") val thumbnail: String? = null
)

data class IndustryIdentifier(
    @SerializedName("type") val type: String? = null,
    @SerializedName("identifier") val identifier: String? = null
)
