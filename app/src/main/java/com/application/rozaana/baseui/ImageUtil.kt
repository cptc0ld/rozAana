package com.application.rozaana.baseui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.application.rozaana.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class ImageUtil {
    companion object {
        fun getImageUrl(url: String): String {
            var imageUrl = url
            if (!imageUrl.startsWith("http")) {
                if (url.startsWith("//")) {
                    imageUrl = "http:$imageUrl"
                } else {
                    imageUrl = "http://$imageUrl"
                }
            }
            return imageUrl
        }

        fun setImage(
            context: Context,
            imageView: ImageView,
            imageUrl: String,
            placeholder: Int?
        ) {
            Glide.with(context).load(imageUrl).apply(getRequestOptions(context, placeholder))
                .into(imageView)

            Log.d("glide", imageUrl)
        }

        fun setImage(context: Context, res: Int, imageView: ImageView) {
            Glide.with(context).load(res).into(imageView)
        }

        fun setImage(context: Context, url: String, imageView: ImageView) {
            Glide.with(context).load(url).into(imageView)
        }

        fun getRequestOptions(context: Context, placeholder: Int?): RequestOptions {
            val requestOptions = RequestOptions()
            if (placeholder != null) requestOptions.placeholder(placeholder)
            else requestOptions.placeholder(
                ColorDrawable(
                    ContextCompat.getColor(
                        context, R.color.black
                    )
                )
            )

            return requestOptions
        }

        fun setImage(
            context: Context,
            imageView: ImageView?,
            imageUrl: String,
            placeholder: Int?,
            imageRequestListener: ImageRequestListener
        ) {
            if (imageView != null) {
                Glide.with(context).load(imageUrl).apply(getRequestOptions(context, placeholder))
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any,
                            target: Target<Drawable>,
                            isFirstResource: Boolean
                        ): Boolean {
                            imageRequestListener.onLoadFailed()
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable,
                            model: Any,
                            target: Target<Drawable>,
                            dataSource: DataSource,
                            isFirstResource: Boolean
                        ): Boolean {
                            imageRequestListener.onResourceReady()
                            return false
                        }
                    }).into(imageView)
            }

            Log.d("glide", imageUrl)
        }

        fun setServerImage(
            context: Context,
            imageView: ImageView,
            imageUrl: String,
            placeholder: Int?
        ) {
            val url = getImageUrl(imageUrl)
            setImage(context, imageView, url, placeholder)
        }

        fun getCompressedImage(imageUri: String, context: Context): String {
            val filePath = getRealPathFromURI(imageUri, context)
            var scaledBitmap: Bitmap? = null
            val options = BitmapFactory.Options()
            // by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
            // you try the use the bitmap here, you will get null.
            options.inJustDecodeBounds = true
            var bmp = BitmapFactory.decodeFile(filePath, options)
            var actualHeight = options.outHeight
            var actualWidth = options.outWidth
            // max Height and width values of the compressed image is taken as 816x612
            var maxHeight = 816.0f
            var maxWidth = 612.0f
            var imgRatio = (actualWidth / actualHeight).toFloat()
            var maxRatio = maxWidth / maxHeight
            // width and height values are set maintaining the aspect ratio of the image
            if (actualHeight > maxHeight || actualWidth > maxWidth) {
                if (imgRatio < maxRatio) {
                    imgRatio = maxHeight / actualHeight
                    actualWidth = (imgRatio * actualWidth).toInt()
                    actualHeight = maxHeight.toInt()
                } else if (imgRatio > maxRatio) {
                    imgRatio = maxWidth / actualWidth
                    actualHeight = (imgRatio * actualHeight).toInt()
                    actualWidth = maxWidth.toInt()
                } else {
                    actualHeight = maxHeight.toInt()
                    actualWidth = maxWidth.toInt()
                }
            }
            // setting inSampleSize value allows to load a scaled down version of the original image
            options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight)
            // inJustDecodeBounds set to false to load the actual bitmap
            options.inJustDecodeBounds = false
            // this options allow android to claim the bitmap memory if it runs low on memory
            options.inPurgeable = true
            options.inInputShareable = true
            options.inTempStorage = ByteArray(16 * 1024)
            try {
                // load the bitmap from its path
                bmp = BitmapFactory.decodeFile(filePath, options)
            } catch (exception: OutOfMemoryError) {
                exception.printStackTrace()
            }
            try {
                scaledBitmap =
                    Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888)
            } catch (exception: OutOfMemoryError) {
                exception.printStackTrace()
            }
            val ratioX = actualWidth.toFloat() / (options.outWidth).toFloat()
            val ratioY = actualHeight.toFloat() / (options.outHeight).toFloat()
            val middleX = actualWidth.toFloat() / 2.0f
            val middleY = actualHeight.toFloat() / 2.0f
            val scaleMatrix = Matrix()
            scaleMatrix.setScale(ratioX, ratioY, middleX, middleY)

            scaledBitmap?.let {
                val canvas = Canvas(it)
                canvas.setMatrix(scaleMatrix)
                canvas.drawBitmap(
                    bmp,
                    middleX - bmp.getWidth() / 2,
                    middleY - bmp.getHeight() / 2,
                    Paint(Paint.FILTER_BITMAP_FLAG)
                )
            }

            // check the rotation of the image and display it properly
            val exif: ExifInterface
            try {
                exif = ExifInterface(filePath)
                val orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0
                )
                Log.d("EXIF", "Exif: " + orientation)
                val matrix = Matrix()
                if (orientation == 6) {
                    matrix.postRotate(90F)
                    Log.d("EXIF", "Exif: " + orientation)
                } else if (orientation == 3) {
                    matrix.postRotate(180F)
                    Log.d("EXIF", "Exif: " + orientation)
                } else if (orientation == 8) {
                    matrix.postRotate(270F)
                    Log.d("EXIF", "Exif: " + orientation)
                }
                scaledBitmap?.let {
                    scaledBitmap = Bitmap.createBitmap(
                        it,
                        0,
                        0,
                        it.getWidth(),
                        it.getHeight(),
                        matrix,
                        true
                    )
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            var out: FileOutputStream? = null
            val filename = getFilename(context)
            try {
                out = FileOutputStream(filename)
                // write the compressed bitmap at the destination specified by filename.
                scaledBitmap?.compress(Bitmap.CompressFormat.JPEG, 80, out)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            return filename
        }

        fun getFilename(context: Context): String {
            val file = File(context.filesDir.path, "files/")
            if (!file.exists()) {
                file.mkdirs()
            }
            val uriSting = (file.getAbsolutePath() + "/" + "profile" + ".jpg")
            return uriSting
        }

        fun getRealPathFromURI(contentURI: String, context: Context): String {
            val contentUri = Uri.parse(contentURI)
            val cursor = context.getContentResolver().query(contentUri, null, null, null, null)
            if (cursor == null) {
                return contentUri.path!!
            } else {
                cursor.moveToFirst()
                val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                return cursor.getString(index)
            }
        }

        fun calculateInSampleSize(
            options: BitmapFactory.Options,
            reqWidth: Int,
            reqHeight: Int
        ): Int {
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1
            if (height > reqHeight || width > reqWidth) {
                val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
                val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
                inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
            }
            val totalPixels = (width * height).toFloat()
            val totalReqPixelsCap = (reqWidth * reqHeight * 2).toFloat()
            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++
            }
            return inSampleSize
        }
    }

    interface ImageRequestListener {
        fun onLoadFailed()
        fun onResourceReady()
    }
}
