package utils

class RuleProvider {

    private val firstVersionRuleProvider = firstversionfiles.rule.RuleProvider()
    private val secondVersionRuleProvider = secondversionfiles.rule.RuleProvider()

    fun provideRules(number: Int, version: String): String {

        return when (version) {
            "1.0" -> firstVersionRuleProvider.provideRules(number)
            "1.1" -> secondVersionRuleProvider.provideRules(number)
            else -> throw IllegalArgumentException("Invalid version")
        }
    }
}
