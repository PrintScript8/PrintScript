package linter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import rules.argument.InputIsMandatoryRule
import rules.argument.PrintIsMandatoryRule
import rules.argument.RuleRunner
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
        @SerialName("identifier_format") val case: String? = null,
        @SerialName("mandatory-variable-or-literal-in-println") val printArgument: Boolean? = null,
        @SerialName("mandatory-variable-or-literal-in-readInput") val inputArgument: Boolean? = null
    )

    fun provideLinter(json: String, version: String): Linter {
        val jsonParser = Json { ignoreUnknownKeys = true }
        val data = jsonParser.decodeFromString<LinterConfig>(json)
        var formatRule: FormatRule? = formatMap[data.case]
        val argumentRule = filterRuleByVersion(
            version,
            createRuleRunner(data.printArgument, data.inputArgument)
        )
        if (data.case == null) formatRule = null
        return when (version) {
            "1.0" -> LinterV1(argumentRule, formatRule)
            "1.1" -> LinterV2(argumentRule, formatRule)
            else -> throw IllegalArgumentException("Unknown version")
        }
    }

    private fun filterRuleByVersion(version: String, rule: RuleRunner?): RuleRunner? {
        if (rule == null) return null
        return if (version == "1.0") {
            if (rule.leftRule != null) RuleRunner(rule.leftRule, null)
            else null
        } else {
            rule
        }
    }

    private fun createRuleRunner(print: Boolean?, input: Boolean?): RuleRunner? {
        return if (print == null && input == null) {
            null
        } else {
            RuleRunner(
                if (print != null) PrintIsMandatoryRule(print) else null,
                if (input != null) InputIsMandatoryRule(input) else null
            )
        }
    }
}
