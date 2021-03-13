package com.rss.rssnews.retrofit

import io.reactivex.Single
import retrofit2.http.GET

interface RssService {
    @GET("rss.xml")
    fun getRss(): Single<RssFeed>
}