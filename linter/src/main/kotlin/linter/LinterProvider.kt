package linter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import rules.argument.IsMandatoryRule
import rules.format.CamelCaseRule
import rules.format.FormatRule
import rules.format.SnakeRule

class LinterProvider {

    private val formatMap: Map<String, FormatRule> = mapOf(
        "camel case" to CamelCaseRule(),
        "snake case" to SnakeRule()
    )

    @Serializable
    data class LinterConfig(
        @SerialName("identifier_format") val case: String = "camel case",
        @SerialName("mandatory-variable-or-literal-in-println") val argument: Boolean = true
    )

    fun provideLinter(json: String, version: String): Linter {
        val jsonParser = Json { ignoreUnknownKeys = true }
        val data = jsonParser.decodeFromString<LinterConfig>(json)
        val formatRule = formatMap[data.case] ?: throw IllegalArgumentException("Unknown format rule")
        val argumentRule = IsMandatoryRule(data.argument)
        return when (version) {
            "1.0" -> LinterV1(argumentRule, formatRule)
            "1.1" -> LinterV2(argumentRule, formatRule)
            else -> throw IllegalArgumentException("Unknown version")
        }
    }
}
