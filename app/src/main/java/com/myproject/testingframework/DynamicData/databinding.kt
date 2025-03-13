package com.myproject.testingframework.DynamicData

fun replaceTemplateVariables(inputString: String, data: Map<String, Any>): String {
    val pattern = Regex("""@([a-zA-Z0-9_.]+)""")

    return pattern.replace(inputString) { matchResult ->
        val variablePath = matchResult.groupValues[1]
        val keys = variablePath.split(".")

        var current: Any? = data
        for (key in keys) {
            current = when (current) {
                is Map<*, *> -> current[key]
                else -> break
            }
            if (current == null) break
        }

        current?.toString() ?: matchResult.value // Return original match if path not found
    }
}

