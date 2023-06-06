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
        val bookInfoJason = intent.getStringExtra("key")
        val bookInfo = bookInfoJason?.let { Json.decodeFromString<VolumeInfo>(it) }
        getBookInfoAndDisplay(bookInfo)

        rBinding.btBookSelect.setOnClickListener {
            val intent = Intent(this, BookAddActivity::class.java)
            startActivity(intent)
        }

        rBinding.tvBookRegistration.setOnClickListener {
            reportAddViewModel.insert(
                BookReportData(
                    bookImg = bookInfo?.volumeInfo?.imageLinks?.smallThumbnail
                        ?: "data:image/png;base64,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",
                    bookTitle = rBinding.etTitle.text.toString(),
                    bookContent = rBinding.etContent.text.toString()
                )
            )
            finish()
        }
    }

    private fun getBookInfoAndDisplay(bookInfo: VolumeInfo?) {
        // BookAddActivity에서 선택한 책 데이터 받아옴


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