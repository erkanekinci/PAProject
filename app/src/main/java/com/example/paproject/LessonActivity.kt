package com.example.paproject

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.graphics.drawable.toDrawable
import com.example.paproject.databinding.ActivityLessonBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LessonActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLessonBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private var completedLessonsList : MutableList<String> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLessonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()

        val level = intent.getStringExtra("level")
        binding.lesson2Text.isClickable = false
        binding.lesson3Text.isClickable = false

        db.collection("${firebaseAuth.currentUser?.email}").document("$level").get().addOnSuccessListener {
            if(it.get("completedLessons") != null){
                completedLessonsList= it.get("completedLessons") as MutableList<String>
                Log.v("listtest",completedLessonsList.toString())
                if(completedLessonsList.size == 1){
                    binding.lesson1Text.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.baseline_check_24,0,0)
                    binding.lesson1Text.text = binding.lesson1Text.text.toString() + " Completed"
                    binding.lesson1Text.setTextColor(Color.parseColor("#349A26"))
                    binding.lesson1Text.isClickable = false
                }
                else if (completedLessonsList.size == 2){
                    binding.lesson1Text.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.baseline_check_24,0,0)
                    binding.lesson1Text.text = binding.lesson1Text.text.toString() + " Completed"
                    binding.lesson1Text.setTextColor(Color.parseColor("#349A26"))
                    binding.lesson1Text.isClickable = false
                    binding.lesson2Text.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.baseline_check_24,0,0)
                    binding.lesson2Text.text = binding.lesson2Text.text.toString() + " Completed"
                    binding.lesson2Text.setTextColor(Color.parseColor("#349A26"))
                    binding.lesson2Text.isClickable = false
                }
            }

            if(binding.lesson1Text.text.toString() == "Lesson - 1 Completed"){
                if(binding.lesson2Text.text.toString() == "Lesson - 2 Completed"){
                    binding.lesson3Text.setTextColor(Color.parseColor("#000000"))
                    binding.lesson3Text.isClickable = true
                }else{
                    binding.lesson2Text.setTextColor(Color.parseColor("#000000"))
                    binding.lesson2Text.isClickable = true
                    binding.lesson3Text.isClickable = false
                }
            }

        }




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
        binding.lesson2Text.setOnClickListener {
            if(binding.lesson2Text.isClickable){
                if(level == "A1"){
                    val intent = Intent(this,PAActivity::class.java)
                    intent.putExtra("level","A1")
                    intent.putExtra("lesson","lesson2")
                    startActivity(intent)
                }else if (level == "A2"){
                    val intent = Intent(this,PAActivity::class.java)
                    intent.putExtra("level","A2")
                    intent.putExtra("lesson","lesson2")
                    startActivity(intent)
                }else if (level == "B1"){
                    val intent = Intent(this,PAActivity::class.java)
                    intent.putExtra("level","B1")
                    intent.putExtra("lesson","lesson2")
                    startActivity(intent)
                }else if (level == "B2"){
                    val intent = Intent(this,PAActivity::class.java)
                    intent.putExtra("level","B2")
                    intent.putExtra("lesson","lesson2")
                    startActivity(intent)
                }else if (level == "C1"){
                    val intent = Intent(this,PAActivity::class.java)
                    intent.putExtra("level","C1")
                    intent.putExtra("lesson","lesson2")
                    startActivity(intent)
                }
            }

        }
        binding.lesson3Text.setOnClickListener {

                if(level == "A1"){
                    val intent = Intent(this,PAActivity::class.java)
                    intent.putExtra("level","A1")
                    intent.putExtra("lesson","lesson3")
                    startActivity(intent)
                }else if (level == "A2"){
                    val intent = Intent(this,PAActivity::class.java)
                    intent.putExtra("level","A2")
                    intent.putExtra("lesson","lesson3")
                    startActivity(intent)
                }else if (level == "B1"){
                    val intent = Intent(this,PAActivity::class.java)
                    intent.putExtra("level","B1")
                    intent.putExtra("lesson","lesson3")
                    startActivity(intent)
                }else if (level == "B2"){
                    val intent = Intent(this,PAActivity::class.java)
                    intent.putExtra("level","B2")
                    intent.putExtra("lesson","lesson3")
                    startActivity(intent)
                }else if (level == "C1"){
                    val intent = Intent(this,PAActivity::class.java)
                    intent.putExtra("level","C1")
                    intent.putExtra("lesson","lesson3")
                    startActivity(intent)
                }


        }
    }
}