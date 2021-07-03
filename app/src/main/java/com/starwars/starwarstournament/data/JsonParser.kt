package com.starwars.starwarstournament.data

import android.content.Context
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

fun Context.getJsonFromAssets(fileName: String): String {
    return try {
        val `is`: InputStream = assets.open(fileName)
        val size: Int = `is`.available()
        val buffer = ByteArray(size)
        `is`.read(buffer)
        `is`.close()
        String(buffer, Charset.defaultCharset())
    } catch (e: IOException) {
        e.printStackTrace()
        return ""
    }
}
