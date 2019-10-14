package com.test.alex.facerecognition.photo.graphic

import android.graphics.Bitmap
import com.google.firebase.ml.vision.face.FirebaseVisionFace

interface Graphic {
    fun draw(bitmap: Bitmap, faces: List<FirebaseVisionFace>): Bitmap
}