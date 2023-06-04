package kr.baekseok.room

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.room.TypeConverter

/**
 * room 에서 이미지 저장은 bitmap 형태로함
 * 서버에서 이미지를 string형으로 받아오니 타입을
 * string -> byteArray -> bitmap으로 변환하여
 * 최종 저장하기 위한 TypeConverter
 */

class RoomTypeConverter {

    @TypeConverter
    fun stringToBitmap(imageString: String): Bitmap? {
        // Base64 디코딩
        val decodedString: ByteArray = Base64.decode(imageString, Base64.DEFAULT)

        // ByteArray를 Bitmap으로 변환
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    }
}