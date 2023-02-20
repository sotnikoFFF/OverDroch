package com.meew.overdroch.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection

object RestUtils {
    @Throws(IOException::class)
    fun getResponseBody(connection: HttpURLConnection): String {
        val responseBody = connection.inputStream
        val response = StringBuilder()
        BufferedReader(InputStreamReader(responseBody, "UTF-8")).use { reader ->
            var c = 0
            while (reader.read().also { c = it } != -1) {
                response.append(c.toChar())
            }
        }
        return response.toString()
    }

    @Throws(IOException::class)
    fun getImage(connection: URLConnection): Bitmap? {
        var mIcon11: Bitmap? = null
        try {
            val `in` = connection.getInputStream()
            mIcon11 = BitmapFactory.decodeStream(`in`)
        } catch (e: Exception) {
            Log.e("Error", e.message!!)
            e.printStackTrace()
        }
        return mIcon11
    }

    fun downloadImage(portait: String?): Bitmap? {
        return try {
            val url = URL(portait)
            getImage(url.openConnection())
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }
}