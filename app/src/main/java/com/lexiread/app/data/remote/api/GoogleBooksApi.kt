package com.lexiread.app.data.remote.api

import com.lexiread.app.data.remote.dto.GoogleBooksResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit interface for the Google Books API.
 * Used to fetch book metadata and cover images by title/author.
 */
interface GoogleBooksApi {

    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int = 1,
        @Query("printType") printType: String = "books"
    ): GoogleBooksResponse
}
