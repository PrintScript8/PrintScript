package json

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.buildJsonObject

data class FormattingRules(
    val spaceBeforeColon: Boolean,
    val spaceAfterColon: Boolean,
    val spaceAroundEquals: Boolean,
    var newlineBeforePrintln: Int,
    val newlineAfterSemicolon: Boolean,
    var indentation: Int = 0,
    val version: String
)

fun parseJsonRules(jsonString: String): FormattingRules {

    try {
        val jsonElement = Json.parseToJsonElement(jsonString)
        val jsonObject = jsonElement.jsonObject
        val rulesObject = jsonObject["rules"]!!.jsonObject
        val version = jsonObject["version"]!!.jsonPrimitive.content

        val newlineBeforePrintln = rulesObject["newlineBeforePrintln"]!!.jsonPrimitive.int
        if (newlineBeforePrintln > 3 || newlineBeforePrintln < 0) {
            throw IllegalArgumentException("newlineBeforePrintln must be between 0 and 3")
        }

        return FormattingRules(
            spaceBeforeColon = rulesObject["spaceBeforeColon"]!!.jsonPrimitive.boolean,
            spaceAfterColon = rulesObject["spaceAfterColon"]!!.jsonPrimitive.boolean,
            spaceAroundEquals = rulesObject["spaceAroundEquals"]!!.jsonPrimitive.boolean,
            newlineBeforePrintln = rulesObject["newlineBeforePrintln"]!!.jsonPrimitive.int,
            newlineAfterSemicolon = rulesObject["newlineAfterSemicolon"]!!.jsonPrimitive.boolean,
            version = version
        )
    }
    catch (e: Exception) {
        // show a message of the json structure so the user can copy it
        throw IllegalArgumentException("Invalid JSON structure:\n" +
                getActualStructure(jsonString) + "\n\n" +
                "Expected structure:\n" +
                getExpectedStructure())
    }
}

private fun getActualStructure(jsonString: String): String {
    return deleteDataFromJson(jsonString, "version")
}

fun deleteDataFromJson(jsonString: String, keyToRemove: String): String {
    // Parse the JSON string into a JsonObject
    val jsonObject = Json.parseToJsonElement(jsonString).jsonObject

    // Create a new JsonObject without the keys to remove
    val modifiedJsonObject = buildJsonObject {
        jsonObject.forEach { (key, value) ->
            if (key != keyToRemove) {
                put(key, value)
            }
        }
    }

    if (modifiedJsonObject.isEmpty()) {
        return "{" + "\n\n" + "}"
    }
    // Convert the modified JsonObject back to a JSON string
    return modifiedJsonObject.toString()
}

private fun getExpectedStructure(): String {
    return """
            {
                "rules": {
                    "spaceBeforeColon": boolean,
                    "spaceAfterColon": boolean,
                    "spaceAroundEquals": boolean,
                    "newlineBeforePrintln": Int,
                    "newlineAfterSemicolon": boolean
                }
            }
        """.trimIndent()
}
