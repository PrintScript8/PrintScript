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
    var newlineBeforePrintln: Int,
    val newlineAfterSemicolon: Boolean,
    var indentation: Int = 0,
    val version: String
)

fun parseJsonRules(jsonString: String): FormattingRules {
    val jsonElement = Json.parseToJsonElement(jsonString)
    val jsonObject = jsonElement.jsonObject
    val rulesObject = jsonObject["rules"]!!.jsonObject
    val version = jsonObject["version"]!!.jsonPrimitive.content

    return FormattingRules(
        spaceBeforeColon = rulesObject["spaceBeforeColon"]!!.jsonPrimitive.boolean,
        spaceAfterColon = rulesObject["spaceAfterColon"]!!.jsonPrimitive.boolean,
        spaceAroundEquals = rulesObject["spaceAroundEquals"]!!.jsonPrimitive.boolean,
        newlineBeforePrintln = rulesObject["newlineBeforePrintln"]!!.jsonPrimitive.int,
        newlineAfterSemicolon = rulesObject["newlineAfterSemicolon"]!!.jsonPrimitive.boolean,
        version = version
    )
}