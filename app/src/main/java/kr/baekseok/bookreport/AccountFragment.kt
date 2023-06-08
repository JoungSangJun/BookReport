package kr.baekseok.bookreport

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kr.baekseok.bookreport.databinding.FragmentAccountBinding
import kr.baekseok.login.LoginActivity


class AccountFragment : Fragment() {
    private lateinit var aBinding: FragmentAccountBinding
    var loginLogoutCode = MyApplication.preferences.getInt("loginLogoutCode", 0)
    val mAuth: FirebaseAuth = Firebase.auth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        aBinding = FragmentAccountBinding.inflate(layoutInflater, container, false)

        if (loginLogoutCode == 1) {
            aBinding.btnRegister.setText("로그아웃")
        } else if (loginLogoutCode == 0) {
            aBinding.btnRegister.setText("로그인/회원가입 ->")
        }

        btnGuestMode()

        btnRegister()
        btnResetPw()
        btnAppInfo()
        btnAppGuide()


        return aBinding.root
    }

<<<<<<< HEAD
    private fun btnRegister() {
=======
    fun btnRegister() {

>>>>>>> feature#hyeongu
        aBinding.btnRegister.setOnClickListener {
            if (loginLogoutCode == 0) {
                loginLogoutCode = 1
                MyApplication.preferences.setInt("loginLogoutCode", loginLogoutCode)
                startActivity(Intent(activity, LoginActivity::class.java))
                activity?.finish()
            } else if (loginLogoutCode == 1) {
                aBinding.btnRegister.setOnClickListener {
                    mAuth.signOut() // 로그아웃
                    loginLogoutCode = 0
                    MyApplication.preferences.setInt("loginLogoutCode", loginLogoutCode)
                    startActivity(Intent(activity, MainActivity::class.java))
                    activity?.finish()
                }
            }
        }

    }

    fun btnFindPass() {

    }

    fun btnGuestMode() { // 게스트 모드
        aBinding.btnGuest.setOnClickListener {
            val builder = AlertDialog.Builder(context)
                .setMessage(R.string.dialog_guest)
                .setPositiveButton("전환",
                DialogInterface.OnClickListener { dialog, which ->
                    Toast.makeText(context,"게스트 모드로 전환되었습니다.", Toast.LENGTH_SHORT).show()
                    mAuth.signOut()
                    loginLogoutCode = 0
                    MyApplication.preferences.setInt("loginLogoutCode", loginLogoutCode)
                    startActivity(Intent(context, MainActivity::class.java))
                    activity?.finish()
                })
                .setNegativeButton("취소",
                DialogInterface.OnClickListener{ dialog, which ->
                    Toast.makeText(context,"취소되었습니다.", Toast.LENGTH_SHORT).show()
                })
            builder.show()
        }
    }

    private fun btnResetPw() {
        aBinding.txResetPw.setOnClickListener {
            startActivity(Intent(activity, PasswordResetActivity::class.java))
        }
    }

    private fun btnAppInfo() {
        aBinding.txAppInfo.setOnClickListener {
            startActivity(Intent(activity, AppInfoActivity::class.java))
        }
    }

    private fun btnAppGuide() {
        aBinding.txGuide.setOnClickListener {
            startActivity(Intent(activity, AppGuideActivity::class.java))
        }
    }



}