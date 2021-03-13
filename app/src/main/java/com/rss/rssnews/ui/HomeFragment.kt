package com.rss.rssnews.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.rss.rssnews.Model
import com.rss.rssnews.R
import com.rss.rssnews.adapter.Adapter
import com.rss.rssnews.adapter.ClickClick
import com.rss.rssnews.retrofit.RssItem
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), ClickClick {

    private val adapter : Adapter by inject()

    private val galleryViewModel by viewModel<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        galleryViewModel.dataServer.observe(viewLifecycleOwner, Observer {
            progress.visibility = View.GONE
            initAdapter(it)

            swipeRefresh.isRefreshing = false
        })

        swipeRefresh.setOnRefreshListener {
            galleryViewModel.fetchData()
        }
    }

    fun initAdapter(list: List<RssItem>) {
        recycler_home.setHasFixedSize(true)
        recycler_home.layoutManager = GridLayoutManager(context, 1)
        adapter.attachCallback(this)
        adapter.setData(list)
        recycler_home.adapter = adapter
    }

    override fun click(
        title: String,
        url: String,
        description: String,
        category: String,
        date: String
    ) {
        Model.apply {
            titleText = title
            urlText = url
            descriptionText = description
            categoryText = category
            dateText = date
        }

        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, DetailFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroy() {
        adapter.detachCallback()
        super.onDestroy()
    }

}