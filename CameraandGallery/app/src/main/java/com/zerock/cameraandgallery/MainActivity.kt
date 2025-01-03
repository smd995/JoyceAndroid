// MainActivity.kt
package com.zerock.cameraandgallery

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.zerock.cameraandgallery.R

class MainActivity : AppCompatActivity() {

    private lateinit var profileImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        profileImageView = findViewById(R.id.profileImageView)
        val galleryButton: Button = findViewById(R.id.galleryButton)
        val cameraButton: Button = findViewById(R.id.cameraButton)

        // 갤러리에서 이미지 가져오기
        val galleryLauncher = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let {
                profileImageView.setImageURI(uri)
            }
        }

        galleryButton.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

        // 카메라로 사진 찍기
        val cameraLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val photo = result.data?.extras?.get("data") as Bitmap
                profileImageView.setImageBitmap(photo)
            }
        }

        cameraButton.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraLauncher.launch(cameraIntent)
        }
    }
}
