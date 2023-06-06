package kr.baekseok.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.baekseok.addreport.ReportAddActivity
import kr.baekseok.bookreport.R
import kr.baekseok.room.BookReportData

class ReportRecyclerAdapter(
    private val context: Context,
    var data: List<BookReportData>,
    var inflater: LayoutInflater
) : RecyclerView.Adapter<ReportRecyclerAdapter.ReportViewHolder>() {

    class ReportDiffCallback(
        private val oldList: List<BookReportData>,
        private val newList: List<BookReportData>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem == newItem
        }
    }

    inner class ReportViewHolder(itemView: View) : RecyclerView.ViewHolder(
        itemView
    ) {
        var reportTitle: TextView = itemView.findViewById(R.id.tv_report_title1)
        var reportImg: ImageView = itemView.findViewById(R.id.iv_report_image1)

        init {
            itemView.setOnClickListener {
                val position: Int = bindingAdapterPosition
                val intent = Intent(context, ReportAddActivity::class.java)
                intent.putExtra("book_report_data", data[position])
                startActivity(context, intent, null)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val view = inflater.inflate(R.layout.report_item, parent, false)
        return ReportViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.reportTitle.text = data[position].bookTitle
        if (data[position].bookImg == null) {
            holder.reportImg.setImageResource(R.drawable.image_no)
        } else {
            Glide.with(context).load(data[position].bookImg!!.replace("http", "https"))
                .into(holder.reportImg)
        }
    }
}

// recycler view item 간격 조절을 위한 클래
class ItemSpacingDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.left = spacing
        outRect.right = spacing
        outRect.top = spacing
        outRect.bottom = spacing
    }
}