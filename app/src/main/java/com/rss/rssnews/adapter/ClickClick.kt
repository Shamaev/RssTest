package com.rss.rssnews.adapter

interface ClickClick {
    fun click(
        title: String,
        url: String,
        description: String,
        category: String,
        date: String
    )
}