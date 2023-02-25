package com.meew.overdroch.data

import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.meew.overdroch.Endpoints
import com.meew.overdroch.data.hero.Hero
import com.meew.overdroch.data.hero.HeroKey
import com.meew.overdroch.data.maps.GameMap
import com.meew.overdroch.utils.HttpsTrustManager
import com.meew.overdroch.utils.RestUtils
import java.io.IOException
import java.net.URL
import javax.net.ssl.HttpsURLConnection


open class OverFastService{

    init {
        val policy = ThreadPolicy.Builder().permitAll().build()
        HttpsTrustManager.allowAllSSL()
        StrictMode.setThreadPolicy(policy)
        getAllHeroes();
        getAllMaps();
    }

    fun getService() : service = service;

    companion object service : OverFastDAO {
        override var heroes: MutableList<Hero>? = mutableListOf();
        var gson = Gson()
        override var maps:MutableList<GameMap> = mutableListOf();

        override fun getAllHeroes() {
            if(heroes?.isNotEmpty() == true){
                return;
            }
            var response:String=""
            var heroesList :List<Hero> ;
            try {
                response= RestUtils.getResponseBody(Endpoints.HEROES.value)
                val itemType = object : TypeToken<List<Hero>>() {}.type
                heroesList = gson.fromJson(response,itemType)

            } catch (e: IOException) {
                Log.println(Log.ERROR,null,response)
                throw RuntimeException(e)
            }
                heroesList!!.forEachIndexed { i, hero -> if(hero.key!=null) heroes?.add(getHeroData(hero.key)) }
    }

    override fun getHeroData(heroKey: String): Hero {
        try {
            val response = gson.fromJson(RestUtils.getResponseBody(Endpoints.HERO.value+"${heroKey}"), Hero::class.java)
            Log.println(Log.INFO, null, response.toString())
            return response as Hero
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    override fun getAllMaps() {
        try {
            val itemType = object : TypeToken<List<GameMap>>() {}.type
            maps = gson.fromJson<MutableList<GameMap>?>(RestUtils.getResponseBody(Endpoints.MAPS.value),itemType).toMutableList()
            Log.println(Log.INFO, null, maps.toString())
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }


    }

}