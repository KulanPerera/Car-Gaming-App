package com.example.carracegame

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import kotlin.math.abs

@SuppressLint("ViewConstructor")
class GameView(context: Context, private val gameTask: GameTask) : View(context) {
    private val myPaint = Paint()
    private var speed = 1
    private var time = 0
    private var score = 0
    private var myCarPosition = 0 // Start in the left lane (0 or 1)
    private val lanePositions = arrayOf(0, 1, 2, 3) // Available lane positions (0, 1)
    private val otherCars = ArrayList<Car>()
    private var isPaused = false

    private var playerCarDrawable: Drawable
    private var otherCarDrawable: Drawable

    init {
        // Load car images from drawable resources
        playerCarDrawable = ContextCompat.getDrawable(context, R.drawable.cars6)!!
        otherCarDrawable = ContextCompat.getDrawable(context, R.drawable.cars4)!!
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        val viewWidth = width
        val viewHeight = height

        if (!isPaused) {
            if (time % 700 < 10 + speed) {
                val lane = lanePositions.random()
                val car = Car(lane, time)
                otherCars.add(car)
            }
            time += 10 + speed
        }

        val laneWidth = viewWidth / lanePositions.size
        val carWidth = laneWidth / 2 // Adjust the car width based on lane width
        val carHeight = carWidth + 120

        // Draw player's car
        val playerCarLeft = myCarPosition * laneWidth + laneWidth / 4
        val playerCarTop = viewHeight - 2 - carHeight
        val playerCarRight = playerCarLeft + carWidth
        val playerCarBottom = viewHeight - 2
        playerCarDrawable.setBounds(playerCarLeft, playerCarTop, playerCarRight, playerCarBottom)
        playerCarDrawable.draw(canvas)

        // Draw other cars
        myPaint.color = Color.RED
        val iterator = otherCars.iterator()
        while (iterator.hasNext()) {
            val car = iterator.next()
            val carX = car.lane * laneWidth + laneWidth / 4
            val carY = time - car.startTime

            val otherCarTop = carY - carHeight
            val otherCarRight = carX + carWidth
            otherCarDrawable.setBounds(carX, otherCarTop, otherCarRight, carY)

            // Draw other cars only when not paused
            if (!isPaused) {
                otherCarDrawable.draw(canvas)
            }

            // Check collision with player's car
            if (!isPaused && car.lane == myCarPosition && carY > viewHeight - 2 - carHeight && carY < viewHeight - 2) {
                gameTask.closeGame(score)
            }

            // Remove cars that have passed the view height
            if (!isPaused && carY > viewHeight + carHeight) {
                iterator.remove()
                score++
                speed = 1 + abs(score / 8)
            }
        }

        // Draw score and speed
        myPaint.color = Color.WHITE
        myPaint.textSize = 60f
        canvas.drawText("Score: $score", 80f, 80f, myPaint)
        canvas.drawText("Speed: $speed", 380f, 80f, myPaint)

        invalidate() // Request a redraw
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isPaused) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val x = event.x
                    val laneWidth = width / lanePositions.size

                    // Calculate the lane index based on the touch position
                    val clickedLane = (x / laneWidth).toInt()

                    if (clickedLane != myCarPosition) {
                        myCarPosition = clickedLane // Move player's car to the clicked lane
                        invalidate() // Redraw after car position change
                    }
                }
            }
        }
        return true
    }

    fun pauseGame() {
        isPaused = true
    }

    fun resumeGame() {
        isPaused = false
        invalidate() // Request a redraw to resume the game
    }

    data class Car(val lane: Int, val startTime: Int)
}
