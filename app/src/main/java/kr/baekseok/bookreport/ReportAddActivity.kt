package kr.baekseok.bookreport

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kr.baekseok.bookreport.databinding.ActivityReportAddBinding
import kr.baekseok.data.VolumeInfo

/*
 책 추가하기 버튼 누르면 어떻게 책 고를건지
 */

class ReportAddActivity : AppCompatActivity() {

    private lateinit var rBinding: ActivityReportAddBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rBinding = ActivityReportAddBinding.inflate(layoutInflater)
        setContentView(rBinding.root)
        supportActionBar?.title = "독서록 쓰기"

        getBookInfoAndDisplay()

        rBinding.btBookSelect.setOnClickListener {
            val intent = Intent(this, BookAddActivity::class.java)
            startActivity(intent)
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

    // 등록버튼 생성
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_book_report_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // 등록버튼 클릭시 이벤트 처리
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            R.id.book_report_post -> {
                Toast.makeText(getApplicationContext(), "Search Action", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item);
        }
    }
}