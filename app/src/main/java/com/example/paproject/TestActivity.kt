package com.example.paproject

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.paproject.databinding.ActivityTestBinding
import com.microsoft.cognitiveservices.speech.CancellationDetails
import com.microsoft.cognitiveservices.speech.CancellationReason
import com.microsoft.cognitiveservices.speech.PronunciationAssessmentConfig
import com.microsoft.cognitiveservices.speech.PropertyId
import com.microsoft.cognitiveservices.speech.ResultReason
import com.microsoft.cognitiveservices.speech.SpeechConfig
import com.microsoft.cognitiveservices.speech.SpeechRecognitionResult
import com.microsoft.cognitiveservices.speech.SpeechRecognizer
import com.microsoft.cognitiveservices.speech.audio.AudioConfig
import kotlinx.coroutines.awaitAll
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit


class TestActivity : AppCompatActivity() {
    lateinit var mr : MediaRecorder
    private  var speechConfig : SpeechConfig?=null
    private var audioConfig : AudioConfig ? = null
    private var speechRecognizer :SpeechRecognizer ? = null

    private lateinit var binding: ActivityTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
      //  var path = "/data/cache/record.wav"

        var file = File(cacheDir,"record.mp3")
        mr = MediaRecorder()

        binding.button.isEnabled = false
        binding.button2.isEnabled = false


        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE),111)

        }
        binding.button.isEnabled = true
        binding.button.setOnClickListener {
            mr.setAudioSource(MediaRecorder.AudioSource.MIC)
            mr.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            mr.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            mr.setOutputFile(FileOutputStream(file).fd)
            mr.prepare()
            mr.start()
            binding.button2.isEnabled = true
            binding.button.isEnabled = false
        }

        binding.button2.setOnClickListener {
            mr.stop()
            binding.button.isEnabled=true
            binding.button2.isEnabled = false
        }

        binding.button3.setOnClickListener {
            var mp = MediaPlayer()
            mp.setDataSource(FileInputStream(file).fd)
            mp.prepare()
            mp.start()

        }



        binding.button4.setOnClickListener {
            val pronunciationAssessmentConfig =
                PronunciationAssessmentConfig.fromJson("{\"referenceText\":\"good morning\",\"gradingSystem\":\"HundredMark\",\"granularity\":\"Phoneme\",\"phonemeAlphabet\":\"IPA\"}")

            speechConfig = SpeechConfig.fromSubscription("3d6de84a449040a3b6c228536f8c72c8","eastus")
            speechConfig?.speechRecognitionLanguage ?:  "en-US"
            audioConfig = AudioConfig.fromDefaultMicrophoneInput()

            val speechRecognizer = SpeechRecognizer(
                speechConfig,
                audioConfig
            )
            pronunciationAssessmentConfig.applyTo(speechRecognizer);
            val task = speechRecognizer.recognizeOnceAsync()
            val speechRecognitionResult = task.get()
            if(speechRecognitionResult.reason == ResultReason.Canceled){
                val cancellation = CancellationDetails.fromResult(speechRecognitionResult)
                Log.v("text1", cancellation.toString())
                if(cancellation.reason == CancellationReason.Error){
                    Log.v("text2", cancellation.errorCode.toString())
                    Log.v("text3", cancellation.errorDetails.toString())



                }
            }
            Log.v("text4", speechRecognitionResult.toString())
            val pronunciationAssessmentResultJson =
                speechRecognitionResult.properties.getProperty(PropertyId.SpeechServiceResponse_JsonResult)

            Log.v("text5",pronunciationAssessmentResultJson)

            speechRecognizer.close();
            speechConfig?.close();
            audioConfig?.close();
            pronunciationAssessmentConfig.close();
            speechRecognitionResult.close();
        }




    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }




}