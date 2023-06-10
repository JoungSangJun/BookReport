package kr.baekseok.addreport

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.baekseok.room.BookReportDao
import kr.baekseok.room.BookReportData

class ReportAddViewModel(
    private val bookReportDao: BookReportDao
) : ViewModel() {

    fun deleteSelectedReport(selectedId: Int) {
        viewModelScope.launch {
            bookReportDao.deleteSelectedReport(selectedId)
        }
    }

    fun update(
        bookImg: String,
        bookTitle: String,
        bookContent: String,
        selectedId: Int
    ) {
        viewModelScope.launch {
            bookReportDao.updateReport(
                bookImg,
                bookTitle,
                bookContent,
                selectedId
            )
        }
    }

    fun insert(bookReportData: BookReportData) {
        viewModelScope.launch {
            bookReportDao.insert(bookReportData)
        }
    }
}