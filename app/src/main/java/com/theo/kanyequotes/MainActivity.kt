package com.theo.kanyequotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var appDB : appDatabase

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appDB = appDatabase.getDatabase(this)

        val textView : TextView = findViewById(R.id.textView)

        val api = Retrofit.Builder()
            .baseUrl("https://api.kanye.rest")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(kanyeApi::class.java)


        val btn : Button = findViewById(R.id.getquote)                // get the quote
        btn.setOnClickListener {

            GlobalScope.launch(Dispatchers.Main) {
                val response = api.getQoute().awaitResponse()
                if (response.isSuccessful){
                    textView.text = response.body()?.quote
                }
            }

        }


        val getsaved : Button = findViewById(R.id.saved)                // go to saved page
        getsaved.setOnClickListener{
            val intent = Intent(this, saved::class.java)
            startActivity(intent)
        }


        val save : Button = findViewById(R.id.save)
        save.setOnClickListener{
            if (textView.text.isNotEmpty()){
                val Quote : quote = quote(textView.text as String)

                GlobalScope.launch {
                    appDB.quotesdao().insert(Quote)
                }

                Toast.makeText(this, "SAVED!", Toast.LENGTH_SHORT).show()

            }
        }

    }
}