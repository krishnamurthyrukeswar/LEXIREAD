package com.lexiread.app.di

import com.lexiread.app.data.remote.api.GoogleBooksApi
import com.lexiread.app.data.repository.BookRepositoryImpl
import com.lexiread.app.data.repository.ReadingProgressRepositoryImpl
import com.lexiread.app.data.repository.DictionaryRepositoryImpl
import com.lexiread.app.domain.repository.BookRepository
import com.lexiread.app.domain.repository.ReadingProgressRepository
import com.lexiread.app.domain.repository.DictionaryRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindBookRepository(impl: BookRepositoryImpl): BookRepository

    @Binds
    @Singleton
    abstract fun bindDictionaryRepository(impl: DictionaryRepositoryImpl): DictionaryRepository

    @Binds
    @Singleton
    abstract fun bindReadingProgressRepository(
        impl: ReadingProgressRepositoryImpl
    ): ReadingProgressRepository
}

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val GOOGLE_BOOKS_BASE_URL = "https://www.googleapis.com/books/v1/"

    @Provides
    @Singleton
    @Named("googleBooks")
    fun provideGoogleBooksRetrofit(
        okHttpClient: okhttp3.OkHttpClient,
        gson: com.google.gson.Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(GOOGLE_BOOKS_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideGoogleBooksApi(@Named("googleBooks") retrofit: Retrofit): GoogleBooksApi {
        return retrofit.create(GoogleBooksApi::class.java)
    }
}
