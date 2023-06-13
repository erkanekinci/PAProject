package com.example.paproject

import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.paproject.databinding.ActivityMainBinding
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.a1levelText.setOnClickListener {
            val intent = Intent(this,LessonActivity::class.java)
            intent.putExtra("level","A1")
            startActivity(intent)
        }
        binding.a2levelText.setOnClickListener {
            val intent = Intent(this,LessonActivity::class.java)
            intent.putExtra("level","A2")
        }
        binding.b1levelText.setOnClickListener {
            val intent = Intent(this,LessonActivity::class.java)
            intent.putExtra("level","B1")
        }
        binding.b2levelText.setOnClickListener {
            val intent = Intent(this,LessonActivity::class.java)
            intent.putExtra("level","B2")
        }
        binding.c1levelText.setOnClickListener {
            val intent = Intent(this,LessonActivity::class.java)
            intent.putExtra("level","C1")
        }

    }

    }


