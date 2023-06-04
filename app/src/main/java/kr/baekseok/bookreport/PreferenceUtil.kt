package kr.baekseok.bookreport

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context : Context) {
    private val preferences : SharedPreferences = context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    fun getInt(key: String, defValue: Int):Int{
        return preferences.getInt(key,defValue)
    }

    fun setInt(key: String, defValue: Int){
        preferences.edit().putInt(key, defValue).apply()
    }
}