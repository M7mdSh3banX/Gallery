package com.shaban.myapplication.data.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
import com.shaban.myapplication.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Locale

class ImageRepositoryImpl : ImageRepository {
    // DCIM directory where downloaded images are stored with new directory called MyImages.
    private val imagesLocation = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
        "MyImages"
    )

    private val imageUrls = listOf(
        "https://i.pinimg.com/564x/cd/39/14/cd391457fd5f6c128e99c7e921606e11.jpg",
        "https://i.pinimg.com/564x/72/c7/a0/72c7a0a131f6ed77bfc7bf203ed47bf8.jpg",
        "https://i.pinimg.com/564x/a9/35/f2/a935f29af6e55366a0901ee9ab347fe4.jpg",
        "https://i.pinimg.com/564x/76/1b/b0/761bb0d24d7e98763a536709b3eff5bc.jpg",
        "https://i.pinimg.com/564x/9f/b3/fc/9fb3fcf85f22f1ebb40061039a7bf3bb.jpg",
        "https://i.pinimg.com/564x/2f/54/bf/2f54bfc97381b9bab0534abf480a3d90.jpg",
        "https://i.pinimg.com/564x/30/9f/ec/309fecf07c6707f3041164171a3510b3.jpg",
        "https://www.svgrepo.com/show/530618/hotel.svg",
        "https://www.svgrepo.com/show/530636/suv.svg",
        "https://www.svgrepo.com/show/530627/sunbathing.svg",
    )

    override suspend fun loadImagesFromInternet(): List<String> {
        return imageUrls
    }

    override suspend fun loadImagesFromDisk(): List<String> {
        // If images directory doesn't exist or is empty, download images to disk.
        if (!imagesLocation.exists() || imagesLocation.listFiles().isNullOrEmpty()) {
            withContext(Dispatchers.IO) {
                downloadImagesToDisk()
            }
        }
        // Return list of image files in the images directory and return their paths from the disk.
        val imageFiles = imagesLocation.listFiles()?.toList() ?: emptyList()
        return imageFiles.map { it.absolutePath }
    }

    override fun loadImagesFromResources(): List<Int> {
        val imageIds = listOf(
            R.drawable.a1,
            R.drawable.a2,
            R.drawable.a3,
            R.drawable.a4,
            R.drawable.a5,
            R.drawable.a6,
            R.drawable.a7,
            R.drawable.a8,
            R.drawable.a9,
            R.drawable.a10,
        )
        return imageIds
    }

    private suspend fun downloadImagesToDisk() = coroutineScope {
        // Create images directory if it doesn't exist.
        if (!imagesLocation.exists()) {
            imagesLocation.mkdirs()
        }
        // Date format for generating unique image file names.
        val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.getDefault())

        val jobs = imageUrls.map { urlString ->
            async(Dispatchers.IO) {
                try {
                    // Open connection to the image URL and download image bitmap.
                    val url = URL(urlString)
                    val connection = url.openConnection()
                    connection.connectTimeout = 3000
                    connection.readTimeout = 3000
                    val inputStream = connection.getInputStream()

                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    // Save downloaded bitmap to a file on disk.
                    saveBitmapToFile(bitmap, imagesLocation, dateFormat)

                    inputStream.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("SHABAN_REPO", "Failed to save image $urlString: $e")
                }
            }
        }
        // Using await for completion of all image download jobs.
        jobs.awaitAll()
    }

    /**
     * Saves a bitmap image to a file on disk.
     *
     * @param bitmap Bitmap image to save.
     * @param dir Directory where image file will be saved.
     * @param dateFormat SimpleDateFormat for generating unique file names.
     */
    private fun saveBitmapToFile(bitmap: Bitmap, dir: File, dateFormat: SimpleDateFormat) {
        val currentTime = System.currentTimeMillis()
        val imageName = "${dateFormat.format(currentTime)}_image.jpeg"
        val file = File(dir, imageName)

        // Write bitmap to file output stream as JPEG format.
        val out = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        out.flush()
        out.close()
        Log.e("SHABAN_REPO", "File Saved In DCIM/MyImages/$imageName")
    }
}
