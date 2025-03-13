package com.myproject.testingframework.mvvm_Arc.model.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonParser

fun JsonToKotlin(context: Context, id: Int): List<Map<*, *>> {
    val ResId = id
    val jsonString = context.resources.openRawResource(ResId).bufferedReader().use { it.readText() }

    val obj = JsonParser.parseString(jsonString).asJsonObject

    val gson = Gson()
    val templates = gson.fromJson(obj.getAsJsonObject("template"), Map::class.java)
    val cards = gson.fromJson(obj.getAsJsonObject("card"), Map::class.java)

    return listOf(templates, cards)
}