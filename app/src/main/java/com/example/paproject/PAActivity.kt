package com.example.paproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.paproject.databinding.ActivityPaactivityBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.json.JSONObject

class PAActivity : AppCompatActivity() {
    private lateinit var db : FirebaseFirestore
    private lateinit var binding: ActivityPaactivityBinding
    private  var wordList : MutableList<String> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val level = intent.getStringExtra("level")
        var lessonIndex = 0
        val lesson = intent.getStringExtra("lesson")
        db = FirebaseFirestore.getInstance()
        db.collection("$level").document("$lesson").get().addOnSuccessListener{document->
            wordList.add(document.get("word1").toString())
            wordList.add(document.get("word2").toString())
            wordList.add(document.get("word3").toString())
            wordList.add(document.get("word4").toString())
            wordList.add(document.get("word5").toString())
            wordList.add(document.get("word6").toString())


            binding.wordText.text = wordList[lessonIndex]

        }.addOnFailureListener{
            Toast.makeText(this,it.localizedMessage,Toast.LENGTH_SHORT).show()
        }

        binding.listenButton.setOnClickListener{
            listenFunction(binding.wordText.text.toString())
        }

        binding.paButton.setOnClickListener{
            val test = paFunc(binding.wordText.text.toString())
            val jsonObject = JSONObject(test)
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
                Toast.makeText(this,"Good Job!",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"Try Again",Toast.LENGTH_LONG).show()
                binding.retryButton.visibility = View.VISIBLE
                binding.paButton.visibility = View.INVISIBLE
            }

            binding.retryButton.setOnClickListener {
                binding.scoresLayout.visibility = View.INVISIBLE
                binding.paButton.visibility = View.VISIBLE
                it.visibility = View.INVISIBLE
            }
            binding.nextButton.setOnClickListener {
                binding.scoresLayout.visibility = View.INVISIBLE
                binding.paButton.visibility = View.VISIBLE


                it.visibility = View.INVISIBLE
                lessonIndex += 1
                if(lessonIndex == 6){
                    //To do lesson bitti
                }
                binding.wordText.text = wordList[lessonIndex]
                binding.paButton.visibility = View.VISIBLE
            }
        }
    }


    private fun scoreChecker(accScore:Int, compScore : Int, fluencyScore :Int, pronScore : Int) : Boolean{
        return accScore > 75 && compScore > 75 && fluencyScore > 75 && pronScore > 75
    }
}