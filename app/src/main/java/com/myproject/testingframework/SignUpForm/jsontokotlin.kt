package com.myproject.testingframework.SignUpForm

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.myproject.testingframework.R

fun JsonFileParsing(context: Context): List<Map<*, *>> {
    val ResId = R.raw.signup
    val jsonString = context.resources.openRawResource(ResId).bufferedReader().use { it.readText() }

    val obj = JsonParser.parseString(jsonString).asJsonObject

    val gson = Gson()
    val templates = gson.fromJson(obj.getAsJsonObject("template"),Map::class.java)
    val cards = gson.fromJson(obj.getAsJsonObject("card"),Map::class.java)

    return listOf(templates, cards)
}