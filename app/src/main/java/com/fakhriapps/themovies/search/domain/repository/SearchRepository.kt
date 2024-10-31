package com.fakhriapps.themovies.search.domain.repository

import com.fakhriapps.themovies.util.Resource
import com.fakhriapps.themovies.main.domain.models.Media
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getSearchList(
        fetchFromRemote: Boolean,
        query: String,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Media>>>

}










