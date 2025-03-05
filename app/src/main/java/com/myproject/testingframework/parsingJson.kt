package com.myproject.testingframework

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonParser
import java.io.File


fun parseJsonToKotlin(data: String): List<Map<*, *>> {
    val obj = JsonParser.parseString(data).asJsonObject
    val gson = Gson()

    val template = gson.fromJson(obj.getAsJsonObject("template"), Map::class.java)
    val card = gson.fromJson(obj.getAsJsonObject("card"), Map::class.java)

    return listOf(template, card)
}