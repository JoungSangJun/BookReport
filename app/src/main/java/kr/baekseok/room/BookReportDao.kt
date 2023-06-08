package kr.baekseok.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookReportDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(weatherData: BookReportData)

    @Query(
        """
        SELECT * FROM BookReport 
        """
    )
    suspend fun getAllBooksReport(): List<BookReportData>
}