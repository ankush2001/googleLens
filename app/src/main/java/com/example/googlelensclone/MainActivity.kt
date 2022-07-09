package com.example.googlelensclone

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.example.googlelensclone.Barcode.BarcodeActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        @JvmStatic val PHOTO_REQ_CODE = 1234
        @JvmStatic val EXTRA_DATA = "data"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        takePicture.setOnClickListener{
            val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePhotoIntent, PHOTO_REQ_CODE)
        }
        cameraActivity.setOnClickListener{
            val camaeraActivity = Intent(this,CameraActivity::class.java)
            startActivity(camaeraActivity)
        }
        barcodeActivity.setOnClickListener{
            startActivity(Intent(this,BarcodeActivity::class.java))
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == PHOTO_REQ_CODE){
            val bitmap = data?.extras?.get(EXTRA_DATA) as Bitmap
            imageThumb.setImageBitmap(bitmap)
            return
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}