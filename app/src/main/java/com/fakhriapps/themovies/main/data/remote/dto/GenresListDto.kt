package com.fakhriapps.themovies.main.data.remote.dto

import com.fakhriapps.themovies.main.domain.models.Genre

data class GenresListDto(
    val genres: List<Genre>
)