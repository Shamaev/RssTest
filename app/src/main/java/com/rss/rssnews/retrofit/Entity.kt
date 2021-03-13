package com.rss.rssnews.retrofit

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
data class RssFeed @JvmOverloads constructor(
    @field:Element(name = "channel")
    @param:Element(name = "channel")
    var channel: RssChannel? = null
)

@Root(name = "channel", strict = false)
data class RssChannel @JvmOverloads constructor(
    @field:Element(name = "title")
    @param:Element(name = "title")
    var title: String = "",

    @field:ElementList(name = "item", inline = true, required = false)
    @param:ElementList(name = "item", inline = true, required = false)
    var items: List<RssItem>? = null
)

@Root(name = "item", strict = false)
data class RssItem @JvmOverloads constructor(

    @field:Element(name = "title")
    @param:Element(name = "title")
    var title: String = "",

    @field:Element(name = "link")
    @param:Element(name = "link")
    var link: String = "",

    @field:Element(name = "pubDate")
    @param:Element(name = "pubDate")
    var pubDate: String = "",

    @field:Element(name = "description")
    @param:Element(name = "description")
    var description: String = "",

    @field:ElementList(entry="category", inline = true, required = false)
    @param:ElementList(entry="category", inline = true, required = false)
    var category: List<String>? = null
)