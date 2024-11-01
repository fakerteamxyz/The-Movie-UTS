package com.fakhriapps.themovies.main.data.repository

import android.app.Application
import com.fakhriapps.themovies.R
import com.fakhriapps.themovies.main.data.local.genres.GenreEntity
import com.fakhriapps.themovies.main.data.local.genres.GenresDatabase
import com.fakhriapps.themovies.util.Resource
import com.fakhriapps.themovies.main.data.remote.api.GenresApi
import com.fakhriapps.themovies.main.domain.models.Genre
import com.fakhriapps.themovies.main.domain.repository.GenreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GenreRepositoryImpl @Inject constructor(
    private val application: Application,
    private val genreApi: GenresApi,
    genreDb: GenresDatabase
) : GenreRepository {


    private val genreDao = genreDb.genreDao

    override suspend fun getGenres(
        fetchFromRemote: Boolean,
        type: String,
        apiKey: String
    ): Flow<Resource<List<Genre>>> {
        return flow {
            emit(Resource.Loading(true))

            val genresEntity = genreDao.getGenres(type)

            if (genresEntity.isNotEmpty() && !fetchFromRemote) {
                emit(
                    Resource.Success(
                        genresEntity.map { genreEntity ->
                            Genre(
                                id = genreEntity.id,
                                name = genreEntity.name
                            )
                        }
                    )
                )
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteGenreList = try {
                genreApi.getGenresList(type, apiKey).genres
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(application.getString(R.string.couldn_t_load_genres)))
                emit(Resource.Loading(false))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(application.getString(R.string.couldn_t_load_genres)))
                emit(Resource.Loading(false))
                return@flow
            }

            remoteGenreList.let {

                genreDao.insertGenres(remoteGenreList.map { remoteGenre ->
                    GenreEntity(
                        id = remoteGenre.id,
                        name = remoteGenre.name,
                        type = type
                    )
                })

                emit(Resource.Success(remoteGenreList))
                emit(Resource.Loading(false))
            }

        }
    }

}










