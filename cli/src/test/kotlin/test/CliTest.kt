package test

import cli.Cli
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Paths

class CliTest {

    private val cli = Cli()

    @Test
    fun `validation test`() {
        val result = cli.run(
            arrayOf(
                "-o",
                "Validation",
                getAbsolutePath("src/test/kotlin/testtxt/Test1"),
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
                getAbsolutePath("src/test/kotlin/testtxt/Test2"),
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
                getAbsolutePath("src/test/kotlin/testtxt/Test1"),
                "-v",
                "1.0"
            )
        )

        assertEquals(
            "Result:\n" +
                "Tom\n" +
                "Eze",
            result
        )
    }

    @Test
    fun `format test`() {
        val result = cli.run(
            arrayOf(
                "-o",
                "Formatting",
                getAbsolutePath("src/test/kotlin/testtxt/Test1"),
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
                getAbsolutePath("src/test/kotlin/testtxt/Test3"),
            )
        )

        assertEquals("No errors found", result)
    }

    private fun getAbsolutePath(relativePath: String): String {
        val projectRoot = Paths.get("").toAbsolutePath().toString()
        return Paths.get(projectRoot, relativePath).toString()
    }
}
