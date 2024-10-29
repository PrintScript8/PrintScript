package tests

import org.junit.jupiter.api.Assertions.assertEquals
import provider.FormatterProvider
import utils.AstProvider
import utils.ExpectedOutputProvider
import utils.RuleProvider
import kotlin.test.Test

class SecondVersionTest {

    private val version = "1.1"

    @Test
    fun runAllTests() {
        var i = 1
        while (true) {
            val astList = AstProvider().providerAstList(i, version)
            if (astList.isEmpty()) break
            val rules = RuleProvider().provideRules(i, version)
            val formatter = FormatterProvider().provideFormatter(rules, version)
            val actual = formatter.format(astList.iterator())
            val expected = ExpectedOutputProvider().provideExpectedOutput(i, version)
            assertEquals(expected, actual)
            i++
        }
    }
}
