package json

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class JsonRules {

    companion object {
        const val TOP = 3
        const val BOTTOM = 0
    }

    @Suppress("TooGenericExceptionCaught")
    fun parseJsonRules(jsonString: String): FormattingRules {

        try {
            val jsonElement = Json.parseToJsonElement(jsonString)
            val jsonObject = jsonElement.jsonObject
            val rulesObject = jsonObject["rules"]!!.jsonObject
            val version = jsonObject["version"]!!.jsonPrimitive.content

            val newlineBeforePrintln = rulesObject["newlineBeforePrintln"]!!.jsonPrimitive.int
            require(newlineBeforePrintln in BOTTOM..TOP) {
                "newlineBeforePrintln must be between $BOTTOM and $TOP"
            }

            return FormattingRules(
                spaceBeforeColon = rulesObject["spaceBeforeColon"]!!.jsonPrimitive.boolean,
                spaceAfterColon = rulesObject["spaceAfterColon"]!!.jsonPrimitive.boolean,
                spaceAroundEquals = rulesObject["spaceAroundEquals"]!!.jsonPrimitive.boolean,
                newlineBeforePrintln = rulesObject["newlineBeforePrintln"]!!.jsonPrimitive.int,
                version = version
            )
        } catch (e: NullPointerException) {
            throw IllegalArgumentException(
                "Invalid JSON structure:\n" +
                    getActualStructure(jsonString) + "\n\n" +
                    "Expected structure:\n" +
                    getExpectedStructure() + "\n\n" +
                    "Error message: ",
                e
            )
        }
    }

    private fun getActualStructure(jsonString: String): String {
        return deleteDataFromJson(jsonString, "version")
    }

    private fun deleteDataFromJson(jsonString: String, keyToRemove: String): String {
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
                    "newlineBeforePrintln": Int
                }
            }
        """.trimIndent()
    }
}
