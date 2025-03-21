package com.myproject.composeflow.Utils.JsonParsing

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonParser

fun ParseLocalJson(context: Context, id: Int): Map<*, *>{
    val ResId = id
    val jsonString = context.resources.openRawResource(ResId).bufferedReader().use { it.readText() }

    val obj = JsonParser.parseString(jsonString).asJsonObject

    val gson = Gson()
    val layout = gson.fromJson(obj.getAsJsonObject("Screen"), Map::class.java)

    return layout
}