package com.test.alex.facerecognition.photo.detector

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions
import com.test.alex.facerecognition.common.CameraDirection
import com.test.alex.facerecognition.common.Effect
import com.test.alex.facerecognition.photo.graphic.BlurGraphic
import com.test.alex.facerecognition.photo.graphic.BoxFacesGraphic
import com.test.alex.facerecognition.photo.graphic.TrollGraphic
import java.security.InvalidParameterException

class SimpleFaceDetector(private val context: Context, private val effect: Effect) :
    FaceDetector {
    private val options = FirebaseVisionFaceDetectorOptions.Builder()
        .setPerformanceMode(FirebaseVisionFaceDetectorOptions.FAST)
        .build()
    private val detector: FirebaseVisionFaceDetector =
        FirebaseVision.getInstance().getVisionFaceDetector(options)

    override fun process(
        bitmap: Bitmap,
        callback: (Bitmap) -> Unit,
        cameraDirection: CameraDirection
    ) {
        val fireImage = FirebaseVisionImage.fromBitmap(bitmap)
        detector.detectInImage(fireImage)
            .addOnSuccessListener { faces ->
                val bitmapWithFaces = when (effect) {
                    Effect.BLUR -> BlurGraphic(
                        context,
                        cameraDirection
                    ).draw(bitmap, faces)
                    Effect.TROLL -> TrollGraphic(
                        context.resources
                    ).draw(bitmap, faces)
                    Effect.BOX -> BoxFacesGraphic.draw(bitmap, faces)
                    Effect.OUTLINE -> throw InvalidParameterException("Unsupported effect")
                }
                callback(bitmapWithFaces)
            }
            .addOnFailureListener { e -> Log.e(TAG, "$e") }
    }
}

private const val TAG = "SimpleFaceDetector"