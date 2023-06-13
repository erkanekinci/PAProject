package com.example.paproject

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.paproject.databinding.ActivityTestBinding
import com.microsoft.cognitiveservices.speech.CancellationDetails
import com.microsoft.cognitiveservices.speech.CancellationReason
import com.microsoft.cognitiveservices.speech.PronunciationAssessmentConfig
import com.microsoft.cognitiveservices.speech.PropertyId
import com.microsoft.cognitiveservices.speech.ResultReason
import com.microsoft.cognitiveservices.speech.SpeechConfig
import com.microsoft.cognitiveservices.speech.SpeechRecognizer
import com.microsoft.cognitiveservices.speech.SpeechSynthesisCancellationDetails
import com.microsoft.cognitiveservices.speech.SpeechSynthesisResult
import com.microsoft.cognitiveservices.speech.SpeechSynthesizer
import com.microsoft.cognitiveservices.speech.audio.AudioConfig
import java.util.concurrent.TimeUnit


class TestActivity : AppCompatActivity() {
    /*lateinit var mr : MediaRecorder*/
    private  var speechConfig : SpeechConfig?=null
    private var audioConfig : AudioConfig ? = null
    private var speechRecognizer :SpeechRecognizer ? = null
    private var speechSynthesizer : SpeechSynthesizer ? = null
    private lateinit var binding: ActivityTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
      //  var path = "/data/cache/record.wav"

       /* var file = File(cacheDir,"record.wav")
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

        }*/



        binding.button4.setOnClickListener {
            val pronunciationAssessmentConfig =
                PronunciationAssessmentConfig.fromJson("{\"referenceText\":\"My Name is John\",\"gradingSystem\":\"HundredMark\",\"granularity\":\"Phoneme\",\"phonemeAlphabet\":\"IPA\"}")

            speechConfig = SpeechConfig.fromSubscription("4336347ac44143319068962bf19bfe0e","eastus")
            speechConfig?.speechRecognitionLanguage ?:  "en-US"
            audioConfig = AudioConfig.fromDefaultMicrophoneInput()
            val speechRecognizer = SpeechRecognizer(
                speechConfig,
                audioConfig
            )
            pronunciationAssessmentConfig.applyTo(speechRecognizer)
            val future = speechRecognizer.recognizeOnceAsync()
            val speechRecognitionResult = future[30, TimeUnit.SECONDS]




          //  val task = speechRecognizer.recognizeOnceAsync()
           // val speechRecognitionResult = task.get()
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

        binding.button5.setOnClickListener {
            paFunc("ingenious")
        }
        binding.button6.setOnClickListener {
            val text = "action"
            speechConfig = SpeechConfig.fromSubscription("4336347ac44143319068962bf19bfe0e","eastus")
            speechConfig?.speechSynthesisVoiceName = "en-US-JasonNeural";
            val speechSynthesizer = SpeechSynthesizer(speechConfig)
            val speechSynthesisResult: SpeechSynthesisResult? =
                speechSynthesizer.SpeakTextAsync(text)?.get()

            if (speechSynthesisResult != null) {
                if (speechSynthesisResult.reason == ResultReason.SynthesizingAudioCompleted) {
                    Log.v("result","succeeded")
                }else if (speechSynthesisResult.reason == ResultReason.Canceled){
                    val cancellation =
                        SpeechSynthesisCancellationDetails.fromResult(speechSynthesisResult)

                    Log.v("result", cancellation.reason.toString())
                    if(cancellation.reason == CancellationReason.Error){
                        Log.v("Error Code",cancellation.errorCode.toString())
                        Log.v("Error Details", cancellation.errorDetails.toString())

                    }
                }
            }else{
                Log.v("result", "NULLLLLLLLLLLLLL")
            }
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