package com.theo.kanyequotes

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*

class quotesAdapter (
    var quotes : MutableList<quote>
    ) : RecyclerView.Adapter<quotesAdapter.quotesHolder>() {

    inner class quotesHolder(itemview : View) : RecyclerView.ViewHolder(itemview)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): quotesHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.quote_item, parent, false)
        return quotesHolder(view)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(holder: quotesHolder, position: Int) {

        var appDB = appDatabase.getDatabase(holder.itemView.context) // to initialize the db

        holder.itemView.findViewById<TextView>(R.id.savedQuote).apply {
            text = quotes[position].quote
        }

        holder.itemView.findViewById<ImageButton>(R.id.Delete).apply {
            setOnClickListener {

                GlobalScope.launch {
                    appDB.quotesdao().delete(quotes[position]) // needs to run inside a coroutine
                    quotes.removeAt(position) // this needs to be in here so that it doesn't run befor the db command
                }

                notifyDataSetChanged()
                Toast.makeText(holder.itemView.context, "DELETED!", Toast.LENGTH_SHORT).show()
            }
        }

        holder.itemView.findViewById<ImageButton>(R.id.share).apply {
            setOnClickListener {

                val msg = quotes[position].quote

                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT, msg)
                intent.type = "text/plain"

                context.startActivity(intent)


            }
        }
    }

    override fun getItemCount(): Int {
        return quotes.size
    }
}