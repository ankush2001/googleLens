package com.example.googlelensclone

import android.app.AlertDialog
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.googlelensclone.Barcode.BarcodeAnalyzer
import kotlinx.android.synthetic.main.activity_barcode.*

abstract class BaseLensActivity :AppCompatActivity(){

    companion object{
        @JvmStatic
        var CAMERA_PERM_CODE = 422
    }

    abstract val imageAnalyzer: ImageAnalysis.Analyzer

    protected lateinit var imageAnalysis: ImageAnalysis
    protected fun askCameraPermission(){
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.CAMERA,android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
            CAMERA_PERM_CODE
        )
    }

    protected fun startCamera(){
        val cameraProvider = ProcessCameraProvider.getInstance(this)
        cameraProvider.addListener(
            Runnable {
                val cameraAp = cameraProvider.get()

                val preview =  Preview.Builder()
                    .build().also { it.setSurfaceProvider(barcodePreview.surfaceProvider) }


                imageAnalysis = ImageAnalysis.Builder().build()

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                try {
                    cameraAp.unbindAll()
                    cameraAp.bindToLifecycle(this, cameraSelector, preview, imageAnalysis)
                }catch (ex:Exception){

                }
            },
            ContextCompat.getMainExecutor(this)
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA_PERM_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera()
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Permission Error")
                    .setMessage("Camera Permission not provided")
                    .setPositiveButton("OK") { _, _ -> finish() }
                    .setCancelable(false)
                    .show()
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}