package com.project.newsly

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NewsViewModel: ViewModel() {

    var _newsItem = MutableLiveData<News>()

    val newsItem: LiveData<News> = _newsItem

    private val _status = MutableLiveData<String>()

    val status: LiveData<String> = _status


    init {
//        observeNewsArticle()
        getNewsArticle()
    }

    private fun getNewsArticle() {
        viewModelScope.launch {
            try {
                _newsItem.value = NewsService.newsInstance.getNews("in", API_KEY)
                _status.value = "Response: ${newsItem.value.toString()}"
                Log.d("STATUS CHECK", "Response successful")
            } catch (e: Exception) {
                _status.value = "Error in network call: ${e.message}"
                Log.d("STATUS CHECK", "Response unsuccessful: ${e.message}")
            }

        }
    }

}