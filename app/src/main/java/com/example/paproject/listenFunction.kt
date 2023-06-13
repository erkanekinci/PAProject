package com.example.paproject

import android.util.Log
import com.microsoft.cognitiveservices.speech.CancellationReason
import com.microsoft.cognitiveservices.speech.ResultReason
import com.microsoft.cognitiveservices.speech.SpeechConfig
import com.microsoft.cognitiveservices.speech.SpeechSynthesisCancellationDetails
import com.microsoft.cognitiveservices.speech.SpeechSynthesisResult
import com.microsoft.cognitiveservices.speech.SpeechSynthesizer

fun listenFunction(word:String){
    var speechConfig = SpeechConfig.fromSubscription("4336347ac44143319068962bf19bfe0e", "eastus")
    speechConfig?.speechSynthesisVoiceName = "en-US-JasonNeural";
    val speechSynthesizer = SpeechSynthesizer(speechConfig)
    val speechSynthesisResult: SpeechSynthesisResult? =
        speechSynthesizer.SpeakTextAsync(word).get()

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
