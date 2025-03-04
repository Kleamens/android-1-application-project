package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.shared

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import java.io.IOException

object ImageResolver {
    fun getUriFromFilename(context: Context, filename: String): Uri? {
        val files = context.filesDir.listFiles()

        val file = files.find {
            it.canRead() && it.isFile && it.name == filename + ".jpg"
        }

        if (file != null) {
            return Uri.parse(context.filesDir.toString() + "/$filename" + ".jpg")
        }
        return null

    }

    fun saveImageToInternalStorage(filename:String, bitmap: Bitmap, context:Context):Boolean{


        return try{
            context.openFileOutput(filename+".jpg", Context.MODE_PRIVATE).use{ stream->

                if(!bitmap.compress(Bitmap.CompressFormat.JPEG,95,stream)){
                    throw IOException("Couldnt save bitmap")
                }
            }
            true
        }catch (e: IOException){
            e.printStackTrace()
            false
        }
    }


    fun getRandomString(length: Int) : String {
        val charset =  "0123456789qwertyuiopasdfghjklzxcvbnm"
        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }
}