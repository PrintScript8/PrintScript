import linter.LinterProvider
import linter.LinterV1
import linter.LinterV2
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LinterProviderTest {
    @Test
    fun testLinterCreation() {
        val provider = LinterProvider()
        val result1 = provider.provideLinter("{ \"identifier_format\": \"camel case\" }", "1.0")
        assertEquals(result1 is LinterV1, true)
        val result2 = provider.provideLinter("{ \"identifier_format\": \"camel case\" }", "1.1")
        assertEquals(result2 is LinterV2, true)
    }

    @Test
    fun testFailCreation() {
        val provider = LinterProvider()
        try {
            provider.provideLinter("{ \"identifier_format\": \"camel case\" , \"argument\": \"unknown\" }", "1.0")
        } catch (e: IllegalArgumentException) {
            assertEquals(e.message, "Unknown argument rule")
        }
    }
}
