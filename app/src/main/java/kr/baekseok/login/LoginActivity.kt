package kr.baekseok.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import kr.baekseok.bookreport.MainActivity
import kr.baekseok.bookreport.R
import kr.baekseok.bookreport.databinding.ActivityLoginBinding
import kr.baekseok.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var lBinding : ActivityLoginBinding
    val loginViewModel : LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        lBinding.lifecycleOwner = this
        lBinding.viewModel = loginViewModel
        lBinding.activity = this
        setObserve()

    }

    fun setObserve(){
        loginViewModel.showRegisterActivity.observe(this){
            if(it){
                finish()
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }

        loginViewModel.showHomeActivity.observe(this){
            if(it){ // showHomeActivity의 값이 true 면 화면 이동
                finish()
                startActivity(Intent(this, MainActivity::class.java))
                Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun join(){
        loginViewModel.showRegisterActivity.value = true
    }
}