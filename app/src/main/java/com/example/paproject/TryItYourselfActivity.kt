package com.example.paproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paproject.databinding.ActivityHomePageBinding
import com.example.paproject.databinding.ActivityTryItYourselfBinding
import org.json.JSONObject

class TryItYourselfActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTryItYourselfBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTryItYourselfBinding.inflate(layoutInflater)
        setContentView(binding.root)




        binding.listenButton.setOnClickListener {
            if (binding.wordText.text.toString().isEmpty()){
                Toast.makeText(this,"Please Enter Something",Toast.LENGTH_SHORT).show()
            }else{
                listenFunction(binding.wordText.text.toString())
            }
        }

        binding.paButton.setOnClickListener {
            if(binding.wordText.text.toString().isEmpty()){
                Toast.makeText(this,"Please Enter Something",Toast.LENGTH_SHORT).show()
            }else{
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

                binding.showDetailsButton.setOnClickListener{
                    showRecyclerViewDialog(this,jsonResponseString)
                }

                binding.nextButton.setOnClickListener {
                    val intent = Intent(this,HomePageActivity::class.java)
                    intent.putExtra("detect","fromTryIt")
                    startActivity(intent)
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