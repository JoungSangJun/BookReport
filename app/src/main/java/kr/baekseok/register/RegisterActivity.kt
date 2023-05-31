package kr.baekseok.register

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import kr.baekseok.bookreport.R
import kr.baekseok.bookreport.databinding.ActivityRegisterBinding
import kr.baekseok.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var rBinding : ActivityRegisterBinding

    val registerViewModel : RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rBinding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        rBinding.lifecycleOwner = this
        rBinding.activity = this
        rBinding.viewModel = registerViewModel

        setObserve()
    }

    fun setObserve(){
        registerViewModel.showActivityMain.observe(this){
            if(it){
                finish()
                startActivity(Intent(this,LoginActivity::class.java))
                Toast.makeText(this,"회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show()
                Log.d("showActivityMain", "true")
            }else{
                Log.d("showActivityMain", "false")
            }
        }

        registerViewModel.pass_confirm_textColor.observe(this){
            if(it == false){
                rBinding.passwordConfirmGuidance.setTextColor(Color.parseColor("#FF0000"))
            }
        }

        registerViewModel.pass_textColor.observe(this){
            if(it == false){
                rBinding.passwordGuidance.setTextColor(Color.parseColor("#FF0000"))
            }
        }

        registerViewModel.email_textColor.observe(this){
            if(it == false){
                rBinding.emailGuidance.setTextColor(Color.parseColor("#FF0000"))
            }
        }

    }
}