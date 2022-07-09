package com.example.googlelensclone.Barcode

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.text.Format

class BarcodeAnalyzer:ImageAnalysis.Analyzer {
    public var aa = ""
    val scanner = BarcodeScanning.getClient()
    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        Log.d("BARCODE","image analyzed")

        imageProxy.image?.let {
            val inputImage = InputImage.fromMediaImage(
                it,
                imageProxy.imageInfo.rotationDegrees
            )
            scanner.process(inputImage)
                .addOnSuccessListener { codes ->
                    codes.forEach{ barcode ->
                        Log.d("BARCODE","""" ,
                            Format= ${barcode.format}
                            Value = ${barcode.rawValue}
                        """.trimIndent()
                        )
                        aa+=barcode.rawValue
                    }


                }
                .addOnFailureListener{ ex->
                    Log.e("BARCODE", "Detection failed", ex)
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }?:imageProxy.close()

    }


}