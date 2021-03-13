package com.rss.rssnews.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rss.rssnews.retrofit.RssItem
import com.rss.rssnews.retrofit.RssService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel(serverApi : RssService) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val serc = serverApi

    private val _dataServer = MutableLiveData<ArrayList<RssItem>>()
    val dataServer: LiveData<ArrayList<RssItem>>
        get() = _dataServer

    init {
         fetchData()
    }

    fun fetchData() {
        compositeDisposable.add(
            serc.getRss()
                .flatMap {
                    val list = ArrayList<RssItem>()
                    it.channel!!.items!!.forEach {
                        list.add(it)
                    }
                    Single.just(list)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _dataServer.value = it
                }, {
                    Log.i("dfdfdfd", "error - ${it.message}")
                }
                )
        )
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

}