import org.junit.jupiter.api.Test
import runner.Operations
import java.io.File
import java.nio.file.Paths
import kotlin.test.assertEquals

class TckTests {

    @Test
    fun `Test interpreter`() {
        val input = readFile(File(getAbsolutePath("src/test/kotlin/testfile/file5")))
        val runner = Operations(input, "1.1")
        assertEquals(
            emptyList(),
            runner.execute()
        )
    }

    private fun getAbsolutePath(relativePath: String): String {
        val projectRoot = Paths.get("").toAbsolutePath().toString()
        return Paths.get(projectRoot, relativePath).toString()
    }

    private fun readFile(file: File): String {
        return file.readText()
    }
}
