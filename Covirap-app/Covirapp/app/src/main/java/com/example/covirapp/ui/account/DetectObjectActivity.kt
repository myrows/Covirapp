package com.example.covirapp.ui.account

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.covirapp.R
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.automl.FirebaseAutoMLLocalModel
import com.google.firebase.ml.vision.automl.FirebaseAutoMLRemoteModel
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel
import com.google.firebase.ml.vision.label.FirebaseVisionOnDeviceAutoMLImageLabelerOptions
import com.google.firebase.ml.vision.objects.FirebaseVisionObject
import com.google.firebase.ml.vision.objects.FirebaseVisionObjectDetectorOptions
import kotlinx.android.synthetic.main.activity_detect_object.*

class DetectObjectActivity : AppCompatActivity() {

    companion object {
        const val ODT_PERMISSIONS_REQUEST: Int = 1
        const val ODT_REQUEST_IMAGE_CAPTURE = 1
    }

    private lateinit var outputFileUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detect_object)

        /**
         *  Training my own model mascarilla
         */



        captureImageFab.setOnClickListener {
            val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if ( takePhotoIntent.resolveActivity(packageManager) != null ) {
                val values = ContentValues()
                values.put(MediaStore.Images.Media.TITLE, "Covirapp")
                outputFileUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)!!

                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)
                startActivityForResult(takePhotoIntent, ODT_REQUEST_IMAGE_CAPTURE)
            }
        }

        if ( ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED ) {

            captureImageFab.isEnabled = false
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            ODT_PERMISSIONS_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ODT_REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {

            val image = getCapturedImage()

            imageViewMlKit.setImageBitmap(image)
            runObjectDetection(image)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * MLKit Object Detection Function
     */
    private fun runObjectDetection(bitmap: Bitmap) {

        val image = FirebaseVisionImage.fromBitmap(bitmap)

        val options = FirebaseVisionObjectDetectorOptions.Builder()
            .setDetectorMode(FirebaseVisionObjectDetectorOptions.SINGLE_IMAGE_MODE)
            .enableMultipleObjects()
            .enableClassification()
            .build()
        //val detector = FirebaseVision.getInstance().getOnDeviceObjectDetector(options)

        // Specify the name you assigned in the Firebase console.
        val remoteModel = FirebaseAutoMLRemoteModel.Builder("mascarilla_covirapp").build()

        // Local model
        val localModel = FirebaseAutoMLLocalModel.Builder()
            .setAssetFilePath("//android_asset/mascarilla/manifest.json")
            .build()

        // Labeler
        val optionsLabeler = FirebaseVisionOnDeviceAutoMLImageLabelerOptions.Builder(localModel)
            .setConfidenceThreshold(0f)  // Evaluate your model in the Firebase console
            // to determine an appropriate value.
            .build()
        val labeler = FirebaseVision.getInstance().getOnDeviceAutoMLImageLabeler(optionsLabeler)

        val conditions = FirebaseModelDownloadConditions.Builder()
            .requireWifi()
            .build()
        FirebaseModelManager.getInstance().download(remoteModel, conditions)
            .addOnCompleteListener {
                Log.d("Model", "Model downloaded successfully")
            }

        FirebaseModelManager.getInstance().isModelDownloaded(remoteModel)
            .addOnSuccessListener { isDownloaded ->
                val optionsBuilder =
                if (isDownloaded) {
                    FirebaseVisionOnDeviceAutoMLImageLabelerOptions.Builder(remoteModel)
                } else {
                    FirebaseVisionOnDeviceAutoMLImageLabelerOptions.Builder(localModel)
                }
                val options = optionsBuilder.setConfidenceThreshold(0.0f).build()
                val labeler = FirebaseVision.getInstance().getOnDeviceAutoMLImageLabeler(options)

                labeler.processImage(image).addOnSuccessListener {
                    Toast.makeText(baseContext, "La foto ha sido analizada con éxito",
                        Toast.LENGTH_SHORT).show()
                    debugPrint(it)
                }
                    .addOnFailureListener {
                        Toast.makeText(baseContext, "Ha ocurrido un error con el análisis de fotos",
                            Toast.LENGTH_SHORT).show()
                    }
            }

        /*detector.processImage(image)
            .addOnSuccessListener {
                // Task completed successfully
                Toast.makeText(baseContext, "La foto ha sido analizada con éxito",
                    Toast.LENGTH_SHORT).show()
                val drawingView = DrawingView(
                    getApplicationContext(),
                    it
                )
                drawingView.draw(Canvas(bitmap))
                runOnUiThread { imageViewMlKit.setImageBitmap(bitmap) }
            }
            .addOnFailureListener {
                // Task failed with an exception
                Toast.makeText(baseContext, "Ha ocurrido un error con el análisis de fotos",
                    Toast.LENGTH_SHORT).show()
            }
         */
    }

    /**
     * getCapturedImage():
     *     Decodes and center crops the captured image from camera.
     */
    private fun getCapturedImage(): Bitmap {

        val srcImage = FirebaseVisionImage
            .fromFilePath(baseContext, outputFileUri).getBitmap()


        // crop image to match imageView's aspect ratio
        val scaleFactor = Math.min(
            srcImage.width / imageViewMlKit.width.toFloat(),
            srcImage.height / imageViewMlKit.height.toFloat()
        )

        val deltaWidth = (srcImage.width - imageViewMlKit.width * scaleFactor).toInt()
        val deltaHeight = (srcImage.height - imageViewMlKit.height * scaleFactor).toInt()

        val scaledImage = Bitmap.createBitmap(
            srcImage, deltaWidth / 2, deltaHeight / 2,
            srcImage.width - deltaWidth, srcImage.height - deltaHeight
        )
        srcImage.recycle()
        return scaledImage

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            ODT_PERMISSIONS_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    captureImageFab.isEnabled = true
                }
            }
        }
    }

    private fun debugPrint(visionObjects : List<FirebaseVisionImageLabel>) {
        val LOG_MOD = "MLKit-ODT"
        for ((idx, obj) in visionObjects.withIndex()) {

            if ( obj.text == "mascarilla" && obj.confidence > 0.80 ) {
                Toast.makeText(
                    baseContext, "Mascarilla colocada, gracias por colocártela",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    baseContext, "No se ha detectado ninguna mascarilla, por favor, colóquesela",
                    Toast.LENGTH_SHORT
                ).show()
            }
            Log.d(LOG_MOD, "Detected object: ${idx} ")
            Log.d(LOG_MOD, "  Category: ${obj.entityId}")
            Log.d(LOG_MOD, "  text: ${obj.text}")
            Log.d(LOG_MOD, "  confidence: ${obj.confidence}")
        }
    }
}
