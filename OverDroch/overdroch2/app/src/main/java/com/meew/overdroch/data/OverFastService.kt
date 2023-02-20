package com.meew.overdroch.data

import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.meew.overdroch.utils.RestUtils
import java.io.Console
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

import javax.net.ssl.HttpsURLConnection

class OverFastService : OverFastDAO {
    var overFastEndpoint: String ="http://146.122.150.115:27015/"
    var connection: HttpURLConnection? = null;
    var memHeroes: List<Hero>? = null
    var gson = Gson()

    init {
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }

    override fun getAllHeroes(): List<Hero> {
        val url: URL
        url = try {
            URL(overFastEndpoint + "heroes")
        } catch (e: MalformedURLException) {
            throw RuntimeException(e)
        }
        var response:String=""
        try {
            if (memHeroes == null) {
                connection = url.openConnection() as HttpURLConnection
                response= RestUtils.getResponseBody(connection!!)
                val itemType = object : TypeToken<List<Hero>>() {}.type
                memHeroes = gson.fromJson(response,itemType)
            }
        } catch (e: IOException) {
            Log.println(Log.ERROR,null,response)
            throw RuntimeException(e)
        }
        return memHeroes!!
    }

    override fun getHeroData(heroKey: HeroKey): Hero {
        val url: URL
        return try {
            url = URL("$overFastEndpoint/heroes/$heroKey")
            connection = url.openConnection() as HttpsURLConnection
            gson.fromJson(connection!!.content as String, Hero::class.java)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }
}