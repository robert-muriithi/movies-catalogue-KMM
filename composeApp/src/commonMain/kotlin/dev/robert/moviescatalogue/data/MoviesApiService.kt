package dev.robert.moviescatalogue.data

import dev.robert.moviescatalogue.data.constants.Constants.BASEURL
import dev.robert.moviescatalogue.data.dto.CreditsResponse
import dev.robert.moviescatalogue.data.dto.GenresResponse
import dev.robert.moviescatalogue.data.dto.MovieDetailsResponse
import dev.robert.moviescatalogue.data.dto.MoviesResponse
import dev.robert.moviescatalogue.data.dto.MultiSearchResponse
import dev.robert.moviescatalogue.data.dto.ReviewResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter


class MoviesApiServiceImpl(
    private val httpClient: HttpClient
) : ApiService {
    companion object {
        private const val API_KEY = "YOUR_API_KEY"
    }
    override suspend fun getTrendingTodayMovies(page: Int): MoviesResponse {
        return try {
            httpClient.get("${BASEURL}trending/movie/day") {
                parameter("page", page)
                parameter("api_key", API_KEY)
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
                parameter("api_key", API_KEY)
                parameter("language", "en")
            }.body()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPopularTvShows(page: Int): MoviesResponse {
        return try {
            httpClient.get("${BASEURL}tv/popular"){
                parameter("page", page)
                parameter("api_key", API_KEY)
                parameter("language", "en")
            }.body()
        } catch (e: Exception){
            throw e
        }
    }

    override suspend fun getAirTodayTvShows(page: Int): MoviesResponse {
        return try {
            httpClient.get("${BASEURL}tv/airing_today"){
                parameter("page", page)
                parameter("api_key", API_KEY)
                parameter("language", "en")
            }.body()
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun getUpcomingMovies(page: Int): MoviesResponse {
        return try {
            httpClient.get("${BASEURL}movie/upcoming") {
                parameter("page", page)
                parameter("api_key", API_KEY)
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
                parameter("api_key", API_KEY)
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
                parameter("api_key", API_KEY)
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
                parameter("api_key", API_KEY)
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
                parameter("api_key", API_KEY)
                parameter("language", "en")
            }.body()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getMovieDetails(movieId: Int, isMovie: Boolean): MovieDetailsResponse {
        return try {
            val url = if (isMovie) "movie" else "tv"
            httpClient.get("${BASEURL}$url/$movieId") {
                parameter("api_key", API_KEY)
                parameter("language", "en")
            }.body()
        } catch (e: Exception) {
            throw e
        }
    }


    override suspend fun getMovieCredits(isMovie: Boolean, movieId: Int): CreditsResponse {
        val url = if (isMovie) "movie" else "tv"
        return try {
            httpClient.get("${BASEURL}$url/$movieId/credits"){
                parameter("api_key", API_KEY)
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
                    API_KEY)
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
                    API_KEY)
                parameter("language", "en")
            }.body()
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun multiSearch(query: String, page: Int): MultiSearchResponse {
        return try {
            httpClient.get("${BASEURL}search/multi"){
                parameter("page", page)
                parameter("query", query)
                parameter("api_key", API_KEY)
            }.body()
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun getDiscoverMovies(page: Int): MoviesResponse {
        return try {
            httpClient.get("${BASEURL}discover/movie"){
                parameter("page", page)
                parameter("api_key", API_KEY)
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
                parameter("api_key", API_KEY)
                parameter("language", "en")
            }.body()
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun getMovieReviews(isMovie: Boolean, movieId: Int, page: Int): ReviewResponse {
        val url = if (isMovie) "movie" else "tv"
        return try {
            httpClient.get("${BASEURL}$url/$movieId/reviews"){
                parameter("page", page)
                parameter("api_key", API_KEY)
                parameter("language", "en")
            }.body()
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun getSimilarMovies(isMovie: Boolean, movieId: Int, page: Int): MoviesResponse {
        val url = if (isMovie) "movie" else "tv"
        return try {
            httpClient.get("${BASEURL}$url/$movieId/similar") {
                parameter("page", page)
                parameter("api_key", API_KEY)
                parameter("language", "en")
            }.body()
        }
        catch (e: Exception){
            throw e
        }
    }

    override suspend fun getAllTrending(page: Int, window: String): MoviesResponse {
        return try {
            httpClient.get("${BASEURL}trending/all/$window") {
                parameter("page", page)
                parameter("api_key", API_KEY)
                parameter("language", "en")
            }.body()
        }catch (e: Exception){
            throw e
        }
    }
}