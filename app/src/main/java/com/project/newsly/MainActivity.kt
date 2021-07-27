package com.project.newsly

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.newsly.databinding.ActivityMainBinding
import com.project.newsly.extensions.Extensions.toast
import com.project.newsly.utils.FirebaseUtils.firebaseAuth
import com.project.newsly.views.CreateAccountActivity

class MainActivity : AppCompatActivity(), NewsListAdapter.NewsItemClicked {

    private lateinit var binding: ActivityMainBinding
    private lateinit var newsViewModel: NewsViewModel
    var data = MutableLiveData<List<Article>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newsList.layoutManager = LinearLayoutManager(this)

        newsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)


        newsViewModel.newsItem.observe(this, {
            data.value = it.articles
            binding.newsList.adapter = NewsListAdapter(this, data)

        })
    }

    override fun onItemClicked(item: Article) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent: CustomTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.action_log_out){
            firebaseAuth.signOut()
            startActivity(Intent(this, CreateAccountActivity::class.java))
            toast("Logged Out")
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}