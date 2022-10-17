package com.example.dquran2.ui.page

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dquran2.R
import com.example.dquran2.data.Juz
import com.example.dquran2.data.Page
import com.google.android.material.card.MaterialCardView


class PageAdapter(val pageList: List<Page>): RecyclerView.Adapter<PageAdapter.PageViewHolder>() {

    var itemClickListener : ((Page) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_page,parent,false)
        return PageViewHolder(view)
    }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        val page = pageList[position]
        holder.bindView(page)
        holder.card.setOnClickListener {
            itemClickListener?.invoke(page)
        }
    }

    override fun getItemCount(): Int {
        return pageList.size
    }


    class PageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textPageName = itemView.findViewById<TextView>(R.id.txt_page_name)
        val textPageStart = itemView.findViewById<TextView>(R.id.txt_page_start)
        val textPageNumber = itemView.findViewById<TextView>(R.id.txt_page_number)
        val card = itemView.findViewById<MaterialCardView>(R.id.card_page)

        fun bindView(page: Page){
            textPageName.text = "Halaman ${page.pageNumber}"
            textPageStart.text = "${page.surahName} : ${page.ayahNumber}"
            textPageNumber.text = page.pageNumber.toString()
        }

    }
}