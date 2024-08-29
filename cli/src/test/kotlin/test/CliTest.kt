package test

import cli.Cli
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CliTest {

    private val cli = Cli()

    @Test
    fun `validation test`() {
        val result = cli.run(
            arrayOf(
                "-o",
                "Validation",
                "C:\\Users\\54911\\Desktop\\Tomy\\projects\\PrintScript\\cli\\src\\test\\kotlin\\testtxt\\Test1",
            )
        )

        assertEquals("Validation successful", result)
    }

    @Test
    fun `missing declaration symbol validation test`() {
        val result = cli.run(
            arrayOf(
                "-o",
                "Validation",
                "C:\\Users\\54911\\Desktop\\Tomy\\projects\\PrintScript\\cli\\src\\test\\kotlin\\testtxt\\Test2",
            )
        )

        assertEquals("Error: Missing ':' in the declaration", result)
    }

    @Test
    fun `execution test`() {
        val result = cli.run(
            arrayOf(
                "-o",
                "Execution",
                "C:\\Users\\54911\\Desktop\\Tomy\\projects\\PrintScript\\cli\\src\\test\\kotlin\\testtxt\\Test1",
                "-v",
                "1.0"
            )
        )

        assertEquals(
            "Result:\n" +
                "\"Tom\"\n" +
                "\"Eze\"",
            result
        )
    }

    @Test
    fun `format test`() {
        val result = cli.run(
            arrayOf(
                "-o",
                "Formatting",
                "C:\\Users\\54911\\Desktop\\Tomy\\projects\\PrintScript\\cli\\src\\test\\kotlin\\testtxt\\Test1"
            )
        )

        assertEquals(
            "Formatted: " +
                "let name: String = \"Hello\";\n" +
                "name = \"Tom\";\n" +
                "println(name);\n" +
                "let name2: String = \"Eze\";\n" +
                "println(name2);",
            result
        )
    }

    @Test
    fun `analyze test`() {
        val result = cli.run(
            arrayOf(
                "-o",
                "Analyzing",
                "C:\\Users\\54911\\Desktop\\Tomy\\projects\\PrintScript\\cli\\src\\test\\kotlin\\testtxt\\Test3",
            )
        )

        assertEquals("No errors found", result)
    }
}
