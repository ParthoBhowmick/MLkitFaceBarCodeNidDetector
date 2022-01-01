package com.google.mlkit.vision.demo.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.google.mlkit.vision.demo.R

class ImagePreviewActivity : AppCompatActivity() {

    private lateinit var capturedImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_preview)

        capturedImage = findViewById(R.id.capturedImage)

    }
}