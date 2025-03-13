package com.myproject.testingframework.itemlist

import androidx.compose.ui.platform.LocalContext
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.serialization.json.Json

fun parseJson(): List<Map<*, *>> {
    val obj = JsonParser.parseString(jsonUi).asJsonObject
    val gson = Gson()

    val template = gson.fromJson(obj.getAsJsonObject("template"), Map::class.java)
    val card = gson.fromJson(obj.getAsJsonObject("card"), Map::class.java)

    return listOf(template, card)
}