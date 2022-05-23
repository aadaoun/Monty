package com.develop.monty

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class Show : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show)
        val name: TextView = findViewById(R.id.rep_name) as TextView
        val date: TextView = findViewById(R.id.rep_date) as TextView
        val count: TextView = findViewById(R.id.rep_count) as TextView
        val avatar: ImageView = findViewById(R.id.avatar_image) as ImageView
        name.setText(intent.getStringExtra("name").toString())
        date.setText(intent.getStringExtra("date").toString().substring(0,10))
        count.setText(intent.getStringExtra("count").toString())
        Glide.with(baseContext).load(intent.getStringExtra("url").toString()).circleCrop()
            .into(avatar);


    }
    }