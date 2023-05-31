package kr.baekseok.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel : ViewModel() {

    val auth : FirebaseAuth = Firebase.auth
    val id = MutableLiveData<String>("")
    val password = MutableLiveData<String>("")

    var showHomeActivity : MutableLiveData<Boolean> = MutableLiveData(false)
    var showFindPassword : MutableLiveData<Boolean> = MutableLiveData(false)
    var showRegisterActivity : MutableLiveData<Boolean> = MutableLiveData(false)

    fun btnLogin(){
        val email = id.value.toString()
        val pass = password.value.toString()
        if(email.isNotEmpty() && pass.isNotEmpty()){
            auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
                if(it.isSuccessful) {
                    showHomeActivity.value = true
                    Log.d("firebaseSuccess", "성공")
                }else{
                    Log.d("firebaseSuccess", "실패")
                }
            }
        }
    }

}