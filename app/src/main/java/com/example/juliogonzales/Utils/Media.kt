package com.example.juliogonzales.Utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class Media(internal var context: Context, var activity: Activity)  {

    private val SELECT_PICTURE = 2
    private val TAKE_PICTURE = 0
    private val TAKE_VIDEO = 3
    internal val REQUEST_TAKE_PHOTO = 1


    //Audi
    internal var mFileName: String? = null

    private var mRecorder: MediaRecorder? = null
    private var mPlayer: MediaPlayer? = null


    var mStartRecording = true
    var mStartPlaying = true




    fun openCameraPhoto(): Uri {

        var photo: Uri? = null
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file = File(Environment.getExternalStorageDirectory(), "" + System.currentTimeMillis().toString() + ".jpg")
        val outputFileUri = Uri.fromFile(file)
        photo = outputFileUri
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photo)
        activity.startActivityForResult(intent, TAKE_PICTURE)

        return photo

    }

    fun chooseImage() {

        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        activity.startActivityForResult(intent, SELECT_PICTURE)

    }


    fun startRecording() {
        mRecorder = MediaRecorder()
        mRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mRecorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mRecorder?.setOutputFile(mFileName)
        mRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        mRecorder?.prepare()
        mRecorder?.start()
    }

    fun stopRecording() {
        mRecorder?.stop()
        mRecorder?.release()
        mRecorder = null
    }


    fun startPlaying() {
        mPlayer = MediaPlayer()
        mPlayer?.setDataSource(mFileName)
        mPlayer?.prepare()
        mPlayer?.start()
    }

    fun stopPlaying() {
        mPlayer?.release()
        mPlayer = null
    }

    @Throws(IOException::class)
    fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName, /* prefix */
            ".jpg", /* suffix */
            storageDir      /* directory */
        )

        // Save a file: path for use with ACTION_VIEW intents
        val mCurrentPhotoPath = image.absolutePath
        return image
    }



    fun bitmapToByte(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val byteArray = stream.toByteArray()
        return byteArray
    }

    fun bitMapArrayByte(uri: Uri): ByteArray {

        var newMoment = uri as Uri
        val file = File(newMoment.path)
        val size = file.length().toInt()

        val bytes = ByteArray(size)
        try {
            val buf = BufferedInputStream(FileInputStream(file))
            buf.read(bytes, 0, bytes.size)
            buf.close()
        } catch (e: FileNotFoundException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        return bytes
    }

}