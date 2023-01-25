package com.theo.kanyequotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.theo.kanyequotes.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class saved : AppCompatActivity() {

    private lateinit var appDB : appDatabase // create up here so we can use from all functions
    private var savedquotes = mutableListOf<quote>() // same

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved)

        appDB = appDatabase.getDatabase(this) // initialize

        //ads

        val adRequest = AdRequest.Builder().build()
        val adview : AdView = findViewById(R.id.savedAds)

        adview.loadAd(adRequest)


        val adapter = quotesAdapter(savedquotes)

        val rv : RecyclerView = findViewById(R.id.recyclerView)

        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)

        getData(adapter) // get data from db

    }

    private suspend fun update(adapter: quotesAdapter){
        withContext(Dispatchers.Main){
            adapter.notifyDataSetChanged() // not working
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun getData(adapter: quotesAdapter){

        var Squotes : MutableList<quote>

        GlobalScope.launch {
            Squotes = appDB.quotesdao().getAll()

            for (q in Squotes){
                savedquotes.add(q)
            }
            update(adapter)
        }

    }
}