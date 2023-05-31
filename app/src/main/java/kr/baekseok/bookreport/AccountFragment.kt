package kr.baekseok.bookreport

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kr.baekseok.bookreport.databinding.FragmentAccountBinding
import kr.baekseok.login.LoginActivity


class AccountFragment : Fragment() {
    private lateinit var aBinding: FragmentAccountBinding
    private var loginLogoutCode = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        aBinding = FragmentAccountBinding.inflate(layoutInflater, container, false)
        val mAuth : FirebaseAuth = Firebase.auth
        val user : FirebaseUser = mAuth.currentUser!!

        user.getIdToken(true).addOnCompleteListener {
            if (it.isSuccessful){
                loginLogoutCode = 1
            }
        }


        btnRegister()

        return aBinding.root
    }

    fun btnRegister() {
        if(loginLogoutCode == 0) {
            aBinding.btnRegister.setOnClickListener {
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }else{
            aBinding.btnRegister.setText("로그아웃")

        }
    }


}