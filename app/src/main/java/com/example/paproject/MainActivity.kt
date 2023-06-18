package com.example.paproject

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.paproject.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private lateinit var binding: ActivityMainBinding
    private var completedLessonsList : MutableList<String> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()


        db.collection("${firebaseAuth.currentUser?.email}").document("A1").get().addOnSuccessListener {
            if (it.get("completedLessons") != null){
                completedLessonsList = it.get("completedLessons") as MutableList<String>
                if (completedLessonsList.size == 3){
                    binding.a1levelText.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.baseline_check_24,0,0)
                    binding.a1levelText.text = binding.a1levelText.text.toString() + " Completed"
                    binding.a1levelText.setTextColor(Color.parseColor("#349A26"))
                    binding.a1levelText.isClickable = false
            }
            }
        }
        db.collection("${firebaseAuth.currentUser?.email}").document("A2").get().addOnSuccessListener {
            if (it.get("completedLessons") != null){
                completedLessonsList = it.get("completedLessons") as MutableList<String>
                if (completedLessonsList.size == 3){
                    binding.a2levelText.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.baseline_check_24,0,0)
                    binding.a2levelText.text = binding.a2levelText.text.toString() + " Completed"
                    binding.a2levelText.setTextColor(Color.parseColor("#349A26"))
                    binding.a2levelText.isClickable = false
            }
            }
        }
        db.collection("${firebaseAuth.currentUser?.email}").document("B1").get().addOnSuccessListener {
            if (it.get("completedLessons") != null){
                completedLessonsList = it.get("completedLessons") as MutableList<String>
                if (completedLessonsList.size == 3){
                    binding.b1levelText.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.baseline_check_24,0,0)
                    binding.b1levelText.text = binding.b1levelText.text.toString() + " Completed"
                    binding.b1levelText.setTextColor(Color.parseColor("#349A26"))
                    binding.b1levelText.isClickable = false
            }

            }
        }
        db.collection("${firebaseAuth.currentUser?.email}").document("B2").get().addOnSuccessListener {
            if (it.get("completedLessons") != null){
                completedLessonsList = it.get("completedLessons") as MutableList<String>
                if (completedLessonsList.size == 3){
                    binding.b2levelText.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.baseline_check_24,0,0)
                    binding.b2levelText.text = binding.b2levelText.text.toString() + " Completed"
                    binding.b2levelText.setTextColor(Color.parseColor("#349A26"))
                    binding.b2levelText.isClickable = false
            }

            }
        }
        db.collection("${firebaseAuth.currentUser?.email}").document("C1").get().addOnSuccessListener {
            if (it.get("completedLessons") != null){
                completedLessonsList = it.get("completedLessons") as MutableList<String>
                if (completedLessonsList.size == 3){
                    binding.c1levelText.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.baseline_check_24,0,0)
                    binding.c1levelText.text = binding.c1levelText.text.toString() + " Completed"
                    binding.c1levelText.setTextColor(Color.parseColor("#349A26"))
                    binding.c1levelText.isClickable = false
            }

            }
        }

        binding.a1levelText.setOnClickListener {
            val intent = Intent(this,LessonActivity::class.java)
            intent.putExtra("level","A1")
            startActivity(intent)
        }
        binding.a2levelText.setOnClickListener {
            val intent = Intent(this,LessonActivity::class.java)
            intent.putExtra("level","A2")
            startActivity(intent)
        }
        binding.b1levelText.setOnClickListener {
            val intent = Intent(this,LessonActivity::class.java)
            intent.putExtra("level","B1")
            startActivity(intent)
        }
        binding.b2levelText.setOnClickListener {
            val intent = Intent(this,LessonActivity::class.java)
            intent.putExtra("level","B2")
            startActivity(intent)
        }
        binding.c1levelText.setOnClickListener {
            val intent = Intent(this,LessonActivity::class.java)
            intent.putExtra("level","C1")
            startActivity(intent)
        }


    }

    }


