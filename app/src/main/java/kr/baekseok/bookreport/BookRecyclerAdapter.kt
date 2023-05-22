package kr.baekseok.bookreport

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.baekseok.viewmodel.BooksUiState

class BookRecyclerAdapter(var data: BooksUiState, var inflater: LayoutInflater) :
    RecyclerView.Adapter<BookRecyclerAdapter.BookViewHolder>() {

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(
        itemView
    ) {
        var bookTitle: TextView = itemView.findViewById(R.id.tv_book_title1)
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
            is BooksUiState.Error -> "error"
            is BooksUiState.Success -> (data as BooksUiState.Success).booksInfo.items[position].volumeInfo.title
            is BooksUiState.Loading -> "loading"
            is BooksUiState.Initial -> ""
        }
    }
}