package kr.baekseok.data

import kr.baekseok.network.BooksApiService

interface BooksInfoRepository {
    suspend fun getBooksInfo(query: String): BooksInfo
}

class NetworkMarsPhotosRepository(
    private val booksApiService: BooksApiService
) : BooksInfoRepository {

    override suspend fun getBooksInfo(query: String): BooksInfo =
        booksApiService.getBooksInfo(query)
}