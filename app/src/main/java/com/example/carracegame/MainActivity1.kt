package com.example.carracegame

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class MainActivity1 : AppCompatActivity() {

    private val delayMillis = 2000L // 10 seconds delay

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)

        // Use a Handler to delay the transition to MainActivity2
        Handler().postDelayed({
            // Start MainActivity2 after the delay
            startActivity(Intent(this, MainActivity2::class.java))
            finish() // Finish MainActivity1
        }, delayMillis)
    }
}
