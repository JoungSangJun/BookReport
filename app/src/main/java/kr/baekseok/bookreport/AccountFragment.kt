package kr.baekseok.bookreport

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kr.baekseok.bookreport.databinding.FragmentAccountBinding
import kr.baekseok.login.LoginActivity


class AccountFragment : Fragment() {
    private lateinit var aBinding: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        aBinding = FragmentAccountBinding.inflate(layoutInflater, container, false)


        btnRegister()
        btnResetPw()
        btnAppInfo()


        return aBinding.root
    }

    fun btnRegister() {
        aBinding.btnRegister.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
        }
    }

    fun btnResetPw() {
        aBinding.btnResetPw.setOnClickListener {
            startActivity(Intent(activity, PasswordResetActivity::class.java))
        }
    }

    fun btnAppInfo() {
        aBinding.txAppInfo.setOnClickListener {
            startActivity(Intent(activity, AppInfoActivity::class.java))
        }
    }


}