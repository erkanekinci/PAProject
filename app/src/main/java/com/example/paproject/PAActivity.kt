package com.example.paproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paproject.databinding.ActivityPaactivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.json.JSONObject

class PAActivity : AppCompatActivity() {
    private lateinit var db : FirebaseFirestore
    private lateinit var binding: ActivityPaactivityBinding
    private  var wordList : MutableList<String> = mutableListOf()
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        val level = intent.getStringExtra("level")
        var lessonIndex = 0
        val lesson = intent.getStringExtra("lesson")
        db = FirebaseFirestore.getInstance()
        db.collection("$level").document("$lesson").get().addOnSuccessListener{document->
            /*wordList.add(document.get("word1").toString())
            wordList.add(document.get("word2").toString())
            wordList.add(document.get("word3").toString())
            wordList.add(document.get("word4").toString())
            wordList.add(document.get("word5").toString())
            wordList.add(document.get("word6").toString())*/
            wordList = document.get("wordList") as MutableList<String>

            binding.wordText.text = wordList[lessonIndex]

        }.addOnFailureListener{
            Toast.makeText(this,it.localizedMessage,Toast.LENGTH_SHORT).show()
        }

        binding.listenButton.setOnClickListener{
            listenFunction(binding.wordText.text.toString())
        }

        binding.paButton.setOnClickListener{
            val jsonResponseString = paFunc(binding.wordText.text.toString())
            val jsonObject = JSONObject(jsonResponseString)
            val nBest = jsonObject.getJSONArray("NBest")
            val scores = nBest.getJSONObject(0).getJSONObject("PronunciationAssessment")
            val accuracyScore = scores.getDouble("AccuracyScore")
            val pronScore = scores.getDouble("PronScore")
            val completenessScore = scores.getDouble("CompletenessScore")
            val fluencyScore = scores.getDouble("FluencyScore")

            binding.accScoreText.text = "Accuracy score: " + accuracyScore
            binding.completenessScoreText.text = "Completeness Score: " + completenessScore
            binding.fluecyScoreText.text = "Fluency Score: "  + fluencyScore
            binding.pronScoreText.text = "Pronunciation Score: " + pronScore
            binding.scoresLayout.visibility = View.VISIBLE


            if(scoreChecker(accuracyScore.toInt(), completenessScore.toInt(),fluencyScore.toInt(), pronScore.toInt())){
                binding.nextButton.visibility = View.VISIBLE
                binding.paButton.visibility = View.INVISIBLE
                binding.showDetailsButton.visibility = View.VISIBLE
                Toast.makeText(this,"Good Job!",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"Try Again",Toast.LENGTH_LONG).show()
                binding.retryButton.visibility = View.VISIBLE
                binding.paButton.visibility = View.INVISIBLE
                binding.showDetailsButton.visibility = View.VISIBLE

            }

            binding.retryButton.setOnClickListener {
                binding.scoresLayout.visibility = View.INVISIBLE
                binding.paButton.visibility = View.VISIBLE
                binding.showDetailsButton.visibility = View.INVISIBLE
                it.visibility = View.INVISIBLE
            }
            binding.showDetailsButton.setOnClickListener {
                // To Do ekrana popup şeklinde detayları göster!!!
                showRecyclerViewDialog(this,jsonResponseString)
            }
            binding.nextButton.setOnClickListener {
                lessonIndex += 1
                if(lessonIndex == wordList.size){
                    Toast.makeText(this,"Well Done You Have Completed The Lesson",Toast.LENGTH_LONG).show()

                    db.collection("${firebaseAuth.currentUser?.email}").document("$level").update("completedLessons",FieldValue.arrayUnion("$lesson"))
                    if(lesson == "lesson3"){
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                    }else{
                        val intent = Intent(this,LessonActivity::class.java)
                        intent.putExtra("level",level)
                        startActivity(intent)
                    }




                }else{
                    binding.scoresLayout.visibility = View.INVISIBLE
                    binding.paButton.visibility = View.VISIBLE
                    binding.showDetailsButton.visibility = View.INVISIBLE



                    it.visibility = View.INVISIBLE

                    binding.wordText.text = wordList[lessonIndex]
                    binding.paButton.visibility = View.VISIBLE
                }

            }
        }
    }


    private fun scoreChecker(accScore:Int, compScore : Int, fluencyScore :Int, pronScore : Int) : Boolean{
        return accScore > 75 && compScore > 75 && fluencyScore > 75 && pronScore > 75
    }

    private fun parseJsonToPhonemeItems(jsonString: String): List<PhonemeItem> {
        val phonemeItems = mutableListOf<PhonemeItem>()

        val jsonObject = JSONObject(jsonString)
        val nBestArray = jsonObject.getJSONArray("NBest")

        // Iterate over the NBest array
        for (i in 0 until nBestArray.length()) {
            val nBestObject = nBestArray.getJSONObject(i)
            val wordsArray = nBestObject.getJSONArray("Words")

            // Iterate over the Words array
            for (j in 0 until wordsArray.length()) {
                val wordObject = wordsArray.getJSONObject(j)
                val phonemesArray = wordObject.getJSONArray("Phonemes")

                // Iterate over the Phonemes array
                for (k in 0 until phonemesArray.length()) {
                    val phonemeObject = phonemesArray.getJSONObject(k)
                    val phoneme = phonemeObject.getString("Phoneme")
                    val accuracyScore = phonemeObject
                        .getJSONObject("PronunciationAssessment")
                        .getDouble("AccuracyScore")
                    val phonemeItem = PhonemeItem(phoneme, accuracyScore)
                    phonemeItems.add(phonemeItem)
                }
            }
        }
        Log.v("PhoneItemsList", phonemeItems.toString())
        return phonemeItems

    }

    private fun showRecyclerViewDialog(context: Context, jsonString: String) {
        val phonemeItems = parseJsonToPhonemeItems(jsonString)

        val dialogView = LayoutInflater.from(context).inflate(R.layout.details_dialog, null)

        val recyclerView = dialogView.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = PhonemeAdapter(phonemeItems)
        recyclerView.adapter = adapter

        // Create and show the AlertDialog
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setView(dialogView)
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
        val button = dialogView.findViewById<Button>(R.id.dialogCloseButton)
        button.setOnClickListener {
            alertDialog.cancel()
        }
    }
}