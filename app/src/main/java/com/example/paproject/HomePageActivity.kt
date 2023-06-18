package com.example.paproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.paproject.databinding.ActivityHomePageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomePageActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomePageBinding
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var db : FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        if (intent.getStringExtra("detect") == "fromTryIt"){
            binding.welcomeText.visibility = View.INVISIBLE
        }else{
            db.collection("${firebaseAuth.currentUser?.email}").document("username").get().addOnSuccessListener {
                val username = it.get("username") as String
                binding.welcomeText.text = "Welcome, $username"
            }
        }





        binding.exercisesText.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        binding.tryItText.setOnClickListener {
            val intent = Intent(this,TryItYourselfActivity::class.java)
            startActivity(intent)
        }
    }
}