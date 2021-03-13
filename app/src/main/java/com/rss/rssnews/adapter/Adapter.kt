package com.rss.rssnews.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.rss.rssnews.R
import com.rss.rssnews.retrofit.RssItem


class Adapter: RecyclerView.Adapter<Adapter.ViewHolder>() {

    private var mlist: ArrayList<RssItem> = ArrayList()
    private var mClick : ClickClick? = null

    fun attachCallback(call: ClickClick) {
        this.mClick = call
    }

    fun detachCallback() {
        mClick = null
    }

    fun setData(list: List<RssItem>) {
        mlist.clear()
        mlist.addAll(list)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title : TextView = view.findViewById(R.id.title)
        private val date : TextView = view.findViewById(R.id.date)
        private val img : ImageView = view.findViewById(R.id.img)
        private val progress : ProgressBar = view.findViewById(R.id.progress)

        fun bind(item: RssItem, mClick: ClickClick){

            title.text = item.title
            date.text = item.pubDate

            val str = item.description

            val sbUrl = StringBuffer(str)
            val url = sbUrl.substring(10, sbUrl.indexOf("\" "))

            val sbUDesc = StringBuffer(str)
            val description = sbUDesc.substring(sbUDesc.indexOf("/>\"")).substring(3).dropLast(1)

            Glide
                .with(itemView.context)
                .load(url)
                .into(img)

            itemView.setOnClickListener { view ->
                mClick.click(
                    item.title,
                    url,
                    description,
                    item.category?.joinToString(prefix = "Категории: ")!!,
                    item.pubDate
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.home_item, parent, false))
    }

    override fun getItemCount() = mlist.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mlist[position], mClick!!)
    }
}