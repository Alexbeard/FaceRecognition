package com.test.alex.facerecognition.photo.detector

import android.graphics.Bitmap
import com.test.alex.facerecognition.common.CameraDirection

interface FaceDetector {
    fun process(
        bitmap: Bitmap,
        callback: (Bitmap) -> Unit,
        cameraDirection: CameraDirection
    )
}