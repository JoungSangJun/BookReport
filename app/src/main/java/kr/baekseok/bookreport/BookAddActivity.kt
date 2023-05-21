package kr.baekseok.bookreport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.baekseok.bookreport.databinding.ActivityBookAddBinding
import kr.baekseok.bookreport.databinding.ActivityReportAddBinding

class BookAddActivity : AppCompatActivity() {

    private lateinit var hBinding: ActivityBookAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hBinding = ActivityBookAddBinding.inflate(layoutInflater)
        setContentView(hBinding.root)
        supportActionBar?.title = "책 추가하기"


    }
}