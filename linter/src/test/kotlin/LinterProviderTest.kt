import linter.LinterImpl
import linter.LinterProvider
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class LinterProviderTest {
    @Test
    fun testLinterCreation() {
        val provider = LinterProvider()
        val result = provider.provideLinter("{ \"case\": \"camelCase\" , \"argument\": \"literal\" }")
        assertEquals(result is LinterImpl, true)
    }

    @Test
    fun testFailCreation() {
        val provider = LinterProvider()
        try {
            provider.provideLinter("{ \"case\": \"camelCase\" , \"argument\": \"unknown\" }")
        } catch (e: IllegalArgumentException) {
            assertEquals(e.message, "Unknown argument rule")
        }
    }
}