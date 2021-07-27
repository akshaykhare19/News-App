package com.project.newsly

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.newsly.databinding.ListItemBinding

class NewsListAdapter(
    private val listener: NewsItemClicked,
    private val articles: LiveData<List<Article>>
) : RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val adapterLayout =
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return NewsViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = articles.value!![position]
        holder.bindData(currentItem)

        holder.itemView.setOnClickListener {
            listener.onItemClicked(articles.value!![position])
        }

    }

    override fun getItemCount(): Int {
        return articles.value!!.size
    }

    class NewsViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(article: Article) {
            val titleText = binding.itemText
            titleText.text = article.title

            val authorText = binding.itemAuthor
            authorText.text = article.author

            val image = binding.itemImage
            Glide.with(this.itemView.context).load(article.urlToImage).into(image)


        }

    }

    interface NewsItemClicked {
        fun onItemClicked(item: Article)
    }


}