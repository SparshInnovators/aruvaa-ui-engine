package com.myproject.composeflow.Utils.JsonParsing

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlin.math.log

fun ParseLocalJson(context: Context, id: Int): Map<*, *> {
    val ResId = id
    val jsonString = context.resources.openRawResource(ResId).bufferedReader().use { it.readText() }

    val obj = JsonParser.parseString(jsonString).asJsonObject

    val gson = Gson()
    val layout = gson.fromJson(obj.getAsJsonObject("Screen"), Map::class.java)

    return layout
}

fun ParseJsonString(data: String): Map<*, *> {

    return try {
        val obj = JsonParser.parseString(data).asJsonObject
        val gson = Gson()

        gson.fromJson(obj.getAsJsonObject("Screen"), Map::class.java)

    } catch (e: Exception) {
        Log.d("Ankit Raj", "Error in Parsing Data")
        emptyMap<Any, Any>()
    }

}