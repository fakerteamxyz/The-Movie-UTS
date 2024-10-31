package com.fakhriapps.themovies.main.domain.repository

import com.fakhriapps.themovies.util.Resource
import com.fakhriapps.themovies.main.domain.models.Genre
import kotlinx.coroutines.flow.Flow

interface GenreRepository {
    suspend fun getGenres(
        fetchFromRemote: Boolean,
        type: String,
        apiKey: String
    ): Flow<Resource<List<Genre>>>
}










