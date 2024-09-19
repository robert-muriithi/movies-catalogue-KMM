package dev.robert.moviescatalogue.data.api

import dev.robert.moviescatalogue.data.api.constants.Constants.BASEURL
import dev.robert.moviescatalogue.data.api.constants.Constants.INITIAL_PAGE
import dev.robert.moviescatalogue.data.api.dto.CreditsResponse
import dev.robert.moviescatalogue.data.api.dto.GenresResponse
import dev.robert.moviescatalogue.data.api.dto.MovieDetailsResponse
import dev.robert.moviescatalogue.data.api.dto.MoviesResponse
import dev.robert.moviescatalogue.data.api.dto.MultiSearchResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter


class MoviesApiServiceImpl(
    private val httpClient: HttpClient
) : ApiService {
    override suspend fun getTrendingTodayMovies(page: Int): MoviesResponse {
        return try {
            httpClient.get("${BASEURL}trending/movie/day") {
                parameter("page", page)
                parameter("api_key", "211a895b98fff91aefbcf2d7d4c624c2")
                parameter("language", "en")
            }.body()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPopularMovies(page: Int): MoviesResponse {
        return try {
            httpClient.get("${BASEURL}movie/popular") {
                parameter("page", page)
                parameter("api_key", "211a895b98fff91aefbcf2d7d4c624c2")
                parameter("language", "en")
            }.body()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getUpcomingMovies(page: Int): MoviesResponse {
        return try {
            httpClient.get("${BASEURL}movie/upcoming") {
                parameter("page", page)
                parameter("api_key", "211a895b98fff91aefbcf2d7d4c624c2")
                parameter("language", "en")
            }.body()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getTopRatedMovies(page: Int): MoviesResponse {
        return try {
            httpClient.get("${BASEURL}movie/top_rated") {
                parameter("page", page)
                parameter("api_key", "211a895b98fff91aefbcf2d7d4c624c2")
                parameter("language", "en")
            }.body()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getNowPlayingMovies(page: Int): MoviesResponse {
        return try {
            httpClient.get("${BASEURL}movie/now_playing") {
                parameter("page", page)
                parameter("api_key", "211a895b98fff91aefbcf2d7d4c624c2")
                parameter("language", "en")
            }.body()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getTrendingTvSeries(page: Int): MoviesResponse {
        return try {
            httpClient.get("${BASEURL}trending/tv/day") {
                parameter("page", page)
                parameter("api_key", "211a895b98fff91aefbcf2d7d4c624c2")
                parameter("language", "en")
            }.body()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getTopRatedTvSeries(page: Int): MoviesResponse {
        return try {
            httpClient.get("${BASEURL}tv/top_rated") {
                parameter("page", page)
                parameter("api_key", "211a895b98fff91aefbcf2d7d4c624c2")
                parameter("language", "en")
            }.body()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetailsResponse {
        return try {
            httpClient.get("${BASEURL}movie/$movieId") {
                parameter("api_key", "")
                parameter("language", "en")
            }.body()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getTvSeriesDetails(tvSeriesId: Int): MovieDetailsResponse {
        return try{
            httpClient.get("${BASEURL}tv/$tvSeriesId"){
                parameter("api_key", "211a895b98fff91aefbcf2d7d4c624c2")
                parameter("language", "en")
            }.body()
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun getTvSeriesCredits(tvSeriesId: Int): CreditsResponse {
        return try {
            httpClient.get("${BASEURL}tv/$tvSeriesId/credits"){
                parameter("api_key", "211a895b98fff91aefbcf2d7d4c624c2")
                parameter("language", "en")
            }.body()
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun getMovieCredits(movieId: Int): CreditsResponse {
        return try {
            httpClient.get("${BASEURL}movie/$movieId/credits"){
                parameter("api_key", "211a895b98fff91aefbcf2d7d4c624c2")
                parameter("language", "en")
            }.body()
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun getMovieGenres(): GenresResponse {
        return try {
            httpClient.get("${BASEURL}genre/movie/list"){
                parameter("api_key",
                    "211a895b98fff91aefbcf2d7d4c624c2")
                parameter("language", "en")
            }.body()
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun getTvSeriesGenres(): GenresResponse {
        return try {
            httpClient.get("${BASEURL}genre/tv/list"){
                parameter("api_key",
                    "211a895b98fff91aefbcf2d7d4c624c2")
                parameter("language", "en")
            }.body()
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun searchMovies(query: String, page: Int): MultiSearchResponse {
        return try {
            httpClient.get("${BASEURL}search/multi"){
                parameter("page", page)
                parameter("query", query)
                parameter("api_key", "211a895b98fff91aefbcf2d7d4c624c2")
            }.body()
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun getDiscoverMovies(page: Int): MoviesResponse {
        return try {
            httpClient.get("${BASEURL}discover/movie"){
                parameter("page", page)
                parameter("api_key", "211a895b98fff91aefbcf2d7d4c624c2")
                parameter("language", "en")
            }.body()
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun getDiscoverTvSeries(page: Int): MoviesResponse {
        return try {
            httpClient.get("${BASEURL}discover/tv"){
                parameter("page", page)
                parameter("api_key", "211a895b98fff91aefbcf2d7d4c624c2")
                parameter("language", "en")
            }.body()
        }catch (e: Exception){
            throw e
        }
    }
}