package json

import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.int
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

data class FormattingRules(
    val spaceBeforeColon: Boolean,
    val spaceAfterColon: Boolean,
    val spaceAroundEquals: Boolean,
    val newlineBeforePrintln: Int,
    val newlineAfterSemicolon: Boolean,
    val maxSpacesBetweenTokens: Int,
    val spaceAroundOperators: Boolean
)

fun parseJsonRules(jsonString: String): FormattingRules {
    val jsonElement = Json.parseToJsonElement(jsonString)
    val jsonObject = jsonElement.jsonObject["rules"]!!.jsonObject

    return FormattingRules(
        spaceBeforeColon = jsonObject["spaceBeforeColon"]!!.jsonPrimitive.boolean,
        spaceAfterColon = jsonObject["spaceAfterColon"]!!.jsonPrimitive.boolean,
        spaceAroundEquals = jsonObject["spaceAroundEquals"]!!.jsonPrimitive.boolean,
        newlineBeforePrintln = jsonObject["newlineBeforePrintln"]!!.jsonPrimitive.int,
        newlineAfterSemicolon = jsonObject["newlineAfterSemicolon"]!!.jsonPrimitive.boolean,
        maxSpacesBetweenTokens = jsonObject["maxSpacesBetweenTokens"]!!.jsonPrimitive.int,
        spaceAroundOperators = jsonObject["spaceAroundOperators"]!!.jsonPrimitive.boolean
    )
}
