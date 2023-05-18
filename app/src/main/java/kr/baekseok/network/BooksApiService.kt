package kr.baekseok.network

import kr.baekseok.data.BooksInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApiService {
    @GET("books/v1/volumes")
    suspend fun getBooksInfo(@Query("q") query: String): BooksInfo
}