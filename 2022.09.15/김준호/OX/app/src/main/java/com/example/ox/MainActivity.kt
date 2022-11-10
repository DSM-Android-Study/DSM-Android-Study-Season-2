package com.example.ox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageButton
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var clickCount = 0
        var answer = 0

        val answerList = listOf<Int>(1,1,0,1,0,0,0,0,1,1,1,1)

        val btnR: ImageButton = findViewById(R.id.right)
        val btnL: ImageButton = findViewById(R.id.left)
        val tv: TextView = findViewById(R.id.tv)

        btnL.setOnClickListener {
            if(clickCount < 12){
                if(answerList[clickCount] == 0){
                    answer++
                }
                clickCount++
                tv.text = clickCount.toString()

            }

        }

        btnR.setOnClickListener {














            if(clickCount < 12){
                if(answerList[clickCount] == 1){
                    answer++
                }

                clickCount++
                tv.text = clickCount.toString()

            }
        }

        tv.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(clickCount == 12){
                    val intent = Intent(this@MainActivity,AnswerActivity::class.java)
                    intent.putExtra("answer",answer)
                    startActivity(intent)
                }
            }

            override fun afterTextChanged(p0: Editable?) {}

        })
    }
}