package kr.baekseok.room

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "BookReport"
)
data class BookReportData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "book_img")
    val bookImg: String? = null,
    @ColumnInfo(name = "book_title")
    val bookTitle: String = "",
    @ColumnInfo(name = "book_content")
    val bookContent: String = ""
)