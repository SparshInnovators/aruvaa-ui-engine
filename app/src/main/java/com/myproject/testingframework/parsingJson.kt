package com.myproject.testingframework

import com.google.gson.Gson
import com.google.gson.JsonParser


fun parseJsonToKotlin(): List<Map<*, *>> {
    val obj = JsonParser.parseString(formdata).asJsonObject
    val gson = Gson()

    val template = gson.fromJson(obj.getAsJsonObject("template"), Map::class.java)
    val card = gson.fromJson(obj.getAsJsonObject("card"), Map::class.java)

    return listOf(template, card)
}