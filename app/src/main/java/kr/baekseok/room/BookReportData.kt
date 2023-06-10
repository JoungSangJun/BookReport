package kr.baekseok.room

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "BookReport"
)
data class BookReportData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "book_img")
    val bookImg: String? = null,
    @ColumnInfo(name = "book_title")
    val bookTitle: String = "",
    @ColumnInfo(name = "book_content")
    val bookContent: String = ""
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(bookImg)
        parcel.writeString(bookTitle)
        parcel.writeString(bookContent)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BookReportData> {
        override fun createFromParcel(parcel: Parcel): BookReportData {
            return BookReportData(parcel)
        }

        override fun newArray(size: Int): Array<BookReportData?> {
            return arrayOfNulls(size)
        }
    }
}