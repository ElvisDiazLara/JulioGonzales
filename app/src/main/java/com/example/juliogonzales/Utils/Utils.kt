package com.example.juliogonzales.Utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.andrognito.flashbar.Flashbar
import com.example.juliogonzales.R
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class Utils (internal var context: Context, internal var activity: Activity) : AppCompatActivity() {

    private val SELECT_PICTURE = 2
    private val TAKE_PICTURE = 0
    private val TAKE_VIDEO = 3
    internal val REQUEST_TAKE_PHOTO = 1


    fun checkAndRequestPermissions(): Boolean {
        val permissionRead = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
        val permissionWrite = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val permissionLocation = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
        val permissionLatitud = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        val permissionCamara = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
        val listPermissionsNeeded : ArrayList<String> = ArrayList()

        if(permissionLocation!= PackageManager.PERMISSION_GRANTED){
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if(permissionLatitud != PackageManager.PERMISSION_GRANTED){
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (permissionRead != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (permissionWrite != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (permissionCamara != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA)
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this.activity, listPermissionsNeeded.toTypedArray(), 1)
            return false
        }
        return true
    }

    fun validateEmail(email: String): Boolean {
        var valid = false
        val emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        val emailCheck = email.trim()
        valid = emailCheck.matches(emailPattern.toRegex())

        return valid
    }


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

    fun dispatchTakePictureIntent() {
        var imageUri: Uri? = null
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(context.packageManager) != null) {
            // Create the File where the photo should go
            var photoFile: File? = null
            if (imageUri != null) {
                imageUri = null
            }
            try {
                photoFile = createImageFile()


            } catch (ex: IOException) {
                // Error occurred while creating the File
                //println("Error on what?   " + ex.localizedMessage)

            }

            // Continue only if the File was successfully created
            if (photoFile != null) {

                imageUri = Uri.parse(photoFile.absolutePath)
                val photoURI = FileProvider.getUriForFile(context,
                    "com.brounie.misonzas.fileprovider",
                    photoFile)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)


                activity.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
            }
        }
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

    fun chooseImage() {

        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        activity.startActivityForResult(intent, SELECT_PICTURE)

    }

    fun showSnackbar(){
        Flashbar.Builder(this)
            .gravity(Flashbar.Gravity.TOP)
            .duration(800)
            .backgroundColor(ContextCompat.getColor(this, R.color.success))
            .message("Se ha subido la foto correctamente")
            .build()
    }

    fun showSnackbarFailed(){
        Flashbar.Builder(this)
            .gravity(Flashbar.Gravity.TOP)
            .duration(800)
            .backgroundColor(ContextCompat.getColor(this, R.color.warning))
            .message("Ocurrio un problmea, vuelve a intentarlo por favor.")
            .build()
    }
}