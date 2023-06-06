package kr.baekseok.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.baekseok.bookreport.R
import kr.baekseok.room.BookReportData

class ReportRecyclerAdapter(
    private val context: Context,
    var data: List<BookReportData>,
    var inflater: LayoutInflater
) : RecyclerView.Adapter<ReportRecyclerAdapter.ReportViewHolder>() {

    inner class ReportViewHolder(itemView: View) : RecyclerView.ViewHolder(
        itemView
    ) {
        var reportTitle: TextView = itemView.findViewById(R.id.tv_report_title1)
        var reportImg: ImageView = itemView.findViewById(R.id.iv_report_image1)


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
    }
}