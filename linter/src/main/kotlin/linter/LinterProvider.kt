package linter

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import rules.argument.ArgumentRule
import rules.argument.ExpressionRule
import rules.argument.LiteralRule
import rules.format.CamelCaseRule
import rules.format.FormatRule
import rules.format.SnakeRule

class LinterProvider {

    private val formatMap: Map<String, FormatRule> = mapOf(
        "camelCase" to CamelCaseRule(),
        "snakeCase" to SnakeRule()
    )

    private val argumentMap: Map<String, ArgumentRule> = mapOf(
        "literal" to LiteralRule(),
        "expression" to ExpressionRule()
    )

    private val linter: Linter = LinterImpl()

    @Serializable
    data class LinterConfig(val case: String, val argument: String)

    fun provideLinter(json: String): Linter {
        val jsonParser = Json { ignoreUnknownKeys = true }
        val data = jsonParser.decodeFromString<LinterConfig>(json)
        val formatRule = formatMap[data.case] ?: throw IllegalArgumentException("Unknown format rule")
        val argumentRule = argumentMap[data.argument] ?: throw IllegalArgumentException("Unknown argument rule")
        linter.setFormatRules(formatRule)
        linter.setArgumentRules(argumentRule)
        return linter
    }
}