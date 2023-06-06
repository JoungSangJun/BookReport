package kr.baekseok.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [BookReportData::class], version = 2)
abstract class BookReportDatabase : RoomDatabase() {
    abstract fun bookReportDao(): BookReportDao

    companion object {
        @Volatile
        private var Instance: BookReportDatabase? = null

        fun getDatabase(context: Context): BookReportDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, BookReportDatabase::class.java, "bookReport_database")
                    .fallbackToDestructiveMigration().build().also { Instance = it }
            }
        }
    }
}