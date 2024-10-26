package secondversion

import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.dynamic.ReadInputType
import node.dynamic.VariableType
import node.staticpkg.ExpressionType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import provider.FormatterProvider

class ReadInputTest {

    @Test
    fun `read input test`() {

        val readInputType = ReadInputType(LiteralType(LiteralValue.StringValue("Enter a number")), null)
        val expression = ExpressionType(
            VariableType("input", LiteralValue.StringValue("")),
            readInputType
        )

        val astList = listOf(expression)
        val version = "1.1"

        val rules = """{
        "rules": {
            "spaceBeforeColon": false,
            "spaceAfterColon": true,
            "spaceAroundEquals": true,
            "newlineBeforePrintln": 2,
            "newlineAfterSemicolon": true
        }
    }"""

        val formatter = FormatterProvider().provideFormatter(rules, version)

        val formatted = formatter.format(astList.iterator())
        val expected = "input = readInput(\"Enter a number\");"
        assertEquals(expected, formatted)
    }
}
