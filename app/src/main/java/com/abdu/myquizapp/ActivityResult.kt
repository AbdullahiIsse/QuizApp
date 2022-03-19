package com.abdu.myquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ActivityResult : AppCompatActivity() {


    private var tNAME:TextView? = null
    private var btnFinish:Button? = null
    private var mUserName:String? = null
    private var tvScore:TextView? = null
    private var correctAnswers:Int = 0
    private var totalQuestions:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        tNAME = findViewById(R.id.tv_name)
        tvScore = findViewById(R.id.tv_score)
        btnFinish =  findViewById(R.id.btn_finish)

        mUserName = intent.getStringExtra(Constants.USER_NAME)
        correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS,0)
        totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTION,0)
        tNAME?.text = mUserName
        tvScore?.text = "Your Score is $correctAnswers out of $totalQuestions"




        btnFinish?.setOnClickListener {

            startActivity(Intent(this,MainActivity::class.java))
        }


    }
}