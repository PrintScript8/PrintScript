package type

import node.Node
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class StringTypeTest {

    @Test
    fun testStringTypeCreation() {
        val stringType = StringType("Hello")
        assertNotNull(stringType)
    }

    @Test
    fun testStringTypeResultType() {
        val stringType = StringType("Hello")
        val children = listOf<Node>() // Assuming Node is a valid class
        assert(stringType.apply(children) is StringType)
    }

    @Test
    fun testStringTypePrint() {
        val stringType = StringType("Hello")
        val children = listOf<Node>() // Assuming Node is a valid class
        val expectedOutput = "Hello" // Replace with actual expected output
        assertEquals(expectedOutput, stringType.print(children))
    }
}