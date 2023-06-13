package com.example.paproject

import android.util.Log
import com.microsoft.cognitiveservices.speech.CancellationDetails
import com.microsoft.cognitiveservices.speech.CancellationReason
import com.microsoft.cognitiveservices.speech.PronunciationAssessmentConfig
import com.microsoft.cognitiveservices.speech.PropertyId
import com.microsoft.cognitiveservices.speech.ResultReason
import com.microsoft.cognitiveservices.speech.SpeechConfig
import com.microsoft.cognitiveservices.speech.SpeechRecognizer
import com.microsoft.cognitiveservices.speech.audio.AudioConfig
import java.util.concurrent.TimeUnit

fun paFunc(word:String) : String{
    val pronunciationAssessmentConfig =
        PronunciationAssessmentConfig.fromJson("{\"referenceText\":\"$word\",\"gradingSystem\":\"HundredMark\",\"granularity\":\"Phoneme\",\"phonemeAlphabet\":\"IPA\"}")

    var speechConfig = SpeechConfig.fromSubscription("4336347ac44143319068962bf19bfe0e", "eastus")
    speechConfig?.speechRecognitionLanguage ?:  "en-US"
    var audioConfig = AudioConfig.fromDefaultMicrophoneInput()
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


    speechRecognizer.close()
    speechConfig?.close()
    audioConfig?.close()
    pronunciationAssessmentConfig.close()
    speechRecognitionResult.close()

    return pronunciationAssessmentResultJson
}