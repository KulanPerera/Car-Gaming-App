package com.example.carracegame

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        // Find and set click listeners for each ImageButton
        val btnNavigate1: ImageButton? = findViewById(R.id.imageButton1)
        btnNavigate1?.setOnClickListener {
            startActivity(Intent(this, MainActivity1::class.java))
        }

        val btnNavigate2: ImageButton? = findViewById(R.id.imageButton2)
        btnNavigate2?.setOnClickListener {
            startActivity(Intent(this, MainActivity1::class.java))
        }

        val btnNavigate3: ImageButton? = findViewById(R.id.imageButton5)
        btnNavigate3?.setOnClickListener {
            startActivity(Intent(this, MainActivity1::class.java))
        }

        val btnNavigate4: ImageButton? = findViewById(R.id.imageButton6)
        btnNavigate4?.setOnClickListener {
            startActivity(Intent(this, MainActivity1::class.java))
        }
    }
}
