package com.example.appdata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.appdata.Constants.BUNDLE_NEWS_ID
import com.example.appdata.databinding.ActivityUpdateNewsBinding
import com.google.android.material.snackbar.Snackbar

class UpdateNewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateNewsBinding
    private lateinit var newsEntity: NewsEntity
    private var newsId = 0
    private var defaultTitle = ""
    private var defaultDesc = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent.extras?.let {
            newsId = it.getInt(BUNDLE_NEWS_ID)
        }
        binding.apply {
            defaultTitle=newsDB.doa().getNews(newsId).newsTitle
            defaultDesc=newsDB.doa().getNews(newsId).newsDesc
            edtTitle.setText(defaultTitle)
            edtDesc.setText(defaultDesc)
            btnDelete.setOnClickListener {
                newsEntity= NewsEntity(newsId,defaultTitle,defaultDesc)
                newsDB.doa().deleteNews(newsEntity)
                finish()
            }
            btnSave.setOnClickListener {
                val title = edtTitle.text.toString()
                val desc=edtDesc.text.toString()
                if (title.isNotEmpty() || desc.isNotEmpty()){

                    newsEntity= NewsEntity(newsId,title,desc)
                    newsDB.doa().updateNews(newsEntity)
                    finish()
                }
                else{
                    Snackbar.make(it,"Title and Description cannot be Empty",  Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
    private val newsDB : NewsDatabase by lazy {
        Room.databaseBuilder(this, NewsDatabase::class.java, Constants.NEWS_DATABASE)  .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
}