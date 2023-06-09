package kr.baekseok.bookreport

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kr.baekseok.adapter.BookRecyclerAdapter
import kr.baekseok.bookreport.databinding.ActivityBookAddBinding
import kr.baekseok.data.BooksInfoRepository
import kr.baekseok.data.DefaultAppContainer
import kr.baekseok.viewmodel.BookAddViewModel
import kr.baekseok.viewmodel.BooksUiState

// viewModel에 매개변수 전달을 위한 ViewModelFactory 생성
class BookAddViewModelFactory(private val booksInfoRepository: BooksInfoRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookAddViewModel::class.java)) {
            return BookAddViewModel(booksInfoRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class BookAddActivity : AppCompatActivity() {

    private lateinit var bBinding: ActivityBookAddBinding
    lateinit var bookAddViewModel: BookAddViewModel
    private lateinit var bookAdapter: BookRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bookAddViewModelFactory =
            BookAddViewModelFactory(booksInfoRepository = DefaultAppContainer().booksPhotosRepository)
        bookAddViewModel =
            ViewModelProvider(this, bookAddViewModelFactory).get(BookAddViewModel::class.java)

        initialize()
    }

    private fun initialize() {
        setupViews()
        setupRecyclerView()
        observeBooksUiState()
        editTextChange()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeBooksUiState() {
        val dataObserver: Observer<BooksUiState> = Observer { liveData ->
            bookAdapter.data = liveData
            bookAdapter.notifyDataSetChanged()
        }
        bookAddViewModel.booksUiState.observe(this, dataObserver)
    }

    private fun setupRecyclerView() {
        val recyclerView = bBinding.recyclerView
        bookAdapter =
            BookRecyclerAdapter(
                this,
                bookAddViewModel.booksUiState.value!!,
                LayoutInflater.from(this)
            )
        recyclerView.adapter = bookAdapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)
    }

    private fun setupViews() {
        bBinding = ActivityBookAddBinding.inflate(layoutInflater)
        setContentView(bBinding.root)
        supportActionBar?.title = "책 추가하기"
    }


    fun editTextChange() {
        bBinding.etTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 텍스트 변경 중에 호출됩니다.
                bookAddViewModel.getBooksInfo(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                // 텍스트 변경 후에 호출됩니다.
            }
        })
    }
}