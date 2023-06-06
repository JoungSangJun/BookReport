package kr.baekseok.data

import kotlinx.serialization.json.Json
import kr.baekseok.network.BooksApiService
import kr.baekseok.room.BookReportDao
import kr.baekseok.room.BookReportDatabase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val booksPhotosRepository: BooksInfoRepository
}

class DefaultAppContainer : AppContainer {
    private val BASE_URL = "https://www.googleapis.com"

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: BooksApiService by lazy {
        retrofit.create(BooksApiService::class.java)
    }


    override val booksPhotosRepository: BooksInfoRepository by lazy {
        NetworkMarsPhotosRepository(retrofitService)
    }
}
