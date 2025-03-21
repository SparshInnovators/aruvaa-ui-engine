package com.myproject.composeflow.Utils

fun searchData(key: String): String {
    val variableName = key.removePrefix("$")
    return "Unknown Variable: $variableName"
}