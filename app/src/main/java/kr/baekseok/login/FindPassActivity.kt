package kr.baekseok.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.baekseok.bookreport.R
import kr.baekseok.bookreport.databinding.ActivityFindPassBinding

class FindPassActivity : AppCompatActivity() {

    private lateinit var fBinding : ActivityFindPassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_pass)
    }
}