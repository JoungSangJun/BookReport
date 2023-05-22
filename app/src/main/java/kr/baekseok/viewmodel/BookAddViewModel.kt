package kr.baekseok.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.baekseok.data.BooksInfo
import kr.baekseok.data.BooksInfoRepository
import retrofit2.HttpException
import java.io.IOException

sealed interface BooksUiState {
    data class Success(val booksInfo: BooksInfo) : BooksUiState
    object Error : BooksUiState
    object Loading : BooksUiState
    object Initial : BooksUiState
}

class BookAddViewModel(private val booksInfoRepository: BooksInfoRepository) : ViewModel() {
    var booksUiState: MutableLiveData<BooksUiState> = MutableLiveData(BooksUiState.Initial)
        private set

    fun getBooksInfo(title: String) {
        viewModelScope.launch {
            booksUiState.value = try {
                val listResult = booksInfoRepository.getBooksInfo(title)
                BooksUiState.Success(listResult)
            } catch (e: IOException) {
                BooksUiState.Error
            } catch (e: HttpException) {
                BooksUiState.Error
            }
        }
    }
}