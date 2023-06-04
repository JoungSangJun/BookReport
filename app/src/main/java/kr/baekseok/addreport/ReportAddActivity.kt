package kr.baekseok.addreport

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kr.baekseok.bookreport.BookAddActivity
import kr.baekseok.bookreport.R
import kr.baekseok.bookreport.databinding.ActivityReportAddBinding
import kr.baekseok.data.VolumeInfo
import kr.baekseok.room.BookReportDao
import kr.baekseok.room.BookReportData

// viewModel에 매개변수 전달을 위한 ViewModelFactory 생성
class ReportAddViewModelFactory(private val booksReportDao: BookReportDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReportAddViewModel::class.java)) {
            return ReportAddViewModel(booksReportDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class ReportAddActivity : AppCompatActivity() {

    private lateinit var rBinding: ActivityReportAddBinding
    lateinit var reportAddViewModel: ReportAddViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rBinding = ActivityReportAddBinding.inflate(layoutInflater)
        setContentView(rBinding.root)
        supportActionBar?.title = "독서록 쓰기"

        val reportAddViewModelFactory =
            ReportAddViewModelFactory(booksReportDao = (application as BookReportApplication).bookReportDatabase.bookReportDao())
        reportAddViewModel =
            ViewModelProvider(this, reportAddViewModelFactory).get(ReportAddViewModel::class.java)

        getBookInfoAndDisplay()

        rBinding.btBookSelect.setOnClickListener {
            val intent = Intent(this, BookAddActivity::class.java)
            startActivity(intent)
        }

        rBinding.tvBookRegistration.setOnClickListener {
            reportAddViewModel.insert(
                BookReportData(
                    bookTitle = rBinding.etTitle.text.toString(),
                    bookContent = rBinding.etContent.text.toString()
                )
            )
            finish()
        }
    }

    private fun getBookInfoAndDisplay() {
        // BookAddActivity에서 선택한 책 데이터 받아옴
        val bookInfoJason = intent.getStringExtra("key")
        val bookInfo = bookInfoJason?.let { Json.decodeFromString<VolumeInfo>(it) }

        if (bookInfo != null) {
            rBinding.tvBookTitle.text = bookInfo.volumeInfo.title
            // 선택한 책 중 이미지가 등록되지 않은 책 처리하기 위함
            if (bookInfo.volumeInfo.imageLinks != null) {
                Glide.with(this)
                    .load(bookInfo.volumeInfo.imageLinks.smallThumbnail.replace("http", "https"))
                    .into(rBinding.ivBook)
            } else {
                rBinding.ivBook.setImageResource(R.drawable.image_no)
            }
        }
    }
}