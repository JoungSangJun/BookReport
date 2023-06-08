package kr.baekseok.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.baekseok.room.BookReportDao
import kr.baekseok.room.BookReportData

class HomeViewModel(private val bookReportDao: BookReportDao) : ViewModel() {

    val reportUiState: MutableLiveData<List<BookReportData>> by lazy {
        MutableLiveData<List<BookReportData>>()
    }

    init {
        Log.d("testt","viewModel init")
        getAllBooksReport()
    }

    fun getAllBooksReport() {
        viewModelScope.launch {
            reportUiState.value = bookReportDao.getAllBooksReport()
        }
    }
}