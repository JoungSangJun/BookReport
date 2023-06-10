package kr.baekseok.addreport

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isInvisible
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

        // BookAddActivity에서 선택한 책 데이터 받아옴
        val bookInfoJason = intent.getStringExtra("key")
        val bookInfo = bookInfoJason?.let { Json.decodeFromString<VolumeInfo>(it) }
        getBookInfoAndDisplay(bookInfo)

        // 독후감을 CRUD하기 위해 액티비티에 들어온다면 독후감에 대한 내용 받아옴
        val reportInfo = intent.getParcelableExtra<BookReportData>("book_report_data")

        // 독후감을 CRUD하기 위해 독후감을 클릭 or 독후감 추가하기 위해 클릭했는지 판별
        if (reportInfo?.bookTitle?.isNotEmpty() == true) {
            rBinding.tvBookRegistration.visibility = View.INVISIBLE
            rBinding.tvBookEdit.visibility = View.VISIBLE
            rBinding.tvBookDelete.visibility = View.VISIBLE
            rBinding.etTitle.setText(reportInfo.bookTitle)
            Glide.with(this)
                .load(reportInfo.bookImg!!.replace("http", "https"))
                .into(rBinding.ivBook)
            rBinding.etContent.setText(reportInfo.bookContent)
        }

        val reportAddViewModelFactory =
            ReportAddViewModelFactory(booksReportDao = (application as BookReportApplication).bookReportDatabase.bookReportDao())
        reportAddViewModel =
            ViewModelProvider(this, reportAddViewModelFactory).get(ReportAddViewModel::class.java)

        rBinding.tvBookDelete.setOnClickListener {
            reportAddViewModel.deleteSelectedReport(reportInfo!!.id)
            finish()
        }

        rBinding.btBookSelect.setOnClickListener {
            val intent = Intent(this, BookAddActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
        }

        rBinding.tvBookEdit.setOnClickListener {
            reportAddViewModel.update(
                reportInfo!!.bookImg!!,
                rBinding.etTitle.text.toString(),
                rBinding.etContent.text.toString(),
                reportInfo.id
            )
            finish()
        }

        rBinding.tvBookRegistration.setOnClickListener {
            reportAddViewModel.insert(
                BookReportData(
                    bookImg = bookInfo?.volumeInfo?.imageLinks?.smallThumbnail,
                    bookTitle = rBinding.etTitle.text.toString(),
                    bookContent = rBinding.etContent.text.toString()
                )
            )
            finish()
        }
    }

    private fun getBookInfoAndDisplay(bookInfo: VolumeInfo?) {
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