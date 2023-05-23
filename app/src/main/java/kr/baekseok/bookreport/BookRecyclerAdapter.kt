package kr.baekseok.bookreport

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kr.baekseok.data.VolumeInfo
import kr.baekseok.viewmodel.BooksUiState

class BookRecyclerAdapter(
    private val context: Context,
    var data: BooksUiState,
    var inflater: LayoutInflater
) :
    RecyclerView.Adapter<BookRecyclerAdapter.BookViewHolder>() {

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(
        itemView
    ) {
        var bookTitle: TextView = itemView.findViewById(R.id.tv_book_title1)
        var bookImg: ImageView = itemView.findViewById(R.id.iv_book_image1)

        init {
            itemView.setOnClickListener {
                val position: Int = bindingAdapterPosition
                val bookInfo: VolumeInfo = when (data) {
                    is BooksUiState.Success -> (data as BooksUiState.Success).booksInfo.items[position]
                    else -> {
                        Toast.makeText(context, "존재하지 않는 책을 선택하셨습니다.", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }
                }
                val intent = Intent(context, ReportAddActivity::class.java)
                val json = Json.encodeToString(bookInfo)
                intent.putExtra("key", json)
                startActivity(context, intent, null)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = inflater.inflate(R.layout.book_item, parent, false)
        return BookViewHolder(view)
    }

    override fun getItemCount(): Int {
        return when (data) {
            is BooksUiState.Success -> (data as BooksUiState.Success).booksInfo.items.size
            else -> 1
        }
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bookTitle.text = when (data) {
            is BooksUiState.Error -> ""
            is BooksUiState.Success -> (data as BooksUiState.Success).booksInfo.items[position].volumeInfo.title
            is BooksUiState.Loading -> "loading"
            is BooksUiState.Initial -> ""
        }

        val bookImageLink: String? = when (data) {
            is BooksUiState.Success -> (data as BooksUiState.Success).booksInfo.items[position].volumeInfo.imageLinks?.smallThumbnail?.replace(
                "http",
                "https"
            )
            is BooksUiState.Initial -> "initial"
            else -> null
        }

        if (bookImageLink != null) {
            Glide.with(context).load(bookImageLink).into(holder.bookImg)
        } else {
            holder.bookImg.setImageResource(R.drawable.image_no)
        }
    }
}

