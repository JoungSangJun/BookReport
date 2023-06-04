package kr.baekseok.addreport

import android.app.Application
import kr.baekseok.room.BookReportDatabase

class BookReportApplication : Application() {
    val bookReportDatabase: BookReportDatabase by lazy { BookReportDatabase.getDatabase(this) }
}