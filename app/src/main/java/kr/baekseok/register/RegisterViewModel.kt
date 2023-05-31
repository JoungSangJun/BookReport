package kr.baekseok.register

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.util.*
import java.util.regex.Pattern

class RegisterViewModel : ViewModel() {

    var mPassFlag: MutableLiveData<Int> = MutableLiveData(0)
    var mPassConfirmFlag: MutableLiveData<Int> = MutableLiveData(0)
    var mEmailFlag: MutableLiveData<Int> = MutableLiveData(0)

    open var edt_name: MutableLiveData<String> = MutableLiveData("")
    open var edt_email: MutableLiveData<String> = MutableLiveData("")
    open var edt_pass: MutableLiveData<String> = MutableLiveData("")
    var mail_vaildation: MutableLiveData<String> = MutableLiveData("")
    var pass_vaildation: MutableLiveData<String> = MutableLiveData("")
    open var edt_pass_confirm: MutableLiveData<String> = MutableLiveData("")
    var pass_confirm_vaildation: MutableLiveData<String> = MutableLiveData("")
    val showActivityMain: MutableLiveData<Boolean> = MutableLiveData(false)

    var email_textColor: MutableLiveData<Boolean> = MutableLiveData(true)
    var pass_confirm_textColor: MutableLiveData<Boolean> = MutableLiveData(true)
    var pass_textColor: MutableLiveData<Boolean> = MutableLiveData(true)

    val name = edt_name.value.toString()




    var watcher_mail: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable) {
            if (s.isNotEmpty()) {
                checkEmail()
            } else if (s.isEmpty()) {
                mail_vaildation.value = ""
            }
        }
    }

    var watcher_pass: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable) {
            if (s.isNotEmpty()) {
                checkPassword()
            } else if (s.isEmpty()) {
                pass_vaildation.value = ""
            }
        }
    }

    var watcher_pass_confirm: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                checkPasswordConfirm()
            }
        }
    }


    fun btnRegister() {

        val auth = Firebase.auth

        if (edt_name.value.toString().isNotEmpty() && edt_email.value.toString().isNotEmpty()
            && edt_pass.value.toString()
                .isNotEmpty() && edt_pass.value.toString() == edt_pass_confirm.value.toString()
        ) {

            auth.createUserWithEmailAndPassword(
                edt_email.value.toString(),
                edt_pass.value.toString()
            ).addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val firebaseUser: FirebaseUser? = auth.currentUser
                    val userUid = firebaseUser?.uid!!
                    val reference =
                        FirebaseDatabase.getInstance().reference.child("Users").child(userUid)
                            .child("userAccount") //id 정보 담는 테이블
                    val idHashMap: HashMap<String, Any> = HashMap()

                    idHashMap["pw"] = edt_pass.value.toString()
                    idHashMap["uid"] = userUid
                    idHashMap["username"] = edt_name.value.toString().lowercase(Locale.getDefault())
                    idHashMap["email"] = edt_email.value.toString()

                    reference.setValue(idHashMap)
                    Log.d("registerTest", "회원가입 성공")
                    showActivityMain.value = true

                } else {
                    Log.d("registerTest", "회원가입 실패")

                }
            }


        }
    }


    fun checkEmail(): Boolean { // 이메일 유효성 검사
        val pattern = Patterns.EMAIL_ADDRESS
        return if (pattern.matcher(edt_email.value.toString().trim()).matches()) {
            mEmailFlag.value = 1
            mail_vaildation.value = ""
            email_textColor.value = true
            true
        } else {
            email_textColor.value = false
            mail_vaildation.value = "* 올바르지 않은 형식입니다"
            mEmailFlag.value = 0
            false
        }

    }


    @SuppressLint("SetTextI18n")
    fun checkPassword(): Boolean { //비밀번호 유효성 검사

        val passwordValidation =
            "^(?=.*[a-zA-Z0-9])(?=.*[a-zA-Z!@#$%^&*])(?=.*[0-9!@#$%^&*]).{6,12}$"

        val p = Pattern.matches(passwordValidation, edt_pass.value.toString().trim()) // 서로 패턴이 맞니?
        if (p) {
            //비밀번호 형태가 정상일 경우
            pass_vaildation.value = ""
            pass_textColor.value = true
            Log.d("passVaildation", "비밀번호 패턴 매치 성공")

            mPassFlag.value = 1

            if (edt_pass_confirm.value.toString().isNotEmpty()) {
                mPassConfirmFlag.value =
                    if (edt_pass.value.toString() == edt_pass_confirm.value.toString()) {
                        pass_confirm_vaildation.value = ""
                        1
                    } else {
                        pass_confirm_textColor.value = false
                        pass_confirm_vaildation.value = "* 비밀번호가 일치하지 않습니다."
                        0
                    }
            }
            return true
        } else {
            pass_textColor.value = false
            pass_vaildation.value = "* 비밀번호는 영문, 숫자, 특수문자 중 2가지 이상 포함된 \n6글자 이상 12글자 이하이어야 합니다"
            Log.d("passVaildation", "비밀번호 패턴 매치 실패")

            mPassFlag.value = 0
            return false
        }
    }


    fun checkPasswordConfirm(): Boolean { //비밀번호 재확인 검사
        return if (edt_pass_confirm.value.toString() == edt_pass.value.toString()) { //비밀번호가 일치할 때
            pass_confirm_vaildation.value = ""
            mPassConfirmFlag.value = 1
            true
        } else {
            pass_confirm_textColor.value = false

            pass_confirm_vaildation.value = "* 비밀번호가 일치하지 않습니다."
            mPassConfirmFlag.value = 0
            false
        }
    }

}