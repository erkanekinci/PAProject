package com.example.paproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.example.paproject.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import org.w3c.dom.Text

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var db : FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        binding.loginRedirectText.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
        binding.loginButton.setOnClickListener(View.OnClickListener {
            val completedLessons = arrayListOf<String>()
            val password = binding.password.text.toString()
            val email = binding.email.text.toString()
            val username = binding.username.text.toString()
            val passwordrepeat = binding.repeatpassword.text.toString()
            if(email.isNotEmpty()  && password.isNotEmpty() && passwordrepeat.isNotEmpty() && username.isNotEmpty()){
                if (password == passwordrepeat){
                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                        if(it.isSuccessful){
                            db.collection("${firebaseAuth.currentUser?.email}").document("username").set(
                                mapOf("username" to username)
                            )
                            db.collection("${firebaseAuth.currentUser?.email}").document("A1").set(
                                mapOf("completedLessons" to completedLessons)
                            )
                            db.collection("${firebaseAuth.currentUser?.email}").document("A2").set(
                                mapOf("completedLessons" to completedLessons)
                            )
                            db.collection("${firebaseAuth.currentUser?.email}").document("B1").set(
                                mapOf("completedLessons" to completedLessons)
                            )
                            db.collection("${firebaseAuth.currentUser?.email}").document("B2").set(
                                mapOf("completedLessons" to completedLessons)
                            )
                            db.collection("${firebaseAuth.currentUser?.email}").document("C1").set(
                                mapOf("completedLessons" to completedLessons)
                            )
                            val intent = Intent(this,HomePageActivity::class.java)
                            startActivity(intent)

                        }else{
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                        }
                    }

                }else{
                    Toast.makeText(this, "Passwords are not matching", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Empty fields are not allowed", Toast.LENGTH_SHORT).show()
            }
        })
    }
}