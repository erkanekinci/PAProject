package com.example.paproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.paproject.databinding.ActivityLessonBinding

class LessonActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLessonBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLessonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val level = intent.getStringExtra("level")

        binding.lesson1Text.setOnClickListener {
            if(level == "A1"){
                val intent = Intent(this,PAActivity::class.java)
                intent.putExtra("level","A1")
                intent.putExtra("lesson","lesson1")
                startActivity(intent)
            }else if (level == "A2"){
                val intent = Intent(this,PAActivity::class.java)
                intent.putExtra("level","A2")
                intent.putExtra("lesson","lesson1")
                startActivity(intent)
            }else if (level == "B1"){
                val intent = Intent(this,PAActivity::class.java)
                intent.putExtra("level","B1")
                intent.putExtra("lesson","lesson1")
                startActivity(intent)
            }else if (level == "B2"){
                val intent = Intent(this,PAActivity::class.java)
                intent.putExtra("level","B2")
                intent.putExtra("lesson","lesson1")
                startActivity(intent)
            }else if (level == "C1"){
                val intent = Intent(this,PAActivity::class.java)
                intent.putExtra("level","C1")
                intent.putExtra("lesson","lesson1")
                startActivity(intent)
            }
        }
    }
}