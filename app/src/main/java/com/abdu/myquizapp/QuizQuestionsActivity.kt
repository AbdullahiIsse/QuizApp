package com.abdu.myquizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import kotlin.math.log


class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {


    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOptions:Int = 0
    private var mUserName:String? = null
    private var mCorrectAnswers:Int = 0

    private var seeIfOptionsIsSelected: Int = 0

    private var progressBar:ProgressBar? = null
    private var tvProgress : TextView? = null
    private var tvQuestion : TextView? = null
    private var ivImage : ImageView? = null

    private var tvOptionOne:TextView? = null
    private var tvOptionTwo:TextView? = null
    private var tvOptionThree:TextView? = null
    private var tvOptionFour:TextView? = null
    private var btnSubmit:Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        mUserName = intent.getStringExtra(Constants.USER_NAME)
        progressBar = findViewById(R.id.progressBar)
        tvProgress = findViewById(R.id.tv_progress)
        tvQuestion = findViewById(R.id.tv_question)
        ivImage = findViewById(R.id.ivImage)
        tvOptionOne = findViewById(R.id.tv_option_one)
        tvOptionTwo = findViewById(R.id.tv_option_two)
        tvOptionThree = findViewById(R.id.tv_option_three)
        tvOptionFour = findViewById(R.id.tv_option_four)
        btnSubmit = findViewById(R.id.btn_submit)
        tvOptionOne?.setOnClickListener(this)
        tvOptionTwo?.setOnClickListener(this)
        tvOptionThree?.setOnClickListener(this)
        tvOptionFour?.setOnClickListener(this)
        btnSubmit?.setOnClickListener(this)
        mQuestionsList = Constants.getQuestions()
        setQuestion()



    }

    private fun setQuestion() {

        Log.i("QuestionsList size is", "${mQuestionsList?.size}")

          defaultOptionsView()


        val question: Question = mQuestionsList!![mCurrentPosition - 1]
        ivImage?.setImageResource(question.image)
        progressBar?.progress = mCurrentPosition
        tvProgress?.text = "$mCurrentPosition/${progressBar?.max}"
        tvQuestion?.text = question.question
        tvOptionOne?.text = question.optionOne
        tvOptionTwo?.text = question.optionTwo
        tvOptionThree?.text = question.optionThree
        tvOptionFour?.text = question.optionFour

        if (mCurrentPosition == mQuestionsList!!.size){

            btnSubmit?.text = "FINISH"
        } else{
            btnSubmit?.text = "SUBMIT"
        }

    }

    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()

        tvOptionOne?.let { options.add(0,it) }

        tvOptionTwo?.let { options.add(1,it) }

        tvOptionThree?.let { options.add(2,it) }

        tvOptionFour?.let { options.add(3,it) }


        for (option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this,R.drawable.default_option_border_bg)

        }


    }

    private fun selectedOptionView(tv:TextView, selectedOptionNum: Int){

        defaultOptionsView()

        mSelectedOptions = selectedOptionNum

        seeIfOptionsIsSelected = selectedOptionNum

        tv.setTextColor((Color.parseColor("#363A43")))

        tv.setTypeface(tv.typeface,Typeface.BOLD)

        tv.background = ContextCompat.getDrawable(this,R.drawable.selecetd_option_border_bg)





    }



    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.tv_option_one -> {
                tvOptionOne?.let {
                    selectedOptionView(it,1)


                }
            }
            R.id.tv_option_two -> {
                tvOptionTwo?.let {
                    selectedOptionView(it,2)

                }

            }
            R.id.tv_option_three -> {
                tvOptionThree?.let {
                    selectedOptionView(it,3)

                }
            }
            R.id.tv_option_four -> {
                tvOptionFour?.let {
                    selectedOptionView(it,4)

                }

            }
            R.id.btn_submit -> {


                btnSubmit?.let {

                   if (seeIfOptionsIsSelected == 0){

                        it.isClickable = false
                       Toast.makeText(this,"PLEASE SELECT AN ANSWER", Toast.LENGTH_SHORT).show()
                    } else{



                    tvOptionOne?.isClickable = true
                    tvOptionTwo?.isClickable = true
                    tvOptionThree?.isClickable = true
                    tvOptionFour?.isClickable = true



                    if (mSelectedOptions == 0) {
                        mCurrentPosition++



                        when {
                            mCurrentPosition <= mQuestionsList!!.size -> {
                                setQuestion()
                                seeIfOptionsIsSelected = 0
                            }
                            else -> {
                                val intent = Intent(this, ActivityResult::class.java)

                                intent.putExtra(Constants.USER_NAME, mUserName)
                                intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                                intent.putExtra(Constants.TOTAL_QUESTION, mQuestionsList?.size)
                                startActivity(intent)
                                finish()
                            }
                        }

                    } else {


                        val question = mQuestionsList?.get(mCurrentPosition - 1)


                        if (question!!.correctAnswer != mSelectedOptions) {

                            answerView(mSelectedOptions, R.drawable.wrong_option_border_bg)

                        } else {

                            mCorrectAnswers++
                        }

                        answerView(question.correctAnswer, R.drawable.correct_option_border_bg)
                        tvOptionOne?.isClickable = false
                        tvOptionTwo?.isClickable = false
                        tvOptionThree?.isClickable = false
                        tvOptionFour?.isClickable = false


                        if (mCurrentPosition == mQuestionsList!!.size) {
                            btnSubmit?.text = "FINSIH"

                        } else {
                            btnSubmit?.text = "GO TO NEXT QUESTION"

                        }

                        Log.e("test","the selected amount is $seeIfOptionsIsSelected")
                        mSelectedOptions = 0

                    }


                    }
                    it.isClickable = true



            }

            }
        }

    }

    private fun answerView(answer:Int,drawableView: Int){
        if(answer == 1){
            tvOptionOne?.background = ContextCompat.getDrawable(this,drawableView)
        } else if (answer == 2){
            tvOptionTwo?.background = ContextCompat.getDrawable(this,drawableView)
        } else if(answer == 3){
            tvOptionThree?.background = ContextCompat.getDrawable(this,drawableView)
        } else if (answer == 4){
            tvOptionFour?.background = ContextCompat.getDrawable(this,drawableView)
        }




    }
}