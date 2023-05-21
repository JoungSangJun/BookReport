package kr.baekseok.bookreport

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kr.baekseok.bookreport.databinding.ActivityReportAddBinding

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

        rBinding.btBookSelect.setOnClickListener {
            val intent = Intent(this, BookAddActivity::class.java)
            startActivity(intent)
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