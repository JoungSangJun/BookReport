package kr.baekseok.bookreport

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.baekseok.bookreport.databinding.FragmentHomeBinding
import kr.baekseok.data.BooksInfoRepository
import kr.baekseok.data.DefaultAppContainer
import kr.baekseok.viewmodel.BooksUiState
import kr.baekseok.viewmodel.HomeViewModel

// viewModel에 매개변수 전달을 위한 ViewModelFactory 생성
class HomeViewModelFactory(private val booksInfoRepository: BooksInfoRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(booksInfoRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class HomeFragment : Fragment() {

    private lateinit var hBinding: FragmentHomeBinding
    lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        hBinding = FragmentHomeBinding.inflate(inflater, container, false)
        val homeViewModelFactory =
            HomeViewModelFactory(booksInfoRepository = DefaultAppContainer().booksPhotosRepository)
        homeViewModel = ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)

        hBinding.rectangle2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 텍스트 변경 중에 호출됩니다.
                homeViewModel.getBooksInfo(s.toString())
                when (homeViewModel.booksUiState) {
                    is BooksUiState.Loading -> Log.d("testt", "Loading")
                    is BooksUiState.Success -> Log.d(
                        "testt",
                        (homeViewModel.booksUiState as BooksUiState.Success).booksInfo.items[0].volumeInfo.title
                    )
                    is BooksUiState.Error -> Log.d("testt", "error")
                    else -> Log.d("testt", "init")
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // 텍스트 변경 후에 호출됩니다.
            }
        })

        hBinding.fab.setOnClickListener {
            val context = requireContext()
            val intent = Intent(context, BookAddActivity::class.java)
            startActivity(intent)
        }

        return hBinding.root
    }
}