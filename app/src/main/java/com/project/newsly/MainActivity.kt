package com.project.newsly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.newsly.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var newsViewModel: NewsViewModel
    var data = MutableLiveData<List<Article>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newsList.layoutManager = LinearLayoutManager(this)

        newsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

//        newsViewModel.status.observe(this, Observer {
//            binding.status.text = it
//        })


        newsViewModel.newsItem.observe(this, Observer {
            data.value = it.articles
            binding.newsList.adapter = NewsListAdapter(this, data)

        })
    }

}