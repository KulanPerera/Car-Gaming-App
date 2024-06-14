package com.example.carracegame

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity2 : AppCompatActivity(), GameTask {
    private lateinit var rootLayout: LinearLayout
    private lateinit var startBtn: Button
    private lateinit var pauseBtn: Button
    private lateinit var endBtn: Button
    private lateinit var mGameView: GameView
    private lateinit var score: TextView
    private lateinit var highScore: TextView
    private lateinit var sharedPreferences: SharedPreferences

    private var isGamePaused = false

    companion object {
        const val PREF_HIGH_SCORE = "pref_high_score"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startBtn = findViewById(R.id.startBtn)
        pauseBtn = findViewById(R.id.pauseBtn)
        endBtn = findViewById(R.id.endBtn) // Find the end button
        rootLayout = findViewById(R.id.rootLayout)
        score = findViewById(R.id.score)
        highScore = findViewById(R.id.highScore)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("GamePreferences", Context.MODE_PRIVATE)

        // Load the current high score from SharedPreferences
        val currentHighScore = sharedPreferences.getInt(PREF_HIGH_SCORE, 0)
        highScore.text = "High Score: $currentHighScore"

        // Set click listener for start button
        startBtn.setOnClickListener {
            // Initialize game view and start the game
            initializeGame()
        }

        // Set click listener for exit button
        endBtn.setOnClickListener {
            // Finish the current activity to exit the game
            finish()
        }

        // Set click listener for pause button
        pauseBtn.setOnClickListener {
            if (isGamePaused) {
                resumeGame()
            } else {
                pauseGame()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun closeGame(mScore: Int) {
        // Update score TextView
        score.text = "Score: $mScore"

        // Show game controls
        rootLayout.removeView(mGameView)
        startBtn.visibility = View.VISIBLE
        score.visibility = View.VISIBLE
        highScore.visibility = View.VISIBLE
        pauseBtn.visibility = View.GONE

        // Update high score if the current score is higher
        val currentHighScore = sharedPreferences.getInt(PREF_HIGH_SCORE, 0)
        if (mScore > currentHighScore) {
            sharedPreferences.edit().putInt(PREF_HIGH_SCORE, mScore).apply()
            highScore.text = "High Score: $mScore"
        }

        // Show the end button after the game ends
        endBtn.visibility = View.VISIBLE
    }

    @SuppressLint("SetTextI18n")
    private fun initializeGame() {
        // Create and add game view to layout
        mGameView = GameView(this, this)
        mGameView.setBackgroundResource(R.drawable.road1)
        rootLayout.addView(mGameView)

        // Hide start button and score/high score TextViews
        startBtn.visibility = View.GONE
        score.visibility = View.GONE
        highScore.visibility = View.GONE
        pauseBtn.visibility = View.VISIBLE

        // Hide the end button when the game starts
        endBtn.visibility = View.GONE

        // Set initial score text
        score.text = "Score: 0"
    }

    @SuppressLint("SetTextI18n")
    private fun pauseGame() {
        isGamePaused = true
        mGameView.pauseGame()
        pauseBtn.text = "Resume"
    }

    @SuppressLint("SetTextI18n")
    private fun resumeGame() {
        isGamePaused = false
        mGameView.resumeGame()
        pauseBtn.text = "Pause"
    }
}