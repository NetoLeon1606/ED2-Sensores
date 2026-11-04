package com.example.sensores

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var btnSensor1: Button
    private lateinit var btnSensor2: Button
    private lateinit var btnSensor3: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        btnSensor1 = findViewById(R.id.btnSensor1)
        btnSensor2 = findViewById(R.id.btnSensor2)
        btnSensor3 = findViewById(R.id.btnSensor3)

        btnSensor1.setOnClickListener {
            val intent = Intent(this, Sensor1Activity::class.java)
            startActivity(intent)
        }

        btnSensor2.setOnClickListener {
            val intent = Intent(this, Sensor2Activity::class.java)
            startActivity(intent)
        }

        btnSensor3.setOnClickListener {
            val intent = Intent(this, Sensor3Activity::class.java)
            startActivity(intent)        }

    }
}