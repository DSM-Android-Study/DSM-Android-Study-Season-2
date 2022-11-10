package com.example.ox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.widget.TextView

class AnswerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

        val tv: TextView = findViewById(R.id.tv_answer)

        tv.text = intent.getIntExtra("answer",0).toString()
    }
}
