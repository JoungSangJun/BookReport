package kr.baekseok.bookreport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

class BookAddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_add)
        supportActionBar?.title = "독서록 쓰기"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_book_report_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            R.id.book_report_post -> {
                Toast.makeText(getApplicationContext(), "Search Action", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item);
        }
    }
}