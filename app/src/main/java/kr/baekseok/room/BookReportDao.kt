package kr.baekseok.room

import androidx.room.*

@Dao
interface BookReportDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(bookReportData: BookReportData)

    @Query(
        """
        SELECT * FROM BookReport 
        """
    )
    suspend fun getAllBooksReport(): List<BookReportData>

    @Query(
        """
    UPDATE BookReport SET book_img = :bookImg, book_title = :bookTitle, book_content = :bookContent WHERE id = :selectedId
    """
    )
    suspend fun updateReport(
        bookImg: String,
        bookTitle: String,
        bookContent: String,
        selectedId: Int
    )

    @Query(
        """
    DELETE FROM BookReport WHERE id = :selectedId
    """
    )
    suspend fun deleteSelectedReport(selectedId: Int)
}